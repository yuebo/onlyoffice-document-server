package com.eappcat.document.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.eappcat.document.server.ResponseStatus;
import com.eappcat.document.server.DocumentConfigProperties;
import com.eappcat.document.server.convert.ConvertRequestVO;
import com.eappcat.document.server.convert.ConvertResultVO;
import com.eappcat.document.server.convert.ConvertVO;
import com.eappcat.document.server.dao.ConverterRequestRepository;
import com.eappcat.document.server.dao.DocumentRepository;
import com.eappcat.document.server.entity.ConverterRequest;
import com.eappcat.document.server.entity.Document;
import com.eappcat.document.server.utils.JaxbUtils;
import com.eappcat.document.server.utils.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

/**
 * 文档转换服务
 * 通过文档转换的api
 * @author Xuan Yue Bo
 */
@RestController
@RequestMapping("/converter")
@Slf4j
public class DocumentConverterController {
    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private DocumentConfigProperties documentConfigProperties;

    @Autowired
    private ConverterRequestRepository converterRequestRepository;
    @Autowired
    private DocumentRepository documentRepository;

    /**
     * 文档转换请求
     * @param convertRequestVO 文档转换的请求对象
     * @param result 文档转换的结构
     * @return
     */
    @PostMapping("/request")
    public JSONObject createConverterRequest(@RequestBody @Valid ConvertRequestVO convertRequestVO, BindingResult result){
        JSONObject jsonObject=new JSONObject();
        if(!result.hasErrors()){
            Optional<Document> document=documentRepository.findByDocId(convertRequestVO.getDocumentId());
            if(!document.isPresent()){
                jsonObject.put("error",ResponseStatus.NOT_FOUND.getCode());
            }else {
                //计算Hash值
                String hash=document.get().getHash();
                Optional<ConverterRequest> converterRequestOptional=converterRequestRepository.findByRequestNo(hash);
                ConverterRequest converterRequest=new ConverterRequest();
                if(converterRequestOptional.isPresent()){
                    converterRequest=converterRequestOptional.get();
                }else {
                    converterRequest.setCreatedDate(new Date());
                    converterRequest.setUpdatedDate(new Date());
                    converterRequest.setRequestNo(hash);
                    ConvertVO convertVO = toConvertVO(convertRequestVO, document.get());
                    converterRequest.setParameters(JSONObject.toJSONString(convertVO));
                    converterRequest.setRequestDate(new Date());
                }
                converterRequestRepository.save(converterRequest);

                executeConvertRequest(jsonObject, converterRequest);
                jsonObject.put("error",ResponseStatus.SUCCESS.getCode());
                jsonObject.put("request",converterRequest);
            }

        }else {
            jsonObject.put("error",ResponseStatus.FAILED.getCode());
        }

        return jsonObject;

    }

    @GetMapping("{id}")
    public JSONObject convert(@PathVariable("id")String id){
        JSONObject jsonObject=new JSONObject();
        Optional<ConverterRequest> converterRequestOptional=converterRequestRepository.findByRequestNo(id);
        if(converterRequestOptional.isPresent()){
            executeConvertRequest(jsonObject, converterRequestOptional.get());
            jsonObject.put("request",converterRequestOptional.get());
            jsonObject.put("error",ResponseStatus.SUCCESS.getCode());
        }else {
            jsonObject.put("error",ResponseStatus.FAILED.getCode());
        }
        return jsonObject;
    }

    /**
     * 调用document服务器转换文档
     * @param jsonObject
     * @param converterRequest
     */
    private void executeConvertRequest(JSONObject jsonObject, ConverterRequest converterRequest) {
        Request.Builder requestBuilder=new Request.Builder();
        requestBuilder.url(documentConfigProperties.getDocumentConverterServiceUrl());
        requestBuilder.post(okhttp3.RequestBody.create(MediaType.get(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE),converterRequest.getParameters()));
        try {
            Response response=okHttpClient.newCall(requestBuilder.build()).execute();
            if (!response.isSuccessful()){
                throw new IllegalStateException("http status is not success");
            }
            String xml=response.body().string();
            log.info("result: {}",xml);
            ConvertResultVO resultVO=JaxbUtils.convertToJavaBean(ConvertResultVO.class,xml);
            jsonObject.put("result",resultVO);
        }catch (Exception e){
            log.error("{}",e);
        }
    }
    private ConvertVO toConvertVO(@Valid @RequestBody ConvertRequestVO convertRequestVO, Document document) {
        ConvertVO convertVO=new ConvertVO();
        convertVO.setAsync(convertRequestVO.getAsync()==null||convertRequestVO.getAsync());
        convertVO.setKey(document.getHash());
        convertVO.setOutputType(convertRequestVO.getConvertTo());
        convertVO.setTitle(document.getName());
        convertVO.setFileType(document.getType());
        convertVO.setThumbnail(convertRequestVO.getThumbnail());
        convertVO.setUrl(String.format("%s/document/stream/%s",documentConfigProperties.getServerUrl(),convertRequestVO.getDocumentId()));
        return convertVO;
    }


}

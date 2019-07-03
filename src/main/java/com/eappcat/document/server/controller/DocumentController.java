package com.eappcat.document.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.eappcat.document.server.DocumentConfigProperties;
import com.eappcat.document.server.DocumentEditStatus;
import com.eappcat.document.server.DocumentStorageService;
import com.eappcat.document.server.DocumentTypeDetector;
import com.eappcat.document.server.ResponseStatus;
import com.eappcat.document.server.dao.DocumentRepository;
import com.eappcat.document.server.entity.Document;
import com.eappcat.document.server.mapping.MappingResovler;
import com.eappcat.document.server.utils.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/document")
@Slf4j
public class DocumentController {
    @Autowired
    private DocumentConfigProperties configProperties;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentTypeDetector detector;
    @Autowired
    private MappingResovler mappingResovler;

    @Autowired
    private DocumentStorageService documentStorageService;
    @GetMapping("/editor/{id}")
    public String editor(@PathVariable(name = "id")String id, Model model,@RequestParam(name = "edit",required = false,defaultValue = "false") String edit){
        model.addAttribute("config",configProperties);
        Optional<Document> documentOptional=documentRepository.findByDocId(id);
        if(documentOptional.isPresent()){
            Document document=documentOptional.get();
            model.addAttribute("document",document);
            model.addAttribute("mode","true".equalsIgnoreCase(edit)?"edit":"view");
            model.addAttribute("documentType",detector.detectDocumentType(document.getType()));
            return "index";
        }
        else {
            return "error";
        }
    }
    @GetMapping("/{id}")
    @ResponseBody
    public JSONObject editor(@PathVariable(name = "id")String id){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("config",configProperties);
        Optional<Document> documentOptional=documentRepository.findByDocId(id);
        if(documentOptional.isPresent()){
            Document document=documentOptional.get();
            jsonObject.put("document",document);
            jsonObject.put("documentType",detector.detectDocumentType(document.getType()));
            jsonObject.put("error",ResponseStatus.SUCCESS.getCode());
        }else {
            jsonObject.put("error",ResponseStatus.NOT_FOUND.getCode());
        }
        return jsonObject;
    }


    @GetMapping("/stream/{id}")
    public void loadDocument(@RequestBody(required = false) String body, @PathVariable(name = "id")String id, HttpServletResponse response) throws IOException {
        try(InputStream inputStream= documentStorageService.load(id)){
            IOUtils.copy(inputStream,response.getOutputStream());
            IOUtils.copy(inputStream,response.getOutputStream());
        }
    }

    @RequestMapping(path = "/callback/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSONObject callback(@RequestBody(required = false) String body,@PathVariable(name = "id")String id){
        JSONObject jsonObject=JSONObject.parseObject(body);
        JSONObject result=new JSONObject();
        int status=jsonObject.getInteger("status");

        switch (status){
            case DocumentEditStatus
                    .READY_TO_EDIT:
                result.put("error",ResponseStatus.SUCCESS.getCode());
                break;
            case DocumentEditStatus.FORCE_SAVED:
                String url= jsonObject.getString("url");
                Optional<Document> document=documentRepository.findByDocId(id);
                if(document.isPresent()){
                    try{
                        URL docUrl=new URL(url);
                        try(InputStream inputStream=docUrl.openStream()) {
                            documentStorageService.save(id,inputStream);
                        }
                        //获取文件签名
                        try(InputStream inputStream= documentStorageService.load(id)) {
                            document.get().setHash(DigestUtils.md5DigestAsHex(inputStream));
                        }
                        document.get().setUpdatedDate(new Date());
                        documentRepository.save(document.get());
                        result.put("error",ResponseStatus.SUCCESS.getCode());
                    }catch (Exception e){
                        result.put("error",ResponseStatus.FAILED.getCode());
                    }
                }else {
                    result.put("error",ResponseStatus.FAILED.getCode());
                }

                break;
            default:

        }
        log.info("{}",body);
        return result;
    }
    @PostMapping("/upload")
    @ResponseBody
    public JSONObject upload(@RequestParam("file") MultipartFile file){
        JSONObject result=new JSONObject();
        try {
            Document document=new Document();
            document.setName(file.getName());
            document.setCreatedDate(new Date());
            document.setUpdatedDate(new Date());
            document.setContentType(file.getContentType());
            document.setType(mappingResovler.getDocumentType(file.getContentType()));
            document.setDocId(UuidGenerator.nextString());
            document.setVersion(1L);
            document.setHash(String.valueOf(System.currentTimeMillis()));

            try(InputStream inputStream=file.getInputStream()) {
                documentStorageService.save(document.getDocId(),inputStream);
            }
            //获取文件签名
            try(InputStream inputStream= documentStorageService.load(document.getDocId())) {
                document.setHash(DigestUtils.md5DigestAsHex(inputStream));
            }
            document=documentRepository.save(document);
            result.put("status",ResponseStatus.SUCCESS.getCode());
            result.put("document",document);
            return result;
        }catch (Exception e){
            log.error("{}",e);
            result.put("status",ResponseStatus.FAILED.getCode());
            return result;
        }

    }
}

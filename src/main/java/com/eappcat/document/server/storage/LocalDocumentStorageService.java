package com.eappcat.document.server.storage;

import com.eappcat.document.server.DocumentStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * 本地文件系统存储器
 * @author Xuan Yue Bo
 */
@AllArgsConstructor
public class LocalDocumentStorageService implements DocumentStorageService {
    private File root;

    @PostConstruct
    public void init(){
        if(root!=null&&!root.exists()){
            root.mkdirs();
        }
    }
    @Override
    public InputStream load(String id) throws IOException {
        File document=new File(root,id);
        return new FileInputStream(document);
    }

    @Override
    public void save(String id, InputStream inputStream)throws IOException {
        File document=new File(root,id);
        try(FileOutputStream fileOutputStream=new FileOutputStream(document)) {
            IOUtils.copy(inputStream,fileOutputStream);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}

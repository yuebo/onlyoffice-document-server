package com.eappcat.document.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文档存储服务
 * @author Xuan Yue Bo
 */
public interface DocumentStorageService {
    /**
     * 根据文档id加载文档
    * @param id 文档的唯一标识
     * @return
     * @throws IOException
     */
    InputStream load(String id) throws IOException;

    /**
     * 文档的持久化
     * @param id
     * @param inputStream
     * @throws IOException
     */
    void save(String id,InputStream inputStream)throws IOException;
}

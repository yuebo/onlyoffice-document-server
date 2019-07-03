package com.eappcat.document.server;

/**
 * 用于将上传的ContentType和文档编辑器的文档类型转换
 */
public interface ContentTypeMapping {
    /**
     * 是否接收此类型
     * @param contentType
     * @return
     */
    boolean accept(String contentType);

    /**
     * 返回编辑器的实际类型
     * @return
     */
    String documentType();
}

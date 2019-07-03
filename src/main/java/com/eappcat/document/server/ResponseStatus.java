package com.eappcat.document.server;

/**
 * 请求错误状态类
 * @author Xuan Yue Bo
 */
public enum ResponseStatus {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 失败
     */
    FAILED(1),
    /**
     * 文档不存在
     */
    NOT_FOUND(2);
    private int code;
    ResponseStatus(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}

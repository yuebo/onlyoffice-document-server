package com.eappcat.document.server;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 根据文档类型推断编辑器类型
 * @author Xuan Yue Bo
 */
@Component
public class DocumentTypeDetector {
    private static final String [] TEXT=new String[]{"docx","doc",".docm","dot","dotm","dotx","epub","fodt","html","htm","mht","odt","ott","rtf","txt","djvu","xps","pdf"};
    private static final String [] SPREADSHEET=new String[]{"csv","fods","ods", "ots", "xls", "xlsm", "xlsx", "xlt","xltm","xltx"};
    private static final String [] PRESENTATION=new String[]{"fodp","odp","otp","pot","potm","potx","pps","ppsm","ppsx","ppt","pptm","pptx"};
    public String detectDocumentType(String type){
        if(Arrays.asList(TEXT).contains(type)){
            return "text";
        }
        if(Arrays.asList(SPREADSHEET).contains(type)){
            return "spreadsheet";
        }
        if(Arrays.asList(PRESENTATION).contains(type)){
            return "presentation";
        }
        return "text";
    }
}

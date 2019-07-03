package com.eappcat.document.server.mapping;

import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.stereotype.Component;

@Component
public class XlsDocumentMapping implements ContentTypeMapping {
    @Override
    public boolean accept(String contentType) {
        return "application/vnd.ms-excel".equals(contentType);
    }
    @Override
    public String documentType() {
        return "xls";
    }
}

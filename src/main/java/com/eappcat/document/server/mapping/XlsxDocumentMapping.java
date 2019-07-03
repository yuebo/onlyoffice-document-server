package com.eappcat.document.server.mapping;

import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.stereotype.Component;

@Component
public class XlsxDocumentMapping implements ContentTypeMapping {
    @Override
    public boolean accept(String contentType) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
    }
    @Override
    public String documentType() {
        return "xlsx";
    }
}

package com.eappcat.document.server.mapping;

import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.stereotype.Component;

@Component
public class DocxDocumentMapping implements ContentTypeMapping {
    @Override
    public boolean accept(String contentType) {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType);
    }
    @Override
    public String documentType() {
        return "docx";
    }
}

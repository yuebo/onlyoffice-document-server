package com.eappcat.document.server.mapping;

import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.stereotype.Component;

@Component
public class PptxDocumentMapping implements ContentTypeMapping {
    @Override
    public boolean accept(String contentType) {
        return "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(contentType);
    }
    @Override
    public String documentType() {
        return "pptx";
    }
}

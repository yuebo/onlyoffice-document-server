package com.eappcat.document.server.mapping;

import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.stereotype.Component;

@Component
public class PdfDocumentMapping implements ContentTypeMapping {
    @Override
    public boolean accept(String contentType) {
        return "application/pdf".equals(contentType);
    }
    @Override
    public String documentType() {
        return "pdf";
    }
}

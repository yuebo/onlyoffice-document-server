package com.eappcat.document.server.mapping;


import com.eappcat.document.server.ContentTypeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MappingResovler {
    @Autowired
    private List<ContentTypeMapping> contentTypeMappingList;
    public String getDocumentType(String contentType){
        Optional<ContentTypeMapping> contentTypeMappingOptional=contentTypeMappingList.stream().filter(item->item.accept(contentType)).findFirst();
        if(contentTypeMappingOptional.isPresent()){
            return contentTypeMappingOptional.get().documentType();
        }
        throw new IllegalStateException("unknown document type "+contentType);
    }
}

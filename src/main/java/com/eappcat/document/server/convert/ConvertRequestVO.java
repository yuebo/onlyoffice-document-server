package com.eappcat.document.server.convert;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ConvertRequestVO {
    @NotBlank
    private String documentId;
    @NotBlank
    private String convertTo;
    private Boolean async;
    private ThumbnailVO thumbnail;
}

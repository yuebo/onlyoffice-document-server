package com.eappcat.document.server.convert;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class ConvertVO {
    @NotNull
    private Boolean async;
    private Integer codePage;
    private Integer delimiter;
    @NotBlank
    private String fileType;
    private String key;
    @JSONField(name = "outputtype")
    @NotBlank
    private String outputType;
    private ThumbnailVO thumbnail;
    @NotBlank
    private String title;
    @NotBlank
    private String url;

}

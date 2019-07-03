package com.eappcat.document.server.convert;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FileResult")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ConvertResultVO {
    @XmlElement(name = "EndConvert")
    private String endConvert;
    @XmlElement(name = "FileUrl")
    private String fileUrl;
    @XmlElement(name = "Percent")
    private Integer percent;
    @XmlElement(name = "Error")
    private Integer error;
}

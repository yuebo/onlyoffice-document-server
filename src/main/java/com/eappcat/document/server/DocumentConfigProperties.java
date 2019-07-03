package com.eappcat.document.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "document.server")
@Data
public class DocumentConfigProperties {
    private String localDir=System.getProperty("java.io.tmpdir","/tmp").concat("/document");
    private String documentServerRootUrl="http://localhost";
    private String serverUrl="http://localhost:8080";
    public String getDocumentServerUrl(){
        return String.format("%s/web-apps/apps/api/documents/api.js",this.documentServerRootUrl);
    }
    public String getDocumentConverterServiceUrl(){
        return String.format("%s/ConvertService.ashx",this.documentServerRootUrl);
    }
}

package com.eappcat.document.server;

import com.eappcat.document.server.storage.LocalDocumentStorageService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@EnableConfigurationProperties(DocumentConfigProperties.class)
public class ServerCoreApplication {

    @Autowired
    private DocumentConfigProperties configProperties;

    public static void main(String[] args) {
        SpringApplication.run(ServerCoreApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean
    LocalDocumentStorageService documentStorage(){
        return new LocalDocumentStorageService(new File(configProperties.getLocalDir()));
    }
    @Bean
    @ConditionalOnMissingBean
    OkHttpClient okHttpClient(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        return builder.build();
    }
}

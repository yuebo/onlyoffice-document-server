package com.eappcat.document.server.utils;

import java.util.UUID;

public class UuidGenerator {
    public static String nextString(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}

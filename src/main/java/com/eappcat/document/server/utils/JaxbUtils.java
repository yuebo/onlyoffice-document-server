package com.eappcat.document.server.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class JaxbUtils {

    public static <T> String convertToXml(T obj) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            //格式化输出，即按标签自动换行，否则就是一行输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //设置编码（默认编码就是utf-8）
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //是否省略xml头信息，默认不省略（false）
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            StringWriter stringWriter=new StringWriter();
            marshaller.marshal(obj, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            log.error("{}",e);
            throw new IllegalStateException(e);
        }
    }

    public static<T> T convertToJavaBean(Class<T> clz, String file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            T t = (T) unmarshaller.unmarshal(new StringReader(file));
            return t;
        } catch (JAXBException e) {
            log.error("{}",e);
            throw new IllegalStateException(e);
        }
    }
}

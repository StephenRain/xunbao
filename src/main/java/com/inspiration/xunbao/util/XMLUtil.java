package com.inspiration.xunbao.util;

import lombok.SneakyThrows;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class XMLUtil {

    static DocumentBuilderFactory factory;
    static DocumentBuilder documentBuilder;

    static {
        try {
            factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static Document parseXML(String filePath){
        URL resource = XMLUtil.class.getClassLoader().getResource(filePath);
        Objects.requireNonNull(resource);
        File file = new File(resource.getFile());
        return documentBuilder.parse(file);
    }

    @SneakyThrows
    public static void main(String[] args) {


    }
}

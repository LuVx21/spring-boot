package org.luvx.tome.utils;

import java.io.StringWriter;

import javax.annotation.Nullable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlUtils {
    public static XmlMapper    xmlMapper    = new XmlMapper();
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertXmlToJson(String xml) {
        StringWriter writer = new StringWriter();
        try (JsonParser jp = xmlMapper.getFactory().createParser(xml)) {
            try (JsonGenerator jg = objectMapper.getFactory().createGenerator(writer)) {
                while (jp.nextToken() != null) {
                    jg.copyCurrentEvent(jp);
                }
            }
        } catch (Exception e) {
            log.info("xml->json异常", e);
        }
        return writer.toString();
    }

    @Nullable
    public <T> T xmlToBean(String xml, Class<T> clazz) {
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

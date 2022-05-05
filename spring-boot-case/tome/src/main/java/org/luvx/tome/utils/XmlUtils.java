package org.luvx.tome.utils;

import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtils {
    public static XmlMapper    xmlMapper    = new XmlMapper();
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertXmlToJson(String xml) {
        StringWriter w = new StringWriter();
        try {
            JsonParser jp = xmlMapper.getFactory().createParser(xml);
            JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
            while (jp.nextToken() != null) {
                jg.copyCurrentEvent(jp);
            }
            jp.close();
            jg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return w.toString();
    }
}

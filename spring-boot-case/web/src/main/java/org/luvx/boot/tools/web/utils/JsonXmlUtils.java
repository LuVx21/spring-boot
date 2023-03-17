package org.luvx.boot.tools.web.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JsonXmlUtils {

    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws IOException
     * @throws SAXException
     */
    public static String jsonToPrettyXml(JSONObject json) throws IOException, SAXException {
        Document document = jsonToDocument(json);

        /* 格式化xml */
        OutputFormat format = OutputFormat.createPrettyPrint();

        // 设置缩进为4个空格
        format.setIndent(" ");
        format.setIndentSize(4);

        StringWriter formatXml = new StringWriter();
        XMLWriter writer = new XMLWriter(formatXml, format);
        writer.write(document);

        return formatXml.toString();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws SAXException
     */
    public static String jsonToXml(JSONObject json) throws SAXException {
        return jsonToDocument(json).asXML();
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws SAXException
     */
    public static Document jsonToDocument(JSONObject json) throws SAXException {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(ENCODING);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Element root = jsonToElement(json.getJSONObject(rootKey), rootKey);
            document.add(root);
            break;
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json     JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    public static Element jsonToElement(JSONObject json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);
        for (String key : json.keySet()) {
            Object child = json.get(key);
            if (child instanceof JSONObject) {
                node.add(jsonToElement(json.getJSONObject(key), key));
            } else {
                Element element = DocumentHelper.createElement(key);
                element.setText(json.getString(key));
                node.add(element);
            }
        }

        return node;
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws DocumentException
     */
    public static JSONObject xmlToJson(String xml) throws DocumentException {
        JSONObject json = new JSONObject();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();

        json.put(root.getName(), elementToJson(root));

        return json;
    }

    /**
     * Element对象转JSON对象
     *
     * @param element Element对象
     * @return JSON对象
     */
    public static JSONObject elementToJson(Element element) {
        JSONObject json = new JSONObject();
        for (Object child : element.elements()) {
            Element e = (Element) child;
            if (e.elements().isEmpty()) {
                json.put(e.getName(), e.getText());
            } else {
                json.put(e.getName(), elementToJson(e));
            }
        }

        return json;
    }

    /**
     * 将json字符串转换成xml
     *
     * @param json          json字符串
     * @param parentElement xml根节点
     * @throws Exception
     */
    public static Element jsonToXml(String json, Element parentElement) throws Exception {
        JSONObject jsonObject = JSON.parseObject(json);
        return toXml(jsonObject, parentElement, null);
    }

    /**
     * 将json字符串转换成xml
     *
     * @param jsonElement   待解析json对象元素
     * @param parentElement 上一层xml的dom对象
     * @param name          父节点
     */
    public static Element toXml(Object jsonElement, Element parentElement, String name) {
        if (jsonElement instanceof JSONArray) {
            //是json数据，需继续解析
            JSONArray sonJsonArray = (JSONArray) jsonElement;
            for (int i = 0; i < sonJsonArray.size(); i++) {
                Object arrayElement = sonJsonArray.get(i);
                toXml(arrayElement, parentElement, name);
            }
        } else if (jsonElement instanceof JSONObject) {
            //说明是一个json对象字符串，需要继续解析
            JSONObject sonJsonObject = (JSONObject) jsonElement;
            Element currentElement = null;
            if (name != null) {
                currentElement = parentElement.addElement(name);
            }
            Set<Map.Entry<String, Object>> set = sonJsonObject.entrySet();
            for (Map.Entry<String, Object> s : set) {
                toXml(s.getValue(), currentElement != null ? currentElement : parentElement, s.getKey());
            }
        } else {
            //说明是一个键值对的key,可以作为节点插入了
            addAttribute(parentElement, name, jsonElement.toString());
        }
        return parentElement;
    }

    /**
     * @param element 父节点
     * @param name    子节点的名字
     * @param value   子节点的值
     */
    public static void addAttribute(Element element, String name, String value) {
        //增加子节点，并为子节点赋值
        Element el = element.addElement(name);
        el.addText(value);
    }
}

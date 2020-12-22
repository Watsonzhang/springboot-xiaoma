package com.xiaoma.zhangwei.xml.hubUtil;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class XMLUtil {

	//获得操作xml文件的对象  
    public static Document getDocument(String file) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//得到创建 DOM 解析器的工厂。
        factory.setNamespaceAware(true);
                
        DocumentBuilder builder = factory.newDocumentBuilder();//得到 DOM 解析器对象。
        Document document = builder.parse(new File(file)); //得到代表整个文档的 Document 对象
                  
        return document;  
    }  
    
	
}

package com.xiaoma.zhangwei.yzf;

import com.xiaoma.zhangwei.xml.hubUtil.DomUtil;
import com.xiaoma.zhangwei.xml.hubUtil.XMLUtil;
import com.xiaoma.zhangwei.xml.self.PathUtil;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/23 上午9:30
 */
public class OFDParser {

    public static final String docNS = "http://www.ofdspec.org/2016";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Document document = XMLUtil.getDocument(PathUtil.getYzfOFdXMLPath());
        Element e = document.getDocumentElement();
        System.out.println(e);
        NodeList customDataNodeList = document.getElementsByTagNameNS(docNS, "CustomData");
        Map<String,String> key2TextMap = new HashMap<>();
        for(int i=0;i<customDataNodeList.getLength();i++)
        {
            Node customerDataNode = customDataNodeList.item(i);
            String innerText = customerDataNode.getTextContent();
            NamedNodeMap attributes = customerDataNode.getAttributes();
            Node name = attributes.getNamedItem("Name");
            key2TextMap.put(name.getNodeValue(),innerText);
        }
        System.out.println(key2TextMap);

    }
}

package com.xiaoma.zhangwei.xml;

import com.xiaoma.zhangwei.xml.util.OfdXmlUtil;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/21 下午3:45
 */
public class TestDom4J {
    public static void main(String[] args) throws Exception {
        //File file = new File("/home/yzf/tax/xml/test.xml");
        //createDom4j(file);
        //readXml("/home/yzf/tax/xml/test.xml");
        String attr = OfdXmlUtil.getAttributeIdByPath("/home/yzf/tax/xml/test.xml", "attr");
        System.out.println(attr);
    }

    public static void createDom4j(File file) {
        try {
            // 创建一个Document实例
            Document doc = DocumentHelper.createDocument();

            // 添加根节点
            Element root = doc.addElement("root");

            // 在根节点下添加第一个子节点
            Element oneChildElement= root.addElement("person").addAttribute("attr", "root noe");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person one child one");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person one child two");

            // 在根节点下添加第一个子节点
            Element twoChildElement= root.addElement("person").addAttribute("attr", "root two");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person two child one");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person two child two");

            // xml格式化样式
            // OutputFormat format = OutputFormat.createPrettyPrint(); // 默认样式

            // 自定义xml样式
            OutputFormat format = new OutputFormat();
            format.setIndentSize(2);  // 行缩进
            format.setNewlines(true); // 一个结点为一行
            format.setTrimText(true); // 去重空格
            format.setPadText(true);
            format.setNewLineAfterDeclaration(false); // 放置xml文件中第二行为空白行

            // 输出xml文件
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(doc);
            System.out.println("dom4j CreateDom4j success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readXml(String xmlPath){
        File file = new File(xmlPath);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Element foo;
            Iterator i = root.elementIterator("person");
            while (i.hasNext()){
                foo = (Element)i.next();
                List<Attribute> attributes = foo.attributes();
                Map<String, String> collect = attributes.stream().collect(Collectors.toMap(Attribute::getName, Attribute::getValue));
                System.out.println(collect);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}

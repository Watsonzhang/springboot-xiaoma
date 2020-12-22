package com.xiaoma.zhangwei.xml.parser;

import com.xiaoma.zhangwei.xml.entity.DocBody;
import com.xiaoma.zhangwei.xml.entity.DocInfo;
import com.xiaoma.zhangwei.xml.entity.OFD;
import com.xiaoma.zhangwei.xml.hubUtil.DomUtil;
import com.xiaoma.zhangwei.xml.hubUtil.XMLUtil;
import com.xiaoma.zhangwei.xml.self.PathUtil;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OFDParser {

	public static final String docNS = "http://www.ofdspec.org/2016";
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException {
		// TODO Auto-generated method stub
		OFD ofd=makeOFD(PathUtil.getRZOfdXMLPath());
		System.out.println(ofd.getDocBodies());
	}
	
	
    
	// 解析ofdxml文件获取OFD结构体信息
	public static OFD makeOFD(String ofdxml) throws ParserConfigurationException, SAXException, IOException, ParseException
	{
		Document document = XMLUtil.getDocument(ofdxml);
		Element e = document.getDocumentElement();
		
		
		OFD ofd=new OFD();
		
		NamedNodeMap attrMap=e.getAttributes();
		
		System.out.println(attrMap.getNamedItem("Version").getNodeValue());
		ofd.setVersion(attrMap.getNamedItem("Version").getNodeValue());
		
		System.out.println(attrMap.getNamedItem("DocType").getNodeValue());
		ofd.setDocType(attrMap.getNamedItem("DocType").getNodeValue());
		
		NodeList list = document.getElementsByTagNameNS(docNS, "DocBody");
		Set<DocBody> docBodies=new HashSet<DocBody>();
		
		System.out.println(list.getLength());
		
		for(int i=0;i<list.getLength();i++)
		{
			Node docBodyNode = list.item(i);
			docBodies.add(makeDocBody(docBodyNode));
		}
		
		ofd.setDocBodies(docBodies);

		return ofd;
	}
	
	private static DocBody makeDocBody(Node node) throws ParseException
	{
		DocBody docBody=new DocBody();
		
		List<Node> list= DomUtil.getElementsByTagNameNS(node, docNS, "DocInfo");
		Node docInfoNode=list.get(0);
		
		DocInfo docInfo=makeDocInfo(docInfoNode);
		docBody.setDocInfo(docInfo);
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "DocRoot");
		if(list.size()>0)
		{
			Node docRootNode=list.get(0);
			String docRoot=docRootNode.getTextContent();
			
			System.out.println(docRoot);
			docBody.setDocRoot(docRoot);
		}
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "Signatures");
		if(list.size()>0)
		{
			Node signaturesNode=list.get(0);
			String signatures=signaturesNode.getTextContent();
			
			System.out.println(signatures);
			docBody.setSignatures(signatures);
		}
		
		return docBody;
	}
	
	private static DocInfo makeDocInfo( Node node) throws ParseException
	{
		DocInfo docInfo=new DocInfo(); 
		
		List<Node> list=DomUtil.getElementsByTagNameNS(node, docNS, "DocID");
		if(list.size()>0)
		{
			Node docIDNode=list.get(0);
			String docID=docIDNode.getTextContent();
			
			System.out.println(docID);
			docInfo.setDocID(docID);
		}
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "Title");
		if(list.size()>0)
		{
			Node titleNode=list.get(0);
			String title=titleNode.getTextContent();
			
			System.out.println(title);
			docInfo.setTitle(title);
		}
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "Creator");
		if(list.size()>0)
		{
			Node creatorNode=list.get(0);
			String creator=creatorNode.getTextContent();
			
			System.out.println(creator);
			docInfo.setCreator(creator);
		}
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "CreationDate");
		if(list.size()>0)
		{
			Node creationDateNode=list.get(0);
			String creationDate=creationDateNode.getTextContent();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(creationDate);
			
			System.out.println(date);
			docInfo.setCreationDate(date);
		}
		
		list=DomUtil.getElementsByTagNameNS(node, docNS, "ModDate");
		if(list.size()>0)
		{
			Node modDateNode=list.get(0);
			String modDate=modDateNode.getTextContent();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(modDate);
			
			System.out.println(date);
			docInfo.setModDate(date);
		}
		
		return docInfo;
	}
	
	
	
}



















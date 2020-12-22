package com.xiaoma.zhangwei.xml.parser;

import com.xiaoma.zhangwei.xml.entity.*;
import com.xiaoma.zhangwei.xml.hubUtil.DomUtil;
import com.xiaoma.zhangwei.xml.hubUtil.XMLUtil;
import com.xiaoma.zhangwei.xml.self.PathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentParser {

    public static final String docNS = "http://www.ofdspec.org/2016";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // TODO Auto-generated method stub
        com.xiaoma.zhangwei.xml.entity.Document doc = makeDocument(PathUtil.getRZContentXMLPath());

        System.out.println(doc.getPages().size());
    }


    public static com.xiaoma.zhangwei.xml.entity.Document makeDocument(String docRoot) throws ParserConfigurationException, SAXException, IOException {
        com.xiaoma.zhangwei.xml.entity.Document doc = new com.xiaoma.zhangwei.xml.entity.Document();

        Document document = XMLUtil.getDocument(docRoot);
        Element e = document.getDocumentElement();

        //System.out.println(e.getNodeName());

        List<Node> list = DomUtil.getElementsByTagNameNS(e, docNS, "CommonData");
        doc.setCommonData(makeCommonData(list.get(0)));

        list = DomUtil.getElementsByTagNameNS(e, docNS, "Pages");
        list = DomUtil.getElementsByTagNameNS(list.get(0), docNS, "Page");
        List<PageNode> pages = new ArrayList<PageNode>();
        for (int i = 0; i < list.size(); i++) {
            PageNode pageNode = makePageNode(list.get(i));
            pages.add(pageNode);
        }

        doc.setPages(pages);
        return doc;
    }

    private static CommonData makeCommonData(Node node) {
        CommonData commonData = new CommonData();

        List<Node> list = DomUtil.getElementsByTagNameNS(node, docNS, "MaxUnitID");
        commonData.setMaxUnitID(list.get(0).getTextContent());

        list = DomUtil.getElementsByTagNameNS(node, docNS, "PageArea");
        commonData.setPageArea(makePageArea(list.get(0)));

        //list = DomUtil.getElementsByTagNameNS(node, docNS, "PublicRes");
        list = DomUtil.getElementsByTagNameNS(node, docNS, "DocumentRes");
        if (list.size() > 0)
            commonData.setPublicRes(list.get(0).getTextContent());

        return commonData;
    }

    public static PageArea makePageArea(Node node) {
        PageArea pageArea = new PageArea();

        List<Node> list = DomUtil.getElementsByTagNameNS(node, docNS, "PhysicalBox");
        pageArea.setPhysicalBox(new Box(list.get(0).getTextContent()));

        list = DomUtil.getElementsByTagNameNS(node, docNS, "ApplicationBox");
        if (list.size() > 0)
            pageArea.setApplicationBox(new Box(list.get(0).getTextContent()));

        list = DomUtil.getElementsByTagNameNS(node, docNS, "ContentBox");
        if (list.size() > 0)
            pageArea.setContentBox(new Box(list.get(0).getTextContent()));

        list = DomUtil.getElementsByTagNameNS(node, docNS, "BleedBox");
        if (list.size() > 0)
            pageArea.setBleedBox(new Box(list.get(0).getTextContent()));

        return pageArea;
    }

    private static PageNode makePageNode(Node node) {
        PageNode pageNode = new PageNode();

        NamedNodeMap attrMap = node.getAttributes();

        pageNode.setId(attrMap.getNamedItem("ID").getNodeValue());
        pageNode.setBaseLoc(attrMap.getNamedItem("BaseLoc").getNodeValue());

        return pageNode;
    }

    private static OutlineElem makeOutlineElem(Node node) {
        OutlineElem outlineElem = new OutlineElem();

        NamedNodeMap attrMap = node.getAttributes();

        outlineElem.setTitle(attrMap.getNamedItem("Title").getNodeValue());

        if (attrMap.getNamedItem("Count") != null)
            outlineElem.setCount(Integer.parseInt(attrMap.getNamedItem("Count").getNodeValue()));

        if (attrMap.getNamedItem("Expanded") != null)
            outlineElem.setExpanded(Boolean.parseBoolean(attrMap.getNamedItem("Expanded").getNodeValue()));


        List<Node> list = DomUtil.getElementsByTagName(node, "Actions");
        List<Action> actions = new ArrayList<>();
        if (list.size() > 0) {
            list = DomUtil.getElementsByTagName(list.get(0), "Action");

            for (int i = 0; i < list.size(); i++) {
                Action action = makeAction(list.get(i));
                actions.add(action);
            }
        }

        outlineElem.setActions(actions);


        return outlineElem;
    }

    private static Action makeAction(Node node) {
        Action action = new Action();

        NamedNodeMap attrMap = node.getAttributes();

        if (attrMap.getNamedItem("Event").getNodeValue().equals("CLICK"))
            action.setEvent(EventType.CLICK);
        else if (attrMap.getNamedItem("Event").getNodeValue().equals("DO"))
            action.setEvent(EventType.DO);
        else
            action.setEvent(EventType.PO);

        List<Node> list = DomUtil.getElementsByTagName(node, "Goto");
        if (list.size() > 0) {
            Goto gt = makeGoto(list.get(0));
            action.setAct(gt);
        }

        return action;
    }

    private static Goto makeGoto(Node node) {
        Goto gt = new Goto();

        List<Node> list = DomUtil.getElementsByTagName(node, "Dest");
        if (list.size() > 0) {
            gt.setDest(makeDest(list.get(0)));
        }

        list = DomUtil.getElementsByTagName(node, "Bookmark");
        if (list.size() > 0) {
            gt.setBookmark(makeBookmark(list.get(0)));
        }

        return gt;
    }

    private static Dest makeDest(Node node) {
        Dest dest = new Dest();

        NamedNodeMap attrMap = node.getAttributes();

        dest.setType(attrMap.getNamedItem("Type").getNodeValue());
        dest.setPageID(attrMap.getNamedItem("PageID").getNodeValue());

        if (attrMap.getNamedItem("Left") != null) {
            dest.setLeft(Double.parseDouble(attrMap.getNamedItem("Left").getNodeValue()));
        }

        if (attrMap.getNamedItem("Right") != null) {
            dest.setRight(Double.parseDouble(attrMap.getNamedItem("Right").getNodeValue()));
        }

        if (attrMap.getNamedItem("Top") != null) {
            dest.setTop(Double.parseDouble(attrMap.getNamedItem("Top").getNodeValue()));
        }

        if (attrMap.getNamedItem("Bottom") != null) {
            dest.setBottom(Double.parseDouble(attrMap.getNamedItem("Bottom").getNodeValue()));
        }

        if (attrMap.getNamedItem("Zoom") != null) {
            dest.setZoom(Double.parseDouble(attrMap.getNamedItem("Zoom").getNodeValue()));
        }

        return dest;
    }

    private static Bookmark makeBookmark(Node node) {
        Bookmark bookmark = new Bookmark();

        NamedNodeMap attrMap = node.getAttributes();

        bookmark.setName(attrMap.getNamedItem("Name").getNodeValue());

        List<Node> list = DomUtil.getElementsByTagName(node, "Dest");
        bookmark.setDest(makeDest(list.get(0)));

        return bookmark;
    }

}







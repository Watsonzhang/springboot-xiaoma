package com.xiaoma.zhangwei.xml.parser;

import com.xiaoma.zhangwei.xml.hubUtil.XMLUtil;
import com.xiaoma.zhangwei.xml.invoice.Invoice;
import com.xiaoma.zhangwei.xml.self.PathUtil;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/22 下午3:12
 */
public class InvoiceParser {

    public static final String docNS = "http://www.edrm.org.cn/schema/e-invoice/2019";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        makeInvoice(PathUtil.getInvoiceXMLPath());
    }

    private static void makeInvoice(String invoiceXMLPath) throws IOException, SAXException, ParserConfigurationException {
        Document document = XMLUtil.getDocument(invoiceXMLPath);
        Element e = document.getDocumentElement();
        NamedNodeMap attributes = e.getAttributes();
        String docID = getFlattenValueByLabel(document, docNS, "DocID");
        String invoiceCode = getFlattenValueByLabel(document, docNS, "InvoiceCode");
        String invoiceNo = getFlattenValueByLabel(document, docNS, "InvoiceNo");
        String issueDate = getFlattenValueByLabel(document, docNS, "IssueDate");
        String invoiceCheckCode = getFlattenValueByLabel(document, docNS, "InvoiceCheckCode");
        String machineNo = getFlattenValueByLabel(document, docNS, "MachineNo");
        String taxControlCode = getFlattenValueByLabel(document, docNS, "TaxControlCode");
        String taxInclusiveTotalAmount = getFlattenValueByLabel(document, docNS, "TaxInclusiveTotalAmount");
        String invoiceClerk = getFlattenValueByLabel(document, docNS, "InvoiceClerk");
        String payee = getFlattenValueByLabel(document, docNS, "Payee");
        String checker = getFlattenValueByLabel(document, docNS, "Checker");
        String taxTotalAmount = getFlattenValueByLabel(document, docNS, "TaxTotalAmount");
        String taxExclusiveTotalAmount = getFlattenValueByLabel(document, docNS, "TaxExclusiveTotalAmount");
        String graphCode = getFlattenValueByLabel(document, docNS, "GraphCode");
        String signature = getFlattenValueByLabel(document, docNS, "Signature");
        Invoice invoice = Invoice.builder().docID(docID).invoiceCode(invoiceCode)
                .invoiceNo(invoiceNo)
                .issueDate(issueDate)
                .invoiceCheckCode(invoiceCheckCode)
                .machineNo(machineNo)
                .taxControlCode(taxControlCode)
                .taxInclusiveTotalAmount(taxInclusiveTotalAmount)
                .invoiceClerk(invoiceClerk)
                .payee(payee)
                .checker(checker)
                .taxTotalAmount(taxTotalAmount)
                .taxExclusiveTotalAmount(taxExclusiveTotalAmount)
                .graphCode(graphCode)
                .signature(signature)
                .build();
        System.out.println(invoice);

    }

    private static String getFlattenValueByLabel(Document document, String docNS, String labelName) {
        NodeList list = document.getElementsByTagNameNS(docNS,labelName);
       if(list.getLength()>0 ){
           return list.item(0).getTextContent();
       }
        return null;
    }
}

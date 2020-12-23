package com.xiaoma.zhangwei.xml.parser;

import com.xiaoma.zhangwei.xml.hubUtil.DomUtil;
import com.xiaoma.zhangwei.xml.hubUtil.XMLUtil;
import com.xiaoma.zhangwei.xml.invoice.Invoice;
import com.xiaoma.zhangwei.xml.self.PathUtil;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/22 下午3:12
 */
public class InvoiceParser {

    public static final String docNS = "http://www.edrm.org.cn/schema/e-invoice/2019";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //makeInvoice(PathUtil.getInvoiceXMLPath());
        makeInvoice(PathUtil.getYZFInvoiceXMLPath());
    }

    private static void makeInvoice(String invoiceXMLPath) throws IOException, SAXException, ParserConfigurationException {
        Document document = XMLUtil.getDocument(invoiceXMLPath);
        Element e = document.getDocumentElement();
        Invoice invoice= buildBasicInvoice(document,docNS);
        List<Invoice.GoodsInfo> goodsInfos = buildGoodsInfos(document, docNS, "GoodsInfo");
        Invoice.Seller seller = buildSeller(document, docNS, "Seller");
        Invoice.Buyer buyer = buildBuyer(document, docNS, "Buyer");
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setGoodsInfos(goodsInfos);
        System.out.println(invoice);
    }

    private static Invoice buildBasicInvoice(Document document, String docNS) {
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
        return Invoice.builder().docID(docID).invoiceCode(invoiceCode)
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
    }

    private static Invoice.Seller buildSeller(Document document, String docNS, String seller) {
        NodeList list = document.getElementsByTagNameNS(docNS, seller);
        if (list.getLength() > 0) {
            Node sellerSingle = list.item(0);
            String sellerName = getFlattenValueByNodeLabel(sellerSingle, docNS, "SellerName");
            String sellerTaxID = getFlattenValueByNodeLabel(sellerSingle, docNS, "SellerTaxID");
            String sellerAddrTel = getFlattenValueByNodeLabel(sellerSingle, docNS, "SellerAddrTel");
            String sellerFinancialAccount = getFlattenValueByNodeLabel(sellerSingle, docNS, "SellerFinancialAccount");
            return Invoice.Seller.builder()
                    .sellerName(sellerName)
                    .sellerFinancialAccount(sellerFinancialAccount)
                    .sellerTaxID(sellerTaxID)
                    .sellerAddrTel(sellerAddrTel)
                    .build();

        }
        return null;

    }

    private static Invoice.Buyer buildBuyer(Document document, String docNS, String label) {
        NodeList list = document.getElementsByTagNameNS(docNS, label);
        if (list.getLength() > 0) {
            Node buyerSingle = list.item(0);
            String buyerName = getFlattenValueByNodeLabel(buyerSingle, docNS, "BuyerName");
            String buyerTaxID = getFlattenValueByNodeLabel(buyerSingle, docNS, "BuyerTaxID");
            String buyerAddrTel = getFlattenValueByNodeLabel(buyerSingle, docNS, "BuyerAddrTel");
            return Invoice.Buyer.builder()
                    .buyerName(buyerName)
                    .buyerTaxID(buyerTaxID)
                    .buyerAddrTel(buyerAddrTel)
                    .build();
        }
        return null;

    }

    private static List<Invoice.GoodsInfo> buildGoodsInfos(Document document, String docNS, String goodsInfoTagName) {
        ArrayList<Invoice.GoodsInfo> goodsInfos = new ArrayList<>();
        NodeList list = document.getElementsByTagNameNS(docNS, goodsInfoTagName);
        System.out.println(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            Node goodsInfo = list.item(i);
            String item = getFlattenValueByNodeLabel(goodsInfo, docNS, "Item");
            String specification = getFlattenValueByNodeLabel(goodsInfo, docNS, "Specification");
            String measurementDimension = getFlattenValueByNodeLabel(goodsInfo, docNS, "MeasurementDimension");
            String price = getFlattenValueByNodeLabel(goodsInfo, docNS, "Price");
            String quantity = getFlattenValueByNodeLabel(goodsInfo, docNS, "Quantity");
            String amount = getFlattenValueByNodeLabel(goodsInfo, docNS, "Amount");
            String taxScheme = getFlattenValueByNodeLabel(goodsInfo, docNS, "TaxScheme");
            String taxAmount = getFlattenValueByNodeLabel(goodsInfo, docNS, "TaxAmount");
            Invoice.GoodsInfo build = Invoice.GoodsInfo.builder()
                    .Item(item)
                    .Specification(specification)
                    .MeasurementDimension(measurementDimension)
                    .Price(price)
                    .Quantity(quantity)
                    .Amount(amount)
                    .TaxAmount(taxAmount)
                    .TaxScheme(taxScheme)
                    .build();
            goodsInfos.add(build);
        }
        return goodsInfos;
    }

    private static String getFlattenValueByLabel(Document document, String docNS, String labelName) {
        NodeList list = document.getElementsByTagNameNS(docNS, labelName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return null;
    }

    private static String getFlattenValueByNodeLabel(Node node, String docNS, String labelName) {
        List<Node> list = DomUtil.getFPElementsByTagNameNS(node, docNS, labelName);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getTextContent();
        }
        return null;
    }
}

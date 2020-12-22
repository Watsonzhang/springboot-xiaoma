package com.xiaoma.zhangwei.xml.invoice;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/22 下午3:46
 */
@Builder
@Data
public class Invoice {
    private String docID;
    private String invoiceCode;
    private String invoiceNo;
    private String issueDate;
    private String invoiceCheckCode;
    private String machineNo;
    private String taxControlCode;
    private String taxInclusiveTotalAmount;
    private String invoiceClerk;
    private String payee;
    private String checker;
    private String taxTotalAmount;
    private String taxExclusiveTotalAmount;
    private String graphCode;
    private String signature;
    private Seller seller;
    private Buyer buyer;
    private List<GoodsInfo> goodsInfos;

    @Data
    public static class Seller{
        private String sellerName;
        private String sellerTaxID;
        private String sellerAddrTel;
        private String sellerFinancialAccount;
    }
    @Data
    public static class Buyer{
        private String buyerName;
        private String buyerTaxID;
        private String buyerAddrTel;
        private String buyerFinancialAccount;
    }

    @Data
    public static class GoodsInfo{
        private String Item;
        private String Specification;
        private String MeasurementDimension;
        private String Price;
        private String Quantity;
        private String Amount;
        private String TaxScheme;
        private String TaxAmount;

    }
}

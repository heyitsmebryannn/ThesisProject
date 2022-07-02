package com.report;

public record TransactionDetail(String productID, String productName, int productPrice,
                                double productQuantity, int totalAmount,String warranty,String warrantyDuration,String warrantyStatus) {

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getWarranty(){
        return  warranty;
    }
    public String getWarrantyDuration(){
        return warrantyDuration;
    }
    public String getWarrantyStatus(){
        return  warrantyStatus;
    }
}

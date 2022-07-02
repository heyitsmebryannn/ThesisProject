package com.productInfo;


public record Products(String productID,
                       String productName,
                       String productBrand,
                       String productType,
                       String productSize,
                       String productLength,
                       String sizeType,
                       double productQuantity,
                       String quantityType,
                       double productPrice,

                       double amount,
                       String productDescription,
                       byte[] image,
                       String hasWarranty,
                       String dateAdded) {

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductSize() {
        return productSize;
    }

    public String getProductLength() {
        return productLength;
    }

    public String getSizeType(){ return sizeType ;}

    public double getProductQuantity() {
        return productQuantity;
    }

    public String getQuantityType(){ return quantityType; }

    public double getProductPrice(){ return productPrice; }

    public double getAmount(){
        return  amount;
    }

    public String getProductDescription(){ return productDescription; }

    public byte[] getImage() {
        return image;
    }

    public String getHasWarranty(){
        return hasWarranty;
    }

    @Override
    public String dateAdded() {
       return dateAdded;
    }
}

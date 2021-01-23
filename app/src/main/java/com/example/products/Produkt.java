package com.example.products;

public class Produkt {
    String productName, productType, count;

    public Produkt(String productName, String productType, String count) {
        this.productName = productName;
        this.productType = productType;
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

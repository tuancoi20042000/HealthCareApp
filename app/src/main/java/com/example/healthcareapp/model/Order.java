package com.example.healthcareapp.model;

public class Order {
    private int productId;
    private String productName;
    private int imgID;
    private int totalBill;
    private String status;
    private String confirm;

    public Order() {
    }

    public Order(int productId, String productName, int imgID, int totalBill, String status, String confirm) {
        this.productId = productId;
        this.productName = productName;
        this.imgID = imgID;
        this.totalBill = totalBill;
        this.status = status;
        this.confirm = confirm;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}

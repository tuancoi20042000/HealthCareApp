package com.example.healthcareapp.model;

public class Product {
    private int productId;
    private String productName;
    private int imgID;
    private double price;
    private String description;
    private int quantity;

    public Product() {
    }

    public Product(int productId, String productName, int imgID, double price, String description, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.imgID = imgID;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

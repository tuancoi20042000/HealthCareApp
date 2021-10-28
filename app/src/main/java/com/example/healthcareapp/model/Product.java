package com.example.healthcareapp.model;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {
    private  String pID;
    private String pName;
    private String pDescription;
    private float pPrice;
    private int pQuantity;
    private String pImage;

    public Product() {
    }
    public Product(String pID, String pName, String pDescription, float pPrice, int pQuantity, String pImage) {
        this.pID = pID;
        this.pName = pName;
        this.pDescription = pDescription;
        this.pPrice = pPrice;
        this.pQuantity = pQuantity;
        this.pImage = pImage;
    }

    public Product(String pName, float pPrice, String pImage) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pImage = pImage;
    }

    public Product(String pID, String pName, String pDescription, float pPrice, String pImage) {
        this.pID = pID;
        this.pName = pName;
        this.pDescription = pDescription;
        this.pPrice = pPrice;
        this.pImage = pImage;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public float getpPrice() {
        return pPrice;
    }

    public void setpPrice(float pPrice) {
        this.pPrice = pPrice;
    }

    public int getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(int pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pID='" + pID + '\'' +
                ", pName='" + pName + '\'' +
                ", pDescription='" + pDescription + '\'' +
                ", pPrice=" + pPrice +
                ", pQuantity=" + pQuantity +
                ", pImage=" + pImage +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return (int) (this.pPrice - o.pPrice);
    }
}

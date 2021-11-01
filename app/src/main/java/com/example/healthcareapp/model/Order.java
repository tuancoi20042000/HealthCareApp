package com.example.healthcareapp.model;

public class Order {
    private String amount, orderCode, pID,pImage,pName, phone
            , shipAddress, shipDate,userName;
    private int status;
    private long quantity;

    public Order() {
    }

    public Order(String amount, String orderCode, String pID, String pImage, String pName, String phone, long quantity, String shipAddress, String shipDate, String userName, int status) {
        this.amount = amount;
        this.orderCode = orderCode;
        this.pID = pID;
        this.pImage = pImage;
        this.pName = pName;
        this.phone = phone;
        this.quantity = quantity;
        this.shipAddress = shipAddress;
        this.shipDate = shipDate;
        this.userName = userName;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

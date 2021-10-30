package com.example.healthcareapp.model;

public class Order {
    private Product product;
    private int status;
    private int totalBill;

    public Order(Product product, int status, int totalBill) {
        this.product = product;
        this.status = status;
        this.totalBill = totalBill;
    }

    public Order() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStatus() {
        if(status == 0){
            return "notyet";
        }

        return "done";
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }
}

package com.example.healthcareapp.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private String cartID;
    private Users user;
    private int numOfQuan;
    private Product product;

    public Cart() {
    }

    public Cart(int numOfQuan, Product product) {
        this.numOfQuan = numOfQuan;
        this.product = product;
    }

    public Cart(Users user, int numOfQuan, Product product) {
        this.user = user;
        this.numOfQuan = numOfQuan;
        this.product = product;
    }

    public int getNumOfQuan() {
        return numOfQuan;
    }

    public void setNumOfQuan(int numOfQuan) {
        this.numOfQuan = numOfQuan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Cart(String cartID, Users user, int numOfQuan, Product product) {
        this.cartID = cartID;
        this.user = user;
        this.numOfQuan = numOfQuan;
        this.product = product;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", user=" + user +
                ", numOfQuan=" + numOfQuan +
                ", product=" + product +
                '}';
    }
}

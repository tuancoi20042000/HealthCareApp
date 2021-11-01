package com.example.healthcareapp.model;

public class Users {
    private String id;
    private String Email;
    private String Avatar;
    private String Address;

    public Users() {
    }

    public Users(String id, String email, String avatar) {
        this.id = id;
        Email = email;
        Avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Users(String id, String email, String avatar, String address) {
        this.id = id;
        Email = email;
        Avatar = avatar;
        Address = address;
    }
}

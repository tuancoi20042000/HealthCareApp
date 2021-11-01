package com.example.healthcareapp.model;

public class Hospital implements Comparable<Hospital>{
    private String name;
    private double lat;
    private double lng;
    private String phone;
    private double distance;

    public Hospital() {
        this.distance = 0;
    }

    public Hospital(String name, double lat, double lng, String phone) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
    }

    public Hospital(String name, double lat, double lng, String phone, double distance) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Hospital o) {
        return (int) (this.distance-o.distance);
    }
}

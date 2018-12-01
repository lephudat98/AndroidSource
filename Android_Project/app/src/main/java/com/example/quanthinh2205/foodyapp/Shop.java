package com.example.quanthinh2205.foodyapp;

import java.io.Serializable;

public class Shop implements Serializable {
    private int id;
    private int type;
    private String name;
    private String picIcon;
    private String pic1;
    private String pic2;
    private String street;
    private String county;
    private int likes;
    private int dislikes;
    private String menu;
    private int seats;
    private String phone;
    private String email;
    private String username;
    private String password;
    private double distance;

    public Shop(int id, int type, String name, String picIcon, String pic1, String pic2, String street, String county, int likes, int dislikes, String menu, int seats, String phone, String email, String username, String password) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.picIcon = picIcon;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.street = street;
        this.county = county;
        this.likes = likes;
        this.dislikes = dislikes;
        this.menu = menu;
        this.seats = seats;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicIcon() {
        return picIcon;
    }

    public void setPicIcon(String picIcon) {
        this.picIcon = picIcon;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

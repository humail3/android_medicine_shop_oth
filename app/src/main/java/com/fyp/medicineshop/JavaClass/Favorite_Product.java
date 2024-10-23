package com.fyp.medicineshop.JavaClass;

public class Favorite_Product {
    public String pid,image,date,time,price,name,description;

    public Favorite_Product() {
    }


    public Favorite_Product(String pid, String image,String price, String date, String time, String name, String description) {
        this.pid = pid;
        this.image = image;
        this.date = date;
        this.price=price;
        this.time = time;
        this.name = name;
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

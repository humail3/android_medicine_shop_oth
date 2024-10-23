package com.fyp.medicineshop.JavaClass;

public class CartProduct {
    public String pid,image,date,time,price,name,pcode,userID;

    public CartProduct() {
    }

    public CartProduct(String pid, String image, String date, String time ,String price, String name, String pcode, String userID) {
        this.pid = pid;
        this.image = image;
        this.date = date;
        this.time = time;
         this.price=price;
        this.name = name;
        this.pcode = pcode;
        this.userID = userID;
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

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

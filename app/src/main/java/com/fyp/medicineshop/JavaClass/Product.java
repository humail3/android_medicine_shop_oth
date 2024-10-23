package com.fyp.medicineshop.JavaClass;

public class Product {
    public String pid,pcode,description,image,price,name,category;

    public Product() {
    }

    public Product(String pid,String pcode, String description, String image, String price, String name,String category) {
        this.pid = pid;
        this.pcode = pcode;
       // this.date = date;
        //this.time = time;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}



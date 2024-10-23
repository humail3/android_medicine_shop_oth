package com.fyp.medicineshop.JavaClass;

public class ConfirmOrders {

    public  String name,totalamount,productName,productCode,date,time,phone,address,city,state,deliveryDate;

    public ConfirmOrders() {
    }

    public ConfirmOrders(String name, String totalamount, String productName, String productCode, String date, String time, String phone, String address, String city, String state, String deliveryDate, String pickUpDate) {
        this.name = name;
        this.totalamount = totalamount;
        this.productName = productName;
        this.productCode = productCode;
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.deliveryDate = deliveryDate;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}

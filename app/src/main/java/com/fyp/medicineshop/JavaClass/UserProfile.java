package com.fyp.medicineshop.JavaClass;

public class UserProfile {
    public String userName,userEmail,userPhone,Ukey,currentDate,currentTime,imageUrl,UprofileName;


    public UserProfile() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUkey() {
        return Ukey;
    }

    public void setUkey(String ukey) {
        Ukey = ukey;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUprofileName() {
        return UprofileName;
    }

    public void setUprofileName(String uprofileName) {
        UprofileName = uprofileName;
    }

    public UserProfile(String userName, String userEmail, String userPhone, String ukey, String currentDate, String currentTime, String imageUrl, String uprofileName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        Ukey = ukey;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.imageUrl = imageUrl;
        UprofileName = uprofileName;

    }
}

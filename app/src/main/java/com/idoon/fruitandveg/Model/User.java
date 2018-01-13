package com.idoon.fruitandveg.Model;


public class User {
    private String name;
    private String password;
    private String Phone;
    private String isStaff;
    private String image;
    private String securecode;

    public User(){

    }

    public User(String name ,String password,String securecode){
        this.name = name;
        this.password = password;
        isStaff = "false";
        image = "";
        this.securecode = securecode;
    }
    //


    public String getSecureCode() {
        return securecode;
    }

    public void setSecureCode(String secureCode) {
        this.securecode = secureCode;
    }

    public String getImage() {
        return image;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

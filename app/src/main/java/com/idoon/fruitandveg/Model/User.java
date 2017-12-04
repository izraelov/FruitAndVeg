package com.idoon.fruitandveg.Model;

/**
 * Created by itayi on 02/12/2017.
 */

public class User {
    private String Name;
    private String Password;

    public User(){

    }

    public User(String name ,String password){
        Name = name;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

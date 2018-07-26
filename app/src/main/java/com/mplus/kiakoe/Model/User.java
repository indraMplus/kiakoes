package com.mplus.kiakoe.Model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String surname,firstname,gender,datebirth,username,email,password,country,constituency,mobileno;

    public User(int id, String surname, String firstname, String gender, String datebirth, String username, String email, String password, String country, String constituency, String mobileno ) {

        this.id = id;
        this.surname = surname;
        this.firstname = firstname;
        this.gender = gender;
        this.datebirth = datebirth;
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
        this.constituency = constituency;
        this.mobileno = mobileno;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(String datebirth) {
        this.datebirth = datebirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

//    public String getImages() {
//        return images;
//    }
//
//    public void setImages(String images) {
//        this.images =images;
//    }

}

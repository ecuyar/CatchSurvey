package com.example.cs;

public class UserPro {
    public String name, surname, username, password, mail, city, birthdate, point;

    public UserPro(String name, String surname, String username, String password, String mail, String city, String birthdate, String point) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.city = city;
        this.birthdate = birthdate;
        this.point = point;
    }

    public UserPro() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}


package com.example.cs;

public class UserApply {
    public String name,surname,age,job,mail,tel;

    UserApply(String name, String surname, String age, String job, String mail, String tel){
    }

    public UserApply(String username,String name,String surname,String password,String mail,String city,String birthdate) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.job = job;
        this.mail = mail;
        this.tel = tel;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

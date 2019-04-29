package com.mamour.myquiz.Model;

import io.realm.RealmObject;

public class Client extends RealmObject {
    String fistname;
    String lastname;
    String emailid;
    String password;
    String seconpassword;


    public String getFistname() {
        return fistname;
    }

    public void setFistname(String fistname) {
        this.fistname = fistname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeconpassword() {
        return seconpassword;
    }

    public void setSeconpassword(String seconpassword) {
        this.seconpassword = seconpassword;
    }

    @Override
    public String toString() {
        return "Client{" +
                "fistname='" + fistname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailid='" + emailid + '\'' +
                ", password='" + password + '\'' +
                ", seconpassword='" + seconpassword + '\'' +
                '}';
    }
}

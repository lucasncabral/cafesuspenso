package com.cafesuspenso.ufcg.cafesuspenso.Model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String description, image;
    private Date date;

    public Transaction(String description, String image, Date date){
        this.description = description;
        this.image = image;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

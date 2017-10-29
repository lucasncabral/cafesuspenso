package com.cafesuspenso.ufcg.cafesuspenso.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 25/07/2017.
 */
public class Product implements Parcelable{
    private int id;
    private double price;
    private String image, description, name;
    private boolean accepted;

    public Product(int id, double price, String image, String description, boolean accepted, String name) {
        this.id = id;
        this.price = price;
        this.image = image;
        this.description = description;
        this.accepted = accepted;
        this.name = name;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        price = in.readDouble();
        image = in.readString();
        description = in.readString();
        name = in.readString();
        accepted = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(price);
        parcel.writeString(image);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeByte((byte) (accepted ? 1 : 0));
    }
}

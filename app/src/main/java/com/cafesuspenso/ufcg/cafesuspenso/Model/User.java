package com.cafesuspenso.ufcg.cafesuspenso.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.cafesuspenso.ufcg.cafesuspenso.Enums.TypeUser;

/**
 * Created by Lucas on 16/06/2017.
 */

public class User implements Parcelable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private TypeUser typeUser;

    public User(String name, String email, String password, TypeUser typeUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.typeUser = typeUser;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeValue(typeUser);
    }

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        typeUser = (TypeUser) in.readValue(ClassLoader.getSystemClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

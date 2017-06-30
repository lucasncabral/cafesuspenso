package com.cafesuspenso.ufcg.cafesuspenso.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.cafesuspenso.ufcg.cafesuspenso.Enums.TypeUser;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lucas on 16/06/2017.
 */

public class Cafeteria extends User implements Parcelable {
    private String placename;
    private Boolean active;
    private String complement;
    private String cnpj;
    private LatLng location;
    private String imagem;
    private byte[] qrCodePic;
    private int availableCoffee;

    public Cafeteria(String name, String email, String password, String placename, String complement, String cnpj, LatLng location, String imagem) {
        super(name, email, password, TypeUser.CAFETERIA);
        this.placename = placename;
        this.complement = complement;
        this.cnpj = cnpj;
        this.location = location;
        this.imagem = imagem;
        this.active = Boolean.FALSE;
    }

    public Cafeteria() {
    }

    protected Cafeteria(Parcel in) {
        placename = in.readString();
        complement = in.readString();
        cnpj = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
        imagem = in.readString();
        qrCodePic = in.createByteArray();
        availableCoffee = in.readInt();
    }

    public static final Creator<Cafeteria> CREATOR = new Creator<Cafeteria>() {
        @Override
        public Cafeteria createFromParcel(Parcel in) {
            return new Cafeteria(in);
        }

        @Override
        public Cafeteria[] newArray(int size) {
            return new Cafeteria[size];
        }
    };

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public byte[] getQrCodePic() {
        return qrCodePic;
    }

    public void setQrCodePic(byte[] qrCodePic) {
        this.qrCodePic = qrCodePic;
    }

    public int getAvailableCoffee() {
        return availableCoffee;
    }

    public void setAvailableCoffee(int availableCoffee) {
        this.availableCoffee = availableCoffee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placename);
        parcel.writeString(complement);
        parcel.writeString(cnpj);
        parcel.writeParcelable(location, i);
        parcel.writeString(imagem);
        parcel.writeByteArray(qrCodePic);
        parcel.writeInt(availableCoffee);
    }
}
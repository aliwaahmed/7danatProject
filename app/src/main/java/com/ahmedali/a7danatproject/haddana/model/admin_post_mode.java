package com.ahmedali.a7danatproject.haddana.model;

import android.os.Parcel;
import android.os.Parcelable;

public class admin_post_mode implements Parcelable {
    private String  admin_name ;
    private String  admin_img  ;
    private String  admin_mail ;
    private String  name       ;
    private String phone       ;
    private String details     ;
    private String price       ;
    private String count       ;
    private String adresse     ;
    private String img1        ;
    private String img2        ;
    private String img3        ;
    private String enable ;

    public admin_post_mode() {
    }

    protected admin_post_mode(Parcel in) {
        admin_name = in.readString();
        admin_img = in.readString();
        admin_mail = in.readString();
        name = in.readString();
        phone = in.readString();
        details = in.readString();
        price = in.readString();
        count = in.readString();
        adresse = in.readString();
        img1 = in.readString();
        img2 = in.readString();
        img3 = in.readString();
        enable=in.readString();
    }

    public static final Creator<admin_post_mode> CREATOR = new Creator<admin_post_mode>() {
        @Override
        public admin_post_mode createFromParcel(Parcel in) {
            return new admin_post_mode(in);
        }

        @Override
        public admin_post_mode[] newArray(int size) {
            return new admin_post_mode[size];
        }
    };

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_img() {
        return admin_img;
    }

    public void setAdmin_img(String admin_img) {
        this.admin_img = admin_img;
    }

    public String getAdmin_mail() {
        return admin_mail;
    }

    public void setAdmin_mail(String admin_mail) {
        this.admin_mail = admin_mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(admin_name);
        dest.writeString(admin_img);
        dest.writeString(admin_mail);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(details);
        dest.writeString(price);
        dest.writeString(count);
        dest.writeString(adresse);
        dest.writeString(img1);
        dest.writeString(img2);
        dest.writeString(img3);
        dest.writeString(enable);
    }
}

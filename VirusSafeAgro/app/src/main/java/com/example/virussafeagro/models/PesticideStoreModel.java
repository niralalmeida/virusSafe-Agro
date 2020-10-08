package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PesticideStoreModel implements Parcelable {
    private LatLng locationLatLng;
    private String businessStatus;
    private String storeName;
    private double rating;
    private int userRatingsTotal;
    private List<String> storeTypeList;

    public PesticideStoreModel() {
    }

    public PesticideStoreModel(LatLng locationLatLng, String businessStatus, String storeName, double rating, int userRatingsTotal, List<String> storeTypeList) {
        this.locationLatLng = locationLatLng;
        this.businessStatus = businessStatus;
        this.storeName = storeName;
        this.rating = rating;
        this.userRatingsTotal = userRatingsTotal;
        this.storeTypeList = storeTypeList;
    }

    protected PesticideStoreModel(Parcel in) {
        locationLatLng = in.readParcelable(LatLng.class.getClassLoader());
        businessStatus = in.readString();
        storeName = in.readString();
        rating = in.readDouble();
        userRatingsTotal = in.readInt();
        storeTypeList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(locationLatLng, flags);
        dest.writeString(businessStatus);
        dest.writeString(storeName);
        dest.writeDouble(rating);
        dest.writeInt(userRatingsTotal);
        dest.writeStringList(storeTypeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PesticideStoreModel> CREATOR = new Creator<PesticideStoreModel>() {
        @Override
        public PesticideStoreModel createFromParcel(Parcel in) {
            return new PesticideStoreModel(in);
        }

        @Override
        public PesticideStoreModel[] newArray(int size) {
            return new PesticideStoreModel[size];
        }
    };

    public LatLng getLocationLatLng() {
        return locationLatLng;
    }

    public void setLocationLatLng(LatLng locationLatLng) {
        this.locationLatLng = locationLatLng;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(int userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public List<String> getStoreTypeList() {
        return storeTypeList;
    }

    public void setStoreTypeList(List<String> storeTypeList) {
        this.storeTypeList = storeTypeList;
    }
}

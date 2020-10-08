package com.example.virussafeagro.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PesticideStoreModel implements Parcelable {
    private String businessStatus; // [ must ] - OPERATIONAL, CLOSED_TEMPORARILY, CLOSED_PERMANENTLY
    private LatLng locationLatLng; // [ must ]
    private String storeName; // [ must ]
    private double rating;
    private int userRatingsCount;
    private List<String> storeTypeList;
    private boolean hasOpeningHours;
    private boolean isOpenNow;

    public PesticideStoreModel() {
    }

    public PesticideStoreModel(String storeName) {
        this.storeName = storeName;
    }

    public PesticideStoreModel(String businessStatus, LatLng locationLatLng, String storeName, double rating, int userRatingsCount, List<String> storeTypeList, boolean hasOpeningHours, boolean isOpenNow) {
        this.businessStatus = businessStatus;
        this.locationLatLng = locationLatLng;
        this.storeName = storeName;
        this.rating = rating;
        this.userRatingsCount = userRatingsCount;
        this.storeTypeList = storeTypeList;
        this.hasOpeningHours = hasOpeningHours;
        this.isOpenNow = isOpenNow;
    }

    protected PesticideStoreModel(Parcel in) {
        businessStatus = in.readString();
        locationLatLng = in.readParcelable(LatLng.class.getClassLoader());
        storeName = in.readString();
        rating = in.readDouble();
        userRatingsCount = in.readInt();
        storeTypeList = in.createStringArrayList();
        hasOpeningHours = in.readByte() != 0;
        isOpenNow = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(businessStatus);
        dest.writeParcelable(locationLatLng, flags);
        dest.writeString(storeName);
        dest.writeDouble(rating);
        dest.writeInt(userRatingsCount);
        dest.writeStringList(storeTypeList);
        dest.writeByte((byte) (hasOpeningHours ? 1 : 0));
        dest.writeByte((byte) (isOpenNow ? 1 : 0));
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

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public LatLng getLocationLatLng() {
        return locationLatLng;
    }

    public void setLocationLatLng(LatLng locationLatLng) {
        this.locationLatLng = locationLatLng;
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

    public int getUserRatingsCount() {
        return userRatingsCount;
    }

    public void setUserRatingsCount(int userRatingsCount) {
        this.userRatingsCount = userRatingsCount;
    }

    public List<String> getStoreTypeList() {
        return storeTypeList;
    }

    public void setStoreTypeList(List<String> storeTypeList) {
        this.storeTypeList = storeTypeList;
    }

    public boolean isHasOpeningHours() {
        return hasOpeningHours;
    }

    public void setHasOpeningHours(boolean hasOpeningHours) {
        this.hasOpeningHours = hasOpeningHours;
    }

    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }
}

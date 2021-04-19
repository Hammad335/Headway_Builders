package com.codewithhamad.headwaybuilders.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BuildingModel {

    private Bitmap buildingImage;
    private String buildingType;
    private int buildingId;
    private String buildingName;
    private int buildingAreaInSqFt;
    private int numbOfFlats;
    private int numbOfFloors;
    private int numbOfLifts;
    private int parkingAreaInSqFt;
    private String shortDetails;
    private String buildingLocation;
    private boolean isExpanded;

    public BuildingModel(Bitmap buildingImage, String buildingType, int buildingId, String buildingName, int buildingAreaInSqFt,
                         int numbOfFlats, int numbOfFloors, int numbOfLifts, int parkingAreaInSqFt, String shortDetails,
                         String buildingLocation) {

        this.buildingImage = buildingImage;
        this.buildingType = buildingType;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingAreaInSqFt = buildingAreaInSqFt;
        this.numbOfFlats = numbOfFlats;
        this.numbOfFloors = numbOfFloors;
        this.numbOfLifts = numbOfLifts;
        this.parkingAreaInSqFt = parkingAreaInSqFt;
        this.shortDetails = shortDetails;
        this.buildingLocation = buildingLocation;
        this.isExpanded = false;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingAreaInSqFt() {
        return buildingAreaInSqFt;
    }

    public void setBuildingAreaInSqFt(int buildingAreaInSqFt) {
        this.buildingAreaInSqFt = buildingAreaInSqFt;
    }

    public int getNumbOfFlats() {
        return numbOfFlats;
    }

    public void setNumbOfFlats(int numbOfFlats) {
        this.numbOfFlats = numbOfFlats;
    }

    public int getParkingAreaInSqFt() {
        return parkingAreaInSqFt;
    }

    public void setParkingAreaInSqFt(int parkingAreaInSqFt) {
        this.parkingAreaInSqFt = parkingAreaInSqFt;
    }

    public Bitmap getBuildingImage() {
        return buildingImage;
    }

    public void setBuildingImage(Bitmap buildingImage) {
        this.buildingImage = buildingImage;
    }

    public String getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(String shortDetails) {
        this.shortDetails = shortDetails;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public int getNumbOfFloors() {
        return numbOfFloors;
    }

    public void setNumbOfFloors(int numbOfFloors) {
        this.numbOfFloors = numbOfFloors;
    }

    public int getNumbOfLifts() {
        return numbOfLifts;
    }

    public void setNumbOfLifts(int numbOfLifts) {
        this.numbOfLifts = numbOfLifts;
    }

    public String getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(String buildingLocation) {
        this.buildingLocation = buildingLocation;
    }
}

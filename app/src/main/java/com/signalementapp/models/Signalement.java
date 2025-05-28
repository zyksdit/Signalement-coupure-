package com.signalementapp.models;

import com.google.gson.annotations.SerializedName;

public class Signalement {

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("imageUrl")
    private String imageUrl;

    public Signalement(int id, String type, String description, double latitude, double longitude, String imageUrl) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

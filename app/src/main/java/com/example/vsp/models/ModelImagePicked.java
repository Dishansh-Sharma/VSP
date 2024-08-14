package com.example.vsp.models;

import android.net.Uri;

public class ModelImagePicked {

    String id = "";
    Uri imageuri = null;
    String imageurl = null;
    Boolean fromInternet = false;

    public ModelImagePicked(){

    }

    public ModelImagePicked(String id, Uri imageuri, String imageurl, Boolean fromInternet) {
        this.id = id;
        this.imageuri = imageuri;
        this.imageurl = imageurl;
        this.fromInternet = fromInternet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Boolean getFromInternet() {
        return fromInternet;
    }

    public void setFromInternet(Boolean fromInternet) {
        this.fromInternet = fromInternet;
    }
}

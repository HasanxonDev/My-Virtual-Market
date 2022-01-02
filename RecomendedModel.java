package com.hudazamov.virtualshop.model;

public class RecomendedModel {
    String name;
    String rating;
    String description;
    String img_url;
    int price;
    String type;


    public RecomendedModel() {

    }

    public RecomendedModel(String name, String rating, String desctiption, String img_url, int price) {
        this.name = name;
        this.rating = rating;
        this.description = desctiption;
        this.img_url = img_url;
        this.price = price;
    }

    public RecomendedModel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desctiption) {
        this.description = desctiption;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

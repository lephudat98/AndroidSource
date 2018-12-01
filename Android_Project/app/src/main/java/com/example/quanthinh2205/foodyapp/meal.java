package com.example.quanthinh2205.foodyapp;

public class meal {
    private int image;
    private String meal;

    public meal(int image, String meal) {
        this.image = image;
        this.meal = meal;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}

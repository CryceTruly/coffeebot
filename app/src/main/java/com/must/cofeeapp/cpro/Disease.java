package com.must.cofeeapp.cpro;



public class Disease {
    String name,photo,signs,measures,effects,storepath;

    public Disease(String name, String photo, String signs, String measures, String effects, String storepath) {
        this.name = name;
        this.photo = photo;
        this.signs = signs;
        this.measures = measures;
        this.effects = effects;
        this.storepath = storepath;
    }

    public Disease() {
    }

    public String getStorepath() {
        return storepath;
    }

    public void setStorepath(String storepath) {
        this.storepath = storepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSigns() {
        return signs;
    }

    public void setSigns(String signs) {
        this.signs = signs;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }
}

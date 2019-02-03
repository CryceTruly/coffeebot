package com.must.cofeeapp.cpro;



public class DiseaseSearch {
    String name, photo;

    public DiseaseSearch(String name, String photo) {
        this.name = name;
        this.photo = photo;
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

    public DiseaseSearch() {
    }

}
package com.nbroadcast.backend.api.blog.api.model;

public enum Category {
    SOFTWARE_ENGINEERING("Software Engineering"),
    HARDWARE("Hardware"),
    MACHINE_LEARNING("Machine Learning");

    private String category;

    Category(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}

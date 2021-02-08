package com.nbroadcast.backend.api.blog.model;

public enum Category {
    SOFTWARE_ENGINEERING("Software Engineering"),
    Hardware("Hardware"),
    MachineLearning("Machine Learning");

    private String category;

    Category(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}

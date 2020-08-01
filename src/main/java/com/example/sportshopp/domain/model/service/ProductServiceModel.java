package com.example.sportshopp.domain.model.service;

import java.math.BigDecimal;

public class ProductServiceModel extends BaseServiceModel{

    private String name;
    private String description;
    private BigDecimal price;
    private CategoryServiceModel category;
    private String type;
//    private String category;
//    private Gender type;

    public ProductServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

//    public Gender getGender() {
//        return type;
//    }
//
//    public void setGender(Gender type) {
//        this.type = type;
//    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

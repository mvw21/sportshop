package com.example.sportshopp.domain.model.view;


import java.math.BigDecimal;

public class ProductViewModel {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imgUrl;
    private String type;
//    private Gender type;

    public ProductViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

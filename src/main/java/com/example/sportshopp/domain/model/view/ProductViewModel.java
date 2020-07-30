package com.example.sportshopp.domain.model.view;


import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.entity.Gender;

import java.math.BigDecimal;

public class ProductViewModel {
    private String id;
    private String name;
    private BigDecimal price;
    private CategoryName categoryName;
    private String imgUrl;
    private Gender gender;

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

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

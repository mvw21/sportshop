package com.example.sportshopp.domain.model.binding;


import com.example.sportshopp.domain.model.service.CategoryServiceModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductAddBindingModel {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String type;

    public ProductAddBindingModel() {
    }

    @Length(min = 3,max = 20,message = "Name length must be between 3 and 20 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 3, message = "Description length must be minimum 3 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DecimalMin(value = "0",message = "Enter valid price!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Enter valid category name! (hint - Jacket, Shoes or Tshirt)")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull(message = "Enter valid type! (Male/Female)")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

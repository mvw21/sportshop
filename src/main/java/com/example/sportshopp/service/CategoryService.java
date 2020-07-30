package com.example.sportshopp.service;

import com.example.sportshopp.domain.entity.Category;
import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.model.service.CategoryServiceModel;

public interface CategoryService {
    void initCategories();
    CategoryServiceModel findByCategoryName(CategoryName categoryName);
    CategoryServiceModel findByName(CategoryName name);
    Category find(CategoryName categoryName);
}

package com.example.sportshopp.service;


import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ProductService {

    void addProduct(ProductServiceModel productServiceModel);

    List<ProductViewModel> findAllItems();

    ProductViewModel findById(String id);

    void delete(String id);

    void deleteAll();

    BigDecimal getAllPrice();

    Collection<ProductViewModel> getProducts(CategoryName categoryName);

    List<ProductServiceModel> findAllByCategory(String categoryName);

    List<ProductServiceModel> findByType(String type);

    UserServiceModel addProductToCart(UserServiceModel user, String id, HttpSession httpSession);


}

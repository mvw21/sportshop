package com.example.sportshopp;

import com.example.sportshopp.domain.entity.Category;
import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.entity.Product;
import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.repository.ProductRepository;
import com.example.sportshopp.service.ProductService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest extends TestBase{

    private Product entityModel = new Product();

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    public void findAllByCategory_shouldWork_withValidDataInput() {

        when(productRepository.findAllByCategory(any()))
                .thenReturn(List.of(entityModel));

        List<ProductServiceModel> result = productService.findAllByCategory("Jersey");

        assertEquals(1, result.size());
    }

    @Test
    public void findAllByType_shouldWork_withValidDataInput() {

        when(productRepository.findAllByType(any()))
                .thenReturn(List.of(entityModel));

        List<ProductServiceModel> result = productService.findByType("Male");

        assertEquals(1, result.size());
    }



}

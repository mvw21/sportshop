package com.example.sportshopp.repository;


import com.example.sportshopp.domain.entity.Category;
import com.example.sportshopp.domain.entity.Product;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findAllByCategory(Category category);
    Optional<Product> findByName(String name);
    List<Product> findAllByType(String type);

}

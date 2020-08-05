package com.example.sportshopp.service.implementations;


import com.example.sportshopp.domain.entity.Category;
import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.entity.Product;
import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;
import com.example.sportshopp.repository.CategoryRepository;
import com.example.sportshopp.repository.ProductRepository;
import com.example.sportshopp.repository.UsersRepository;
import com.example.sportshopp.service.CategoryService;
import com.example.sportshopp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper, CategoryRepository categoryRepository, UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setCategory(this.categoryService.find(productServiceModel.getCategory().getCategoryName()));
        this.productRepository.saveAndFlush(product);
    }

    @Override
    public List<ProductViewModel> findAllItems() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name()));

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewModel findById(String id) {
        return this.productRepository
                .findById(id)
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name()));

                    return productViewModel;
                })
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.productRepository.deleteAll();
    }

    @Override
    public BigDecimal getAllPrice() {

        return this.productRepository.findAll()
                .stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Collection<ProductViewModel> getProducts(CategoryName categoryName) {
        return this.productRepository.findAll()
                .stream()
                .filter(x -> x.getCategory().getCategoryName().equals(categoryName))
                .map(x -> this.modelMapper.map(x,ProductViewModel.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String categoryName) {
        Category category = this.modelMapper.map(this.categoryService.find(CategoryName.valueOf(categoryName)), Category.class);
        return this.productRepository.findAllByCategory(category).stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }



    @Override
    public List<ProductServiceModel> findByType(String type) {
        return this.productRepository.findAllByType(type)
                .stream()
//                .filter(x-> x.getGender().name().equalsIgnoreCase(gender.name()))
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public UserServiceModel addProductToCart(UserServiceModel user, String id, HttpSession httpSession) {
        Product product = this.productRepository.findById(id).orElse(null);

//        Product product = this.modelMapper.map(model, Product.class);
//        Optional<User> loggedUser = this.usersRepository.findByUsername(user.getUsername());
        User loggedUser = this.modelMapper.map(user,User.class);
        User realUser = this.usersRepository.findByUsername(loggedUser.getUsername()).orElse(null);
        assert realUser != null;
        realUser.getCart().add(product);



        httpSession.setAttribute("user",realUser);
        this.usersRepository.saveAndFlush(realUser);
       //saveAndFlush user ?
            return this.modelMapper.map(realUser,UserServiceModel.class);
    }


}

package com.example.sportshopp.web.controllers;

import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.model.binding.ProductAddBindingModel;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;
import com.example.sportshopp.service.CategoryService;
import com.example.sportshopp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController extends BaseController{

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductsController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session){
        if(!model.containsAttribute("productAddBindingModel")){
            model.addAttribute("productAddBindingModel",new ProductAddBindingModel());

        }

        if(session.getAttribute("user") == null){
            return "home/index";
        }
        return "product-add";

    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAddBindingModel")ProductAddBindingModel productAddBindingModel,
                             @NotNull BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             HttpSession httpSession){

        // Security
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "product-add";
        }

        ProductServiceModel product = this.modelMapper.map(productAddBindingModel, ProductServiceModel.class);
        product.setCategory(this.categoryService.findByName(CategoryName.valueOf(productAddBindingModel.getCategory())));
        this.productService.addProduct(product);

//        this.productService
//                .addProduct(this.modelMapper.map(productAddBindingModel, ProductServiceModel.class));

        return "redirect:/";

    }

    @GetMapping("/men")
    public String men(Model model, HttpSession session){

//        List<ProductServiceModel> pr = this.productService.findByGender("Male");

//        ProductServiceModel pr = this.productService.findByGender("Male").get(0);
//        ProductServiceModel pr1 = this.productService.findByGender("Male").get(20);


       try{
           List<ProductViewModel> products = this.productService.findByType("Male")
                   .stream()
                   .map(product -> {
                       ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                       productViewModel.setImgUrl(String.format("/img/%s.jpg", product.getCategory().getCategoryName().name().toLowerCase()));
                       return productViewModel;
                   }).collect(Collectors.toList());


           model.addAttribute("male", products);
           return "product-male";
       }catch(Exception ex) {
           ex.printStackTrace();
       }

        return "product-male";
    }

}


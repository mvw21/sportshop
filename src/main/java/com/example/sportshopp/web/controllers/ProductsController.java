package com.example.sportshopp.web.controllers;

import com.example.sportshopp.domain.entity.BaseEntity;
import com.example.sportshopp.domain.entity.CategoryName;
import com.example.sportshopp.domain.model.binding.ProductAddBindingModel;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;
import com.example.sportshopp.service.CategoryService;
import com.example.sportshopp.service.ProductService;
import com.example.sportshopp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    private final UserService userService;

    public ProductsController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.userService = userService;
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


        return "redirect:/";

    }

    @GetMapping("/men")
    public String men(Model model, HttpSession session){
        UserServiceModel user =this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);





           List<ProductViewModel> products = this.productService.findByType("Male")
                   .stream()
                   .map(product -> {
                       ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                       productViewModel.setImgUrl(String.format("/img/%s.jpg", product.getCategory().getCategoryName().name().toLowerCase()));
                       return productViewModel;
                   }).collect(Collectors.toList());

           List<String> ids = user.getCart().stream()
                   .map(BaseEntity::getId).collect(Collectors.toList());
           model.addAttribute("ids",ids);
           model.addAttribute("male", products);
              session.setAttribute("name", user.getUsername());
           return "product-male";


    }

    @PostMapping("/men")
    public String menPost(HttpSession httpSession,@ModelAttribute("i")ProductViewModel productViewModel){

        UserServiceModel user =this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);

        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);

            return "home/home";

    }

    @GetMapping("/women")
    public String female(Model model, HttpSession session){
        UserServiceModel user =this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);

        List<ProductViewModel> products = this.productService.findByType("Female")
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                }).collect(Collectors.toList());

        List<String> ids = user.getCart().stream()
                .map(BaseEntity::getId).collect(Collectors.toList());
        model.addAttribute("ids",ids);
        model.addAttribute("female", products);
        session.setAttribute("name", user.getUsername());
        return "product-female";


    }

    @PostMapping("/women")
    public String femalePost(HttpSession httpSession,@ModelAttribute("i")ProductViewModel productViewModel){

        UserServiceModel user =this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);
        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);


        return "home/home";

    }

    @GetMapping("/kids")
    public String kids(Model model, HttpSession session){
        UserServiceModel user =this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);

        List<ProductViewModel> products = this.productService.findByType("Kids")
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                }).collect(Collectors.toList());

        List<String> ids = user.getCart().stream()
                .map(BaseEntity::getId).collect(Collectors.toList());
        model.addAttribute("ids",ids);
        model.addAttribute("kids", products);
        session.setAttribute("name", user.getUsername());
        return "product-kids";


    }

    @PostMapping("/kids")
    public String kidsPost(HttpSession httpSession,@ModelAttribute("i")ProductViewModel productViewModel){

        UserServiceModel user =this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);
        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);

        return "home/home";

    }

    @GetMapping("/other")
    public String other(Model model, HttpSession session){
        UserServiceModel user =this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);

        List<ProductViewModel> products = this.productService.findByType("Other")
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                }).collect(Collectors.toList());

        List<String> ids = user.getCart().stream()
                .map(BaseEntity::getId).collect(Collectors.toList());
        model.addAttribute("ids",ids);
        model.addAttribute("other", products);
        session.setAttribute("name", user.getUsername());
        return "product-other";


    }

    @PostMapping("/other")
    public String otherPost(HttpSession httpSession,@ModelAttribute("i")ProductViewModel productViewModel){

        UserServiceModel user =this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);

        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);
        return "home/home";

    }

    @GetMapping("/details")
    public ModelAndView details(@RequestParam("id")String id, ModelAndView modelAndView){
        modelAndView.addObject("product",this.productService.findById(id));
        modelAndView.setViewName("product-details");
        return modelAndView;
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("id")String id, ModelAndView modelAndView,HttpSession httpSession){

        UserServiceModel user =(UserServiceModel) httpSession.getAttribute("user");
        this.productService.addProductToCart(user,id,httpSession);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")String id){
        this.productService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable("id")String id,HttpSession session,Model model){

        UserServiceModel user3 = this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);
        String username = user3.getUsername();
        UserServiceModel realUser1 = this.userService.findByUsername(username);

        UserServiceModel user = this.modelMapper.map(session.getAttribute("user"),UserServiceModel.class);

        UserServiceModel umodel = this.productService.addProductToCart(user,id,session);
        List<String> ids = user.getCart().stream()
                .map(p->{
                    return p.getId();
                }).collect(Collectors.toList());
        ids.add(id);
        model.addAttribute("model",umodel);

        model.addAttribute("ids",ids);
        model.addAttribute("session", session);
        model.addAttribute("user", umodel);

        return "redirect:/";
    }



}


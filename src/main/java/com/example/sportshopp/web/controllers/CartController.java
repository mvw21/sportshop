package com.example.sportshopp.web.controllers;

import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;
import com.example.sportshopp.service.ProductService;
import com.example.sportshopp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public CartController(UserService userService, ProductService productService, ModelMapper modelMapper) {
        this.userService = userService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cart")
    public String cart(HttpSession httpSession, Model model){
        UserServiceModel user = this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);

        User userWithProductList = this.modelMapper.map(user,User.class);

        UserServiceModel userT = this.userService.findByUsername(user.getUsername());

        int g = userT.getCart().size();
int n = user.getCart().size();
        List<ProductViewModel> cart = user.getCart()
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());
        model.addAttribute("cart", cart);

        BigDecimal totalPrice =  cart.stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalPrice", totalPrice);
       httpSession.setAttribute("name", user.getUsername());
       httpSession.setAttribute("role",user.getRole());

        return "cart";
    }

    @GetMapping("/buyAll")
    public String buyAll(HttpSession httpSession){
        UserServiceModel user = this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);
        UserServiceModel user1 = this.userService.clearList(user);
        httpSession.setAttribute("user",user1);
        httpSession.setAttribute("user1",user1);

        return "redirect:/";
    }

    @GetMapping("/buyProduct/{id}")
    public String buyProduct(@PathVariable("id")String id,HttpSession httpSession){
       UserServiceModel userServiceModel = this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);
        this.userService.deleteProduct(id,userServiceModel);
        return "redirect:/";
    }


}

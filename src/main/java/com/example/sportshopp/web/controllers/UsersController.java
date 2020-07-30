package com.example.sportshopp.web.controllers;

import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.binding.UserLoginBindingModel;
import com.example.sportshopp.domain.model.binding.UserRegisterBindingModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.UserProfileViewModel;
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
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {


    private final UserService userService;
    private final ModelMapper modelMapper;


    public UsersController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.modelMapper = mapper;
    }

    @GetMapping("/login")
//    @PreAuthorize("isAnonymous()")
//    @PageTitle("Login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }
        return "users/login";
    }
    //todo da go probvam s ModelAndView

    @PostMapping("/login")
//    @PreAuthorize("isAnonymous()")
//    @PageTitle("Login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginBindingModel")
                                       UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "users/login";
        }

        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());

        if (user == null || !this.userService.passwordsAreEqual(userLoginBindingModel.getPassword(), user.getUsername())) {

            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            return "users/login";
        }

        httpSession.setAttribute("user", user);

        return "home/home";
    }


//    @InitBinder
//    private void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//    }


//    last used
//    @PostMapping("/register")
//    @PreAuthorize("isAnonymous()")
//    @PageTitle("Register")
//    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model) {
//        if (!model.getPassword().equals(model.getConfirmPassword())) {
//            return super.view("users/register");
//        }
//        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));
//
//        return super.view("users/login",);
//    }


    @GetMapping("/register")
//    @PreAuthorize("isAnonymous()")
//    @PageTitle("Register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "users/register";
    }


    @PostMapping("/register")
//    @PreAuthorize("isAnonymous()")
//    @PageTitle("Register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel")
                                          UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword()
                .equals(userRegisterBindingModel.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(this.modelMapper
                .map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:/users/login";

    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView){
        modelAndView
                .addObject("model",UserProfileViewModel.class);
        return super.view("users/profile", modelAndView);
    }
}
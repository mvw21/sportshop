package com.example.sportshopp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
@Controller
public class HomeController extends BaseController{

//    @GetMapping("/")
//    public String index(HttpSession httpSession, Model model) {
//        if (httpSession.getAttribute("user") == null) {
//            return "home/index";
//        }
//        return "home/home";
//    }

    @GetMapping("/")
    public ModelAndView index(){
        return super.view("home/index");
    }

    @GetMapping("/home")
    public ModelAndView home(){
        return super.view("home/home");
    }

}

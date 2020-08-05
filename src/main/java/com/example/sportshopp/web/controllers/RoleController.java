package com.example.sportshopp.web.controllers;


import com.example.sportshopp.domain.model.binding.RoleAddBindingModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.service.RoleService;
import com.example.sportshopp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public RoleController(RoleService roleService, ModelMapper modelMapper, UserService userService) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView addRole(ModelAndView modelAndView, HttpSession httpSession){

        UserServiceModel user = (UserServiceModel) httpSession.getAttribute("user");
        UserServiceModel user1 =this.modelMapper.map(httpSession.getAttribute("user"),UserServiceModel.class);
        System.out.println();
        if (user.getRole().getAuthority().equals("ADMIN")) {
            String name = user.getUsername();
            List<String> usernamesCollection = this.userService.getAllUserNames(name);
            System.out.println();
            modelAndView.addObject("usernamesCollection", usernamesCollection);
            modelAndView.setViewName("roles-add");
        }else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addRoleConfirm(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel,
                                       ModelAndView modelAndView,HttpSession httpSession){

        UserServiceModel user = (UserServiceModel) httpSession.getAttribute("user");
        UserServiceModel userServiceModel = this.userService.findByUsername(user.getUsername());
        String userRole = userServiceModel.getRole().getAuthority();
        System.out.println();
        if (userRole.equals("ADMIN")) {
            this.userService.changeRole(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());
            System.out.println();
            modelAndView.setViewName("redirect:/");
        }else {
            modelAndView.setViewName("redirect:/roles/add");
        }
        return modelAndView;
    }



}

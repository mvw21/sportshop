package com.example.sportshopp.domain.model.service;


import com.example.sportshopp.domain.entity.Product;

import java.util.List;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel{

    private String username;
    private String password;
    private String email;
//    private Set<RoleServiceModel> authorities;
    private RoleServiceModel role;
    private List<Product> cart;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleServiceModel getRole() {
        return role;
    }

    public void setRole(RoleServiceModel role) {
        this.role = role;
    }

    //    public Set<RoleServiceModel> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Set<RoleServiceModel> authorities) {
//        this.authorities = authorities;
//    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }
}

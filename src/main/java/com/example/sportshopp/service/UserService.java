package com.example.sportshopp.service;


import com.example.sportshopp.domain.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    boolean passwordsAreEqual(String password,String username);

    //login ako trqbva
}

package com.example.sportshopp.service;


import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    boolean passwordsAreEqual(String password,String username);

    void changeRole(String username, String role);

    UserServiceModel clearList(UserServiceModel userServiceModel);

    void deleteProduct(String id,UserServiceModel userServiceModel);

    List<String> getAllUserNames(String name);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    //login ako trqbva
}

package com.example.sportshopp;

import com.example.sportshopp.domain.entity.Product;
import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.binding.UserRegisterBindingModel;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.error.UserAlreadyExistException;
import com.example.sportshopp.error.UserNotFoundException;
import com.example.sportshopp.repository.ProductRepository;
import com.example.sportshopp.repository.UsersRepository;
import com.example.sportshopp.service.ProductService;
import com.example.sportshopp.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsersServiceTest extends TestBase{

        private User entityModel = new User();

        @MockBean
        UsersRepository usersRepository;

        @MockBean
        ProductRepository productRepository;

        @Autowired
        ProductService productService;

        @Autowired
        UserService userService;

        @Test
        public void checkUsername() throws Exception {
            User user = new User();
            user.setUsername("Pesho");
            String username = "Pesho";
            Mockito.when(usersRepository.findByUsername(user.getUsername()))
                    .thenReturn(java.util.Optional.of(user));


            assertEquals(username, user.getUsername());

        }




}

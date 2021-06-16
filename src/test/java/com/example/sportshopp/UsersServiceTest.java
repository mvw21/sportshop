package com.example.sportshopp;

import com.example.sportshopp.domain.entity.*;
import com.example.sportshopp.domain.model.binding.UserRegisterBindingModel;
import com.example.sportshopp.domain.model.service.CategoryServiceModel;
import com.example.sportshopp.domain.model.service.ProductServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.error.UserAlreadyExistException;
import com.example.sportshopp.error.UserNotFoundException;
import com.example.sportshopp.repository.CategoryRepository;
import com.example.sportshopp.repository.ProductRepository;
import com.example.sportshopp.repository.UsersRepository;
import com.example.sportshopp.service.ProductService;
import com.example.sportshopp.service.UserService;


import com.example.sportshopp.service.implementations.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsersServiceTest extends TestBase{

        private User entityModel = new User();
        private User testUser;
        private Product testProduct;
        private ModelMapper mapper;
//        private  UserServiceModel mockedUserServiceModel;

    @Before
    public void init(){
        this.testUser = new User() {{
            setId("some_uuid");
            setUsername("Pesho");
            setPassword("123");
            userService.getAllUserNames("Pesho");

        }};
        List<String> usernames =
                this.usersRepository
                        .findAll()
                        .stream()
                        .filter(user -> !user.getUsername().equals("Pesho"))
                        .map(User::getUsername)
                        .collect(Collectors.toList());
        this.usersRepository = Mockito.mock(UsersRepository.class);
    }

        @MockBean
        UsersRepository usersRepository;

        @MockBean
        ProductRepository productRepository;

        @MockBean
        UserServiceModel userServiceModel;

        @MockBean
        CategoryRepository categoryRepository;

        @MockBean
        ProductService productService1;

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

        @Test
        public void check_if_changeRoll_works(){
//            UserServiceModel clearList(UserServiceModel userServiceModel)

           this.testUser = new User() {{
                setId("some_uuid");
                setUsername("Pesho");
                setPassword("123");
                userService.getAllUserNames("Pesho");

            }};

            this.usersRepository = Mockito.mock(UsersRepository.class);
            Role role = new Role();
            role.setAuthority("asd");
            role.setAuthority("sss");
            testUser.setRole(role);




            //Act

            //Assert

            Assert.assertEquals("sss",testUser.getRole().getAuthority());


    }

}

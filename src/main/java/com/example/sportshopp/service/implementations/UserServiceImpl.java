package com.example.sportshopp.service.implementations;


import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.LogServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.error.UserAlreadyExistException;
import com.example.sportshopp.repository.UsersRepository;
import com.example.sportshopp.service.LogService;
import com.example.sportshopp.service.RoleService;
import com.example.sportshopp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LogService logService;
    //todo  private final UserRegisterValidator userRegisterValidator;


    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, LogService logService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.logService = logService;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.usersRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());

            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }
        User user = this.usersRepository
                .findByUsername(userServiceModel.getUsername())
                .orElse(null);

        if (user != null) {
            throw new UserAlreadyExistException("User already exists");
        }
        user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user.getUsername());
        logServiceModel.setDescription("User registered");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.usersRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.usersRepository.findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }
    @Override
    public boolean passwordsAreEqual (String password,String username){
        User user = this.usersRepository.findByUsername(username).orElse(null);
        assert user != null;
        return bCryptPasswordEncoder.matches(password, user.getPassword());

    }
}

package com.example.sportshopp.service.implementations;


import com.example.sportshopp.domain.entity.Product;
import com.example.sportshopp.domain.entity.Role;
import com.example.sportshopp.domain.entity.User;
import com.example.sportshopp.domain.model.service.LogServiceModel;
import com.example.sportshopp.domain.model.service.RoleServiceModel;
import com.example.sportshopp.domain.model.service.UserServiceModel;
import com.example.sportshopp.domain.model.view.ProductViewModel;
import com.example.sportshopp.error.Constants;
import com.example.sportshopp.error.UserAlreadyExistException;
import com.example.sportshopp.repository.RoleRepository;
import com.example.sportshopp.repository.UsersRepository;
import com.example.sportshopp.service.LogService;
import com.example.sportshopp.service.ProductService;
import com.example.sportshopp.service.RoleService;
import com.example.sportshopp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LogService logService;
    private final ProductService productService;
    private  final RoleRepository roleRepository;
 


    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, LogService logService, ProductService productService, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.logService = logService;
        this.productService = productService;
        this.roleRepository = roleRepository;
    }



    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
//        this.roleService.seedRolesInDb();

        if (this.roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            Role user = new Role();
            user.setAuthority("USER");
            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(user);
        }


//            userServiceModel.setAuthorities(new LinkedHashSet<>());

//            RoleServiceModel roleService = this.roleService.findByAuthority("USER");
//            userServiceModel.setRole(roleService);

//            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        User user = this.usersRepository
                .findByUsername(userServiceModel.getUsername())
                .orElse(null);

        if (user != null) {
            throw new UserAlreadyExistException("User already exists");
        }
        user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        if(this.usersRepository.count() == 0){
            RoleServiceModel role = this.roleService.findByAuthority("ADMIN");
            role.setAuthority("ADMIN");
            user.setCart(new ArrayList<>());
            user.setRole(this.modelMapper.map(role, Role.class));


        }else{
            RoleServiceModel role = this.roleService.findByAuthority("USER");
            role.setAuthority("USER");
            user.setCart(new ArrayList<>());
            user.setRole(this.modelMapper.map(role, Role.class));
        }

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

    @Override
    public UserServiceModel clearList(UserServiceModel userServiceModel) {
       User user = this.modelMapper.map(userServiceModel,User.class);
       User realUser  = this.usersRepository.findByUsername(user.getUsername()).orElse(null);
        assert realUser != null;
        realUser.getCart().clear();
        this.usersRepository.saveAndFlush(realUser);
        return this.modelMapper.map(realUser,UserServiceModel.class);

    }

    @Override
    public void deleteProduct(String id, UserServiceModel userServiceModel) {
            User user = this.usersRepository.findByUsername(userServiceModel.getUsername()).orElse(null);
        assert user != null;
        ProductViewModel productvm = this.productService.findById(id);
        Product product1 = this.modelMapper.map(productvm,Product.class);
        user.getCart().remove(product1);

    }

    @Override
    public List<String> getAllUserNames(String name) {
        return this.usersRepository
                .findAll()
                .stream()
                .map(User::getUsername)
                .filter(username -> !username.equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.usersRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException(Constants.USER_ID_NOT_FOUND));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(Constants.PASSWORD_IS_INCORRECT);
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());
        user.setEmail(userServiceModel.getEmail());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user.getUsername());
        logServiceModel.setDescription("User profile edited");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        return this.modelMapper.map(this.usersRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public void changeRole(String username, String role) {

        User user = this.usersRepository.findFirstByUsername(username).orElse(null);
        Role newRole = this.modelMapper.map(this.roleService.findByAuthority(role), Role.class);

        assert user != null;
        if (!user.getRole().getAuthority().equals(role)) {
            user.setRole(newRole);
            this.usersRepository.saveAndFlush(user);
        }

    }
}

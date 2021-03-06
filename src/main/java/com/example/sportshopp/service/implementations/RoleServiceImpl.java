package com.example.sportshopp.service.implementations;


import com.example.sportshopp.domain.entity.Role;
import com.example.sportshopp.domain.model.service.RoleServiceModel;
import com.example.sportshopp.repository.RoleRepository;
import com.example.sportshopp.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            Role user = new Role();
            user.setAuthority("USER");
            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(user);
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAndAuthority(authority),RoleServiceModel.class);
    }
}

package com.jwt.app1.security.service;

import com.jwt.app1.security.entity.Rol;
import com.jwt.app1.security.enums.RolName;
import com.jwt.app1.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Optional<Rol> findByRolName(RolName rol){
        return rolRepository.findByRolName(rol);
    }

    public void save(Rol rol) {
        rolRepository.save(rol);
    }
}

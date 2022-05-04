package com.jwt.app1.service;

import com.jwt.app1.entity.Rol;
import com.jwt.app1.enums.RolName;
import com.jwt.app1.repository.RolRepository;
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

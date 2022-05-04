package com.jwt.app1.security.repository;

import com.jwt.app1.security.entity.Rol;
import com.jwt.app1.security.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolname);
}

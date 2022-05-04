package com.jwt.app1.repository;

import com.jwt.app1.entity.Rol;
import com.jwt.app1.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolname);
}

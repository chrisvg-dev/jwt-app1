package com.jwt.app1.security.controller;

import com.jwt.app1.security.dto.JwtTokenDto;
import com.jwt.app1.security.dto.LoginDto;
import com.jwt.app1.security.dto.Message;
import com.jwt.app1.security.dto.UserDto;
import com.jwt.app1.security.entity.Rol;
import com.jwt.app1.security.entity.User;
import com.jwt.app1.security.enums.RolName;
import com.jwt.app1.security.jwt.JwtProvider;
import com.jwt.app1.security.service.RolService;
import com.jwt.app1.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private RolService rolService;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody UserDto user) {
        User newUser = User.builder()
                .name( user.getName() )
                .username( user.getUsername() )
                .email( user.getEmail() )
                .pwd( this.passwordEncoder.encode( user.getPwd() ) )
                .build();

        Set<Rol> roles = new HashSet();
        roles.add( this.rolService.findByRolName( RolName.ROLE_USER ).get() );

        if (user.getRoles().contains("admin")) {
            roles.add( this.rolService.findByRolName(RolName.ROLE_ADMIN).get() );
        }
        newUser.setRoles(roles);
        this.userService.save(newUser);
        return new ResponseEntity( new Message("Usuario registrado"), HttpStatus.CREATED );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto user) {

        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPwd() )
        );
        // ASIGNA AL USUARIO AUTHENTICADO AL CONTEXTO DE LA APLICACION
        SecurityContextHolder.getContext().setAuthentication( auth );

        String token = this.jwtProvider.generate(user.getUsername());
        JwtTokenDto response = new JwtTokenDto(token);
        return new ResponseEntity( response, HttpStatus.OK );
    }
}

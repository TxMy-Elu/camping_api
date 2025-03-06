package sio.app.camping_api.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.secutity.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Map<String, String> authenticateUser(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            log.info("User authenticated : " + email);
            log.info(authentication.getAuthorities().toString());


            String jwt = jwtUtils.generateJwtToken(email);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return response;

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException("Nom d'utilisateur ou mot de passe incorrect");
        }
    }
}
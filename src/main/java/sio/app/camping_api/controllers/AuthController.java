package sio.app.camping_api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.entity.Compte;
import sio.app.camping_api.repository.CompteRepository;
import sio.app.camping_api.secutity.JwtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompteRepository compteRepository;

    @PostMapping("/login")
    public Map<String, String> authenticateUser(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

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

    @PostMapping("/connexion")
    public Map<String, Object> connexion(@RequestParam String email, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Compte> compteOptional = compteRepository.findByEmail(email);

        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();
            if (passwordEncoder.matches(password, compte.getPassword())) {
                response.put("status", "ok");
                response.put("id_compte", compte.getId_compte());
                response.put("nom", compte.getNom());
                response.put("prenom", compte.getPrenom());
                response.put("email", compte.getEmail());
                response.put("role", compte.getRole().toString());
            } else {
                response.put("status", "error");
                response.put("message", "Mot de passe incorrect");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Email non trouvé");
        }

        return response;
    }
}

package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sio.app.camping_api.entity.Compte;
import sio.app.camping_api.repository.CompteRepository;

@Service
public class CustomCompteDetailsService implements UserDetailsService {
    @Autowired
    private CompteRepository compteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Compte user = compteRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));
        org.springframework.security.core.userdetails.User usr = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities() // les rôles et permissions de l'utilisateur
        );
        System.out.println(usr.getAuthorities());
        return usr;
    }
}
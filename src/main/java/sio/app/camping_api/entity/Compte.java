package sio.app.camping_api.entity;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
public class Compte implements UserDetails {

    @Id
    @GeneratedValue
    private Long id_compte;

    private String nom;
    private String prenom;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int abscences;
    private boolean bloque;

    //getters and setters

    public Long getId_compte() {
        return id_compte;
    }

    public void setId_compte(Long id_compte) {
        this.id_compte = id_compte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getAbscences() {
        return abscences;
    }

    public void setAbscences(int abscences) {
        this.abscences = abscences;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }


    //override methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourne le rôle unique de l'utilisateur sous forme de collection avec un seul élément
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}

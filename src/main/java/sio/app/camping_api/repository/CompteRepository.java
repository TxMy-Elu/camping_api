package sio.app.camping_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sio.app.camping_api.entity.Compte;

import java.util.Optional;
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByEmail(String email);
}
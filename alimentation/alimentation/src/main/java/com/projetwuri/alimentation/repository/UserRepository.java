package com.projetwuri.alimentation.repository;

import com.projetwuri.alimentation.models.Utilisateur; // Gardez cette importation, c'est la bonne
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> { // <-- CHANGEZ User en Utilisateur ici
    Optional<Utilisateur> findByUsername(String username);
}

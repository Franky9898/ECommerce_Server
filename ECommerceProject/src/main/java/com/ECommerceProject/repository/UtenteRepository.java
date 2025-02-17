package com.ECommerceProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECommerceProject.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long>
{
	Optional<Utente> findByEmail(String email);
}

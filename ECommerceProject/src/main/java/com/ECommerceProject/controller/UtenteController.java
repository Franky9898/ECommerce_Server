package com.ECommerceProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.UtenteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/utenti")
public class UtenteController
{
	@Autowired
	private UtenteRepository utenteRepo;

	@GetMapping
	public List<Utente> ottieniTuttiUtenti()
	{
		return utenteRepo.findAll();
	}

	@PostMapping
	public ResponseEntity<Utente> creaUtente(@Valid @RequestBody Utente utente)
	{
		Utente nuovoUtente = utenteRepo.save(utente);
		return new ResponseEntity<Utente>(nuovoUtente, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancellaUtente(@PathVariable Long id)
	{
		if (utenteRepo.existsById(id))
		{
			utenteRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Utente> modificaNomeUtente(@PathVariable Long id, @NotBlank @RequestBody String nome)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setNome(nome);
			utenteRepo.save(utente);
			return new ResponseEntity<Utente>(utente, HttpStatus.OK);
		}
		return new ResponseEntity<Utente>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Utente> modificaCognomeUtente(@PathVariable Long id, @NotBlank @RequestBody String cognome)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setCognome(cognome);
			utenteRepo.save(utente);
			return new ResponseEntity<Utente>(utente, HttpStatus.OK);
		}
		return new ResponseEntity<Utente>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Utente> modificaPIvaUtente(@PathVariable Long id, @NotBlank @RequestBody Long pIva)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setpIva(pIva);
			utenteRepo.save(utente);
			return new ResponseEntity<Utente>(utente, HttpStatus.OK);
		}
		return new ResponseEntity<Utente>(HttpStatus.NOT_FOUND);
	}
}

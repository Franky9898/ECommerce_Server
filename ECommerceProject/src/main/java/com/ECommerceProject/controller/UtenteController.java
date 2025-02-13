package com.ECommerceProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.UtenteRepository;

@RestController
@RequestMapping("/ordine")
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
	public Utente creaOrdine(@RequestBody Utente utente)
	{
		return utenteRepo.save(utente);
	}
}

package com.ECommerceProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.Ordine;
import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.OrdineRepository;

@RestController
@RequestMapping("/ordini")
public class OrdineController
{
	@Autowired
	private OrdineRepository ordineRepo;

	@GetMapping
	public List<Ordine> ottieniTuttiOrdini()
	{
		return ordineRepo.findAll();
	}

	@PostMapping
	public ResponseEntity<Ordine> creaUtente(@Valid @RequestBody Ordine ordine)
	{
		Ordine nuovoOrdine = ordineRepo.save(ordine);
		return new ResponseEntity<Ordine>(nuovoOrdine, HttpStatus.CREATED);
	}
}

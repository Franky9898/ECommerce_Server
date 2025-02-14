package com.ECommerceProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.Ordine;
import com.ECommerceProject.repository.OrdineRepository;

@RestController
@RequestMapping("/ordine")
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
	public Ordine creaOrdine(@RequestBody Ordine ordine)
	{
		return ordineRepo.save(ordine);
	}
}

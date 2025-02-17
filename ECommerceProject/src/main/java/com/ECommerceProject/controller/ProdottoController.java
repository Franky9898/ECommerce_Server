package com.ECommerceProject.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.ECommerceProject.model.Prodotto;
import com.ECommerceProject.repository.ProdottoRepository;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController
{
	@Autowired
	private ProdottoRepository prodRepo;

	@GetMapping("/fetchAndSaveFakeStoreAPI")
	public ResponseEntity<String> fetchAndSaveProducts()
	{
		RestTemplate restTemplate = new RestTemplate();
		final String apiUrl = "https://fakestoreapi.com/products";
		ResponseEntity<Prodotto[]> response = restTemplate.getForEntity(apiUrl, Prodotto[].class);
		Prodotto[] products = response.getBody();

		if (products != null)
		{
			prodRepo.saveAll(Arrays.asList(products));
			return ResponseEntity.ok("Prodotti salvati con successo");
		} else
		{
			return ResponseEntity.ok("Errore: prodotti non salvati");
		}
	}

	@GetMapping
	public List<Prodotto> getAllProducts()
	{
		return prodRepo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Prodotto> getProductById(@PathVariable Long id)
	{
		Optional<Prodotto> product = prodRepo.findById(id);
		if (product.isPresent())
		{
			return new ResponseEntity<Prodotto>(product.get(), HttpStatus.OK);
		} else
		{
			return new ResponseEntity<Prodotto>(HttpStatus.NOT_FOUND);
		}
	}
}

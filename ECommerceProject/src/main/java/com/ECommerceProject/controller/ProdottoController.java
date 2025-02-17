package com.ECommerceProject.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	/**
	 * 
	 * @return Messaggio di successo o errore Metodo per salvare in locale i prodotti del fakeStoreApi
	 */
	@GetMapping("/fetchAndSaveFakeStoreAPI")
	public ResponseEntity<String> fetchAndSaveProducts()
	{
		RestTemplate restTemplate = new RestTemplate();
		final String apiUrl = "https://fakestoreapi.com/products";
		ResponseEntity<Prodotto[]> response = restTemplate.getForEntity(apiUrl, Prodotto[].class); // Esegue un get sull'URL e converte il JSON in formato prodotto
		Prodotto[] products = response.getBody(); // Prende solamente il body della response
		if (products != null) // Salva se non vuoto
		{
			prodRepo.saveAll(Arrays.asList(products));
			return ResponseEntity.ok("Prodotti salvati con successo");
		}
		return ResponseEntity.ok("Errore: prodotti non salvati"); // Errore altrimenti
	}

	/**
	 * 
	 * @return Lista di tutti i prodotti Metodo per ottenere la lista di tutti i prodotti (non utilizzabile normalmente)
	 */
	@GetMapping
	public ResponseEntity<List<Prodotto>> getAllProducts()
	{
		List<Prodotto> prodotti = prodRepo.findAll(); // Ottieni la lista di prodotti
		return ResponseEntity.ok(prodotti);
	}

	/**
	 * 
	 * @param id per identificare prodotto
	 * @return il prodotto con quel id se esiste, altrimenti errore not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable Long id)
	{
		Map<String, String> result = new HashMap<String, String>();
		Optional<Prodotto> productOpt = prodRepo.findById(id);
		if (productOpt.isPresent()) // Controllo se il prodotto è presente
		{
			return ResponseEntity.ok(productOpt.get());
		}
		result.put("errore", "Prodotto non trovato.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // Response senza body
	}
}

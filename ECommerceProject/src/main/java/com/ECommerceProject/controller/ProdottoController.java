package com.ECommerceProject.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.ECommerceProject.model.Prodotto;
import com.ECommerceProject.repository.ProdottoRepository;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController
{
	@Autowired
	private ProdottoRepository prodRepo;

	@GetMapping("/fetchAndSaveFakeStoreAPI")
	public String fetchAndSaveProducts()
	{
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "https://fakestoreapi.com/products";
		Prodotto[] prodotti = restTemplate.getForObject(apiUrl, Prodotto[].class);
		if (prodotti != null)
		{
			prodRepo.saveAll(Arrays.asList(prodotti));
			return "Prodotti salvati correttamente!";
		}
		return "Errore: No products found to save.";
	}
}

package com.ECommerceProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.repository.ProdottoRepository;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController
{
	@Autowired
	private ProdottoRepository prodRepo;
	
	
}

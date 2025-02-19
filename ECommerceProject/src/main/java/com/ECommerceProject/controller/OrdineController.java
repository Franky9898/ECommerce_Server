package com.ECommerceProject.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.AuthUser;
import com.ECommerceProject.model.Ordine;
import com.ECommerceProject.model.Prodotto;
import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.AuthUserRepository;
import com.ECommerceProject.repository.OrdineRepository;
import com.ECommerceProject.repository.UtenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ordini")
@CrossOrigin(origins = {})
public class OrdineController
{
	@Autowired
	private OrdineRepository ordineRepo;
	@Autowired
	private AuthUserRepository authRepo;
	@Autowired
	private UtenteRepository userRepository;

	/**
	 * 
	 * @return List di tutti gli ordini Metodo per ottenere tutti gli ordini (non utilizzabile da utenti)
	 */
	@GetMapping
	public ResponseEntity<List<Ordine>> ottieniTuttiOrdini()
	{
		List<Ordine> ordini = ordineRepo.findAll();
		return ResponseEntity.ok(ordini);
	}

	/**
	 * 
	 * 
	 * Metodo per creare un nuovo ordine
	 */
	@PostMapping
	public ResponseEntity<Map<String, String>> creaOrdine(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Map<String, Object> body)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) // Verifica che l'header Authorization sia presente e abbia standard corretto
		{
			result.put("errore", "Header Authorization mancante o formato errato.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		String token = authorizationHeader.substring(7); // Estrae il token rimuovendo la stringa "Bearer "
		System.out.println("Token estratto: " + token); // Debug
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token); // Cerca l'utente autenticato tramite il token
		if (!authUserOpt.isPresent())
		{
			result.put("errore", "Il token non è associato a nessun utente.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		String email = authUserOpt.get().getEmail(); // Recupera l'email associata al token
		System.out.println("Email associata al token: " + email); // Debug
		Optional<Utente> userOpt = userRepository.findByEmail(email); // Recupera l'eventuale utente dal database
		if (!userOpt.isPresent())
		{
			result.put("errore", "Non è presente alcun utente con l'email in uso.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Utente user = userOpt.get();
		Long timestamp = Long.valueOf(body.get("date").toString()); // Prende data dal JSON
		Date date = new Date(timestamp);
		Double totale = Double.parseDouble((String) body.get("totale"));
		List<LinkedHashMap<String, Object>> prodottiMap = (List<LinkedHashMap<String, Object>>) body.get("prodotti"); // Di base dal JSON arrivano linkedHashMap, si deve convertire in Array
		List<Prodotto> prodotti = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (LinkedHashMap<String, Object> map : prodottiMap)
		{
			Prodotto prodotto = mapper.convertValue(map, Prodotto.class);
			prodotti.add(prodotto);
		}
		Ordine ordine = new Ordine(date, totale, user, prodotti);
		ordineRepo.save(ordine);
		result.put("messaggio", "Ordine salvato con successo.");
		return ResponseEntity.ok(result);

	}

	/**
	 * 
	 * @param authorizationHeader valore dell'header HTTP
	 * @return response entity con body lista ordini e status ok se non ci sono errori, altrimenti uno status di non autorizzato
	 */
	@GetMapping("/dettagliOrdini")
	public ResponseEntity<Object> getOrdiniUtenteLoggato(@RequestHeader("Authorization") String authorizationHeader)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) // Verifica che l'header Authorization sia presente e abbia standard corretto
		{
			result.put("errore", "Header Authorization mancante o formato errato.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		String token = authorizationHeader.substring(7); // Estrae il token rimuovendo la stringa "Bearer "
		System.out.println("Token estratto: " + token); // Debug
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token); // Cerca l'utente autenticato tramite il token
		if (!authUserOpt.isPresent())
		{
			result.put("errore", "Il token non è associato a nessun utente.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		String email = authUserOpt.get().getEmail(); // Recupera l'email associata al token
		System.out.println("Email associata al token: " + email); // Debug
		Optional<Utente> userOpt = userRepository.findByEmail(email); // Recupera l'eventuale utente dal database
		if (!userOpt.isPresent())
		{
			result.put("errore", "Non è presente alcun utente con l'email in uso.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Utente user = userOpt.get();
		List<Ordine> ordini = ordineRepo.findOrdiniByUtenteId(user.getId()); // Recupera gli ordini dell'utente
		return ResponseEntity.ok(ordini);
	}
}

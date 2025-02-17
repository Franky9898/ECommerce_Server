package com.ECommerceProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/utenti")
public class UtenteController
{
	@Autowired
	private UtenteRepository utenteRepo;

	/**
	 * 
	 * @return la lista di tutti gli utenti Metodo non utilizzabile normalmente
	 */
	@GetMapping
	public ResponseEntity<List<Utente>> ottieniTuttiUtenti()
	{
		List<Utente> utenti = utenteRepo.findAll();
		return ResponseEntity.ok(utenti);
	}

	/**
	 * 
	 * @param utente
	 * @return le info dell'utente creato se andata a buon fine, errore altrimenti
	 */
	@PostMapping
	public ResponseEntity<Object> creaUtente(@Valid @RequestBody Utente utente)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (utenteRepo.findByEmail(utente.getEmail()) != null) // Controlla se l'email inserita nella richiesta di post è già nel DB
		{
			result.put("errore", "L'email è già presente nel database.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
		Utente nuovoUtente = utenteRepo.save(utente);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
	}

	/**
	 * 
	 * @param id dell'utente da cancellare
	 * @return messaggi di successo o errore se non esiste
	 * Metodo per cancellare l'utente dal database
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> cancellaUtente(@PathVariable Long id)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (utenteRepo.existsById(id))
		{
			result.put("messaggio", "L'utente è stato cancellato.");
			utenteRepo.deleteById(id);
			return ResponseEntity.ok(result);
		}
		result.put("errore", "L'utente non esiste.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	}
	
	/**
	 * 
	 * @param id dell'utente
	 * @param nome nuovo dell'utente
	 * @return dettagli utente e status richiesta
	 * Metodo per modificare il nome dell'utente
	 */
	@PutMapping("/{id}/modNome")
	public ResponseEntity<Object> modificaNomeUtente(@PathVariable Long id, @NotBlank @RequestBody String nome)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setNome(nome);
			utenteRepo.save(utente);
			return ResponseEntity.status(HttpStatus.OK).body(utente);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
	}

	/**
	 * 
	 * @param id dell'utente
	 * @param cognome nuovo dell'utente
	 * @return dettagli utente e status richiesta
	 * Metodo per modificare il cognome dell'utente
	 */
	@PutMapping("/{id}/modCognome")
	public ResponseEntity<Object> modificaCognomeUtente(@PathVariable Long id, @NotBlank @RequestBody String cognome)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setCognome(cognome);
			utenteRepo.save(utente);
			return ResponseEntity.status(HttpStatus.OK).body(utente);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
	}
	
	/**
	 * 
	 * @param id dell'utente
	 * @param pIva nuova dell'utente
	 * @return dettagli utente e status richiesta
	 * Metodo per modificare la partita IVA dell'utente
	 */
	@PutMapping("/{id}/modPIva")
	public ResponseEntity<Object> modificaPIvaUtente(@PathVariable Long id, @NotBlank @RequestBody Long pIva)
	{
		Optional<Utente> utenteDaModificare = utenteRepo.findById(id);
		if (utenteDaModificare.isPresent())
		{
			Utente utente = utenteDaModificare.get();
			utente.setpIva(pIva);
			utenteRepo.save(utente);
			return ResponseEntity.status(HttpStatus.OK).body(utente);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
	}
}

package com.ECommerceProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.AuthUser;
import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.AuthUserRepository;
import com.ECommerceProject.repository.UtenteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/utenti")
@CrossOrigin(origins = {})
public class UtenteController
{
	@Autowired
	private UtenteRepository utenteRepo;

	@Autowired
	AuthUserRepository authRepo;

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
		if (utenteRepo.findByEmail(utente.getEmail()).isPresent()) // Controlla se l'email inserita nella richiesta di post è già nel DB
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
	 * @return messaggi di successo o errore se non esiste Metodo per cancellare l'utente dal database
	 */
	@DeleteMapping("/cancellaUtente")
	public ResponseEntity<Map<String, String>> cancellaUtente(@RequestHeader("Authorization") String authHeader)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (authHeader == null || authHeader.isEmpty())
		{
			result.put("errore", "Nessun token fornito");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		String token;
		if (authHeader != null && authHeader.startsWith("Bearer ")) // Se il token è inviato come "Bearer <token>", estrae la parte dopo "Bearer " e quindi il token
		{
			token = authHeader.substring(7);
		} else
		{
			token = authHeader; // Prende il token anche quando non c'è Bearer
		}
		if (token == null || token.isEmpty())
		{
			result.put("errore", "Token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token); // Trova lo user tramite token
		if (!authUserOpt.isPresent()) // Se per qualche motivo il token non corrisponde manda un errore
		{
			result.put("errore", "Errore token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		AuthUser authUser = authUserOpt.get();
		String email = authUser.getEmail();
		Optional<Utente> utenteOpt = utenteRepo.findByEmail(email);
		if (!utenteOpt.isPresent())
		{
			result.put("errore", "Non esiste utente con tale email.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		Utente utente = utenteOpt.get();
		result.put("messaggio", "Cancellazione avvenuta con successo");
		utenteRepo.delete(utente);
		return ResponseEntity.ok(result);
		
	}

	@PutMapping("/modificaProfilo")
	public ResponseEntity<Map<String, String>> modificaProfilo(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, Object> body)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (authHeader == null || authHeader.isEmpty())
		{
			result.put("errore", "Nessun token fornito");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		String token;
		if (authHeader != null && authHeader.startsWith("Bearer ")) // Se il token è inviato come "Bearer <token>", estrae la parte dopo "Bearer " e quindi il token
		{
			token = authHeader.substring(7);
		} else
		{
			token = authHeader; // Prende il token anche quando non c'è Bearer
		}
		if (token == null || token.isEmpty())
		{
			result.put("errore", "Token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token); // Trova lo user tramite token
		if (!authUserOpt.isPresent()) // Se per qualche motivo il token non corrisponde manda un errore
		{
			result.put("errore", "Errore token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		AuthUser authUser = authUserOpt.get();
		String email = authUser.getEmail();
		Optional<Utente> utenteOpt = utenteRepo.findByEmail(email);
		if (!utenteOpt.isPresent())
		{
			result.put("errore", "Non esiste utente con tale email.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		Utente utente = utenteOpt.get();
		String nome = (String) body.get("nome");
		String cognome = (String) body.get("cognome");
		Long pIva = null;
		if (body.containsKey("pIva") && body.get("pIva") != null)
		{
			try
			{
				pIva = Long.valueOf(body.get("pIva").toString());
			} catch (NumberFormatException e)
			{
				result.put("errore", "Formato partita IVA non valido");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		}
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setpIva(pIva);
		utenteRepo.save(utente);
		result.put("messaggio", "Modifiche avvenute con successo");
		return ResponseEntity.ok(result);
	}

	@GetMapping("/dettagli")
	public ResponseEntity<Object> login(@RequestHeader("Authorization") String authHeader)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (authHeader == null || authHeader.isEmpty())
		{
			result.put("errore", "Nessun token fornito");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		String token;
		if (authHeader != null && authHeader.startsWith("Bearer ")) // Se il token è inviato come "Bearer <token>", estrae la parte dopo "Bearer " e quindi il token
		{
			token = authHeader.substring(7);
		} else
		{
			token = authHeader; // Prende il token anche quando non c'è Bearer
		}
		if (token == null || token.isEmpty())
		{
			result.put("errore", "Token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token); // Trova lo user tramite token
		if (!authUserOpt.isPresent()) // Se per qualche motivo il token non corrisponde manda un errore
		{
			result.put("errore", "Errore token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		AuthUser authUser = authUserOpt.get();
		String email = authUser.getEmail();
		Optional<Utente> utenteOpt = utenteRepo.findByEmail(email);
		if (!utenteOpt.isPresent())
		{
			result.put("errore", "Non esiste utente con tale email.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		Utente utente = utenteOpt.get();
		return ResponseEntity.ok(utente);
	}
}

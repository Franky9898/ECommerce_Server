package com.ECommerceProject.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerceProject.model.AuthUser;
import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.AuthUserRepository;
import com.ECommerceProject.repository.UtenteRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {}) // Disabilita richieste CORS da origini esterne
public class AuthUserController
{

	
	@Autowired
	private AuthUserRepository authRepo;

	@Autowired
	private UtenteRepository userRepository;

	/**
	 * Endpoint per effettuare il login. Riceve email e password in formato JSON e restituisce il token se le credenziali sono valide.
	 *
	 * @param body     mappa contenente "email" e "password"
	 * @param response oggetto HttpServletResponse per impostare lo status
	 * @return mappa con un messaggio di conferma, il ruolo dell'utente e il token
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body, HttpServletResponse response)
	{
		Map<String, Object> result = new HashMap<>();
		// Estrae email e password dalla richiesta JSON
		String email = body.get("email");
		String password = body.get("password");

		// Verifica che email e password siano stati forniti
		if (email == null || password == null)
		{
			result.put("error", "Credenziali non valide");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		// Cerca l'utente nel database tramite email
		Optional<Utente> optionalUser = userRepository.findByEmail(email);
		// Se l'utente non esiste o la password non corrisponde, ritorna errore 401
		if (!optionalUser.isPresent() || !optionalUser.get().getPassword().equals(password))
		{
			result.put("error", "Credenziali non valide");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		// Se le credenziali sono corrette, recupera l'utente e il suo ruolo
		Utente user = optionalUser.get();

		// Genera un token associato all'utente
		String token = AuthUser.generateToken(email);
		Optional<AuthUser> authUser = authRepo.findByEmail(user.getEmail());

		// Salva il nuovo authUser:
		if (authUser.isPresent())
		{
			authUser.get().setToken(token);
			authRepo.save(authUser.get());
		} else
		{
			AuthUser nuovoUser = new AuthUser(user.getEmail(), token);
			authRepo.save(nuovoUser);
		}

		result.put("message", "Login effettuato con successo");
		result.put("token", token);
		return ResponseEntity.ok(result);
	}

	/**
	 * Endpoint per effettuare il logout. Riceve il token nell'header "Authorization" e lo rimuove dal TokenService, invalidando così il token lato client.
	 *
	 * @param authHeader header contenente il token (formato "Bearer <token>")
	 * @return mappa con un messaggio di conferma del logout
	 */
	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authHeader)
	{
		Map<String, Object> result = new HashMap<>();
		if (authHeader == null || authHeader.isEmpty())
		{
			result.put("Errore", "Nessun token fornito");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		String token;
		// Se il token è inviato come "Bearer <token>", estrae la parte dopo "Bearer " e quindi il token
		if (authHeader != null && authHeader.startsWith("Bearer "))
		{
			token = authHeader.substring(7);
		} else
		{
			token = authHeader;
		}
		if (token == null || token.isEmpty())
		{
			result.put("Errore", "Token non valido");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		Optional<AuthUser> authUserOpt = authRepo.findByToken(token);
		if (!authUserOpt.isPresent())
		{
			result.put("Errore", "Errore nel logout, token non valido");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		AuthUser authUser = authUserOpt.get();
	    authUser.setToken(null);
	    authRepo.save(authUser);

	    result.put("Message", "Logout effettuato con successo");
	    return ResponseEntity.ok(result);
	}
}
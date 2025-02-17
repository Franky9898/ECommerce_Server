package com.ECommerceProject.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuthUser
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// Username dell'utente autenticato
	private String username;

	private String token;

	public AuthUser(String username, String token)
	{
		this.username = username;
		this.token = generateToken(username);
	}

	public static String generateToken(String email)
	{
		// Genera un token univoco usando UUID
		String token = UUID.randomUUID().toString();
		System.out.println("Token generato: " + token); // stringa di debug in console
		// Associa il token all'utente autenticato nella mappa
		return token;
	}

	/**
	 * Costruttore che inizializza l'oggetto AuthUser con username e ruolo.
	 *
	 * @param username Username dell'utente
	 * @param role     Ruolo dell'utente
	 */
	public AuthUser(String username)
	{
		this.username = username;
	}

	public AuthUser()
	{
	}

	// Getter e Setter per username e ruolo

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}

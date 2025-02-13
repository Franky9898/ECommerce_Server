package com.ECommerceProject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Utente
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cognome;
	private Long pIva;
	private String email;
	private String password;
	@OneToMany(mappedBy = "utente")
	@JsonManagedReference("utente-ordini")
	private List<Ordine> ordini;
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getCognome()
	{
		return cognome;
	}
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}
	public Long getpIva()
	{
		return pIva;
	}
	public void setpIva(Long pIva)
	{
		this.pIva = pIva;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public List<Ordine> getOrdini()
	{
		return ordini;
	}
	public void setOrdini(List<Ordine> ordini)
	{
		this.ordini = ordini;
	}
	
	
}

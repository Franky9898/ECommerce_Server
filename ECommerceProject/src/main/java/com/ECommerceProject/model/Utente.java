package com.ECommerceProject.model;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Utente
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome obbligatorio")
	private String nome;
	
	@NotBlank(message = "Cognome obbligatorio")
	private String cognome;
	
	@Column(unique = true)
	private Long pIva;
	
	@Email(message = "Email deve essere valida")
	@Column(unique = true)
	private String email;
	
	@Length(min = 8, message = "La password deve essere almeno di 8 caratteri")
	@NotBlank(message = "Password obbligatoria")
	private String password;
	
	@OneToMany(mappedBy = "utente")
	@JsonManagedReference("utente-ordini")
	private List<Ordine> ordini;
	
	public Utente() 
	{
	}
	
	public Utente(String nome, String cognome, Long pIva,String email, String password)
	{
		this.nome = nome;
		this.cognome = cognome;
		this.pIva = pIva;
		this.email = email;
		this.password = password;
	}
	
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

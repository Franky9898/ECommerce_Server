package com.ECommerceProject.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Ordine
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Past(message = "La data non può essere nel futuro")
	@NotNull
	private Date date;
	
	@Min(value = 0, message = "Il prezzo deve essere non nullo")
	@NotNull
	private Double totale;

	@ManyToOne
	@JoinColumn(name = "utenteId", nullable = false)
	@JsonBackReference("utente-ordini")
	private Utente utente;

	@ManyToMany
	@JoinTable(name = "ordine_prodotto", joinColumns = @JoinColumn(name = "ordine_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "prodotto_id", nullable = false))
	@JsonIdentityReference(alwaysAsId = false)
	private List<Prodotto> prodotti;
	
	public Ordine()
	{
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Utente getUtente()
	{
		return utente;
	}

	public void setUtente(Utente utente)
	{
		this.utente = utente;
	}

	public List<Prodotto> getProdotti()
	{
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti)
	{
		this.prodotti = prodotti;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Double getTotale()
	{
		return totale;
	}

	public void setTotale(Double totale)
	{
		this.totale = totale;
	}
}

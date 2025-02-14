package com.ECommerceProject.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Ordine
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	//private Double totale;
	
	@ManyToOne
	@JoinColumn(name = "utenteId")
	@JsonBackReference("utente-ordini")
	private Utente utente;
	
	@ManyToMany(mappedBy = "ordine")
	@JoinTable(
	        name = "ordine_prodotto",
	        joinColumns = @JoinColumn(name = "ordine_id"),
	        inverseJoinColumns = @JoinColumn(name = "prodotto_id")
	    )
	@JsonManagedReference("ordine-prodotti")
	private List<Prodotto> prodotti;
	
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
}

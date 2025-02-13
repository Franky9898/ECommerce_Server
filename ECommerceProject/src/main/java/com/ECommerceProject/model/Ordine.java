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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ordine
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	@ManyToOne
	@JoinColumn(name = "utenteId")
	@JsonBackReference("utente-ordini")
	private Utente utente;
	@OneToMany(mappedBy = "ordine")
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

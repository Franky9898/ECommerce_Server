package com.ECommerceProject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prodotto
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;
	@ManyToOne
	@JoinColumn(name = "ordineId")
	@JsonBackReference("ordine-prodotti")
	private List<Ordine> ordine;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public Rating getRating()
	{
		return rating;
	}
	public void setRating(Rating rating)
	{
		this.rating = rating;
	}
	public List<Ordine> getOrdine()
	{
		return ordine;
	}
	public void setOrdine(List<Ordine> ordine)
	{
		this.ordine = ordine;
	}
	
	
}

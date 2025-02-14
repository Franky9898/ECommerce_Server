package com.ECommerceProject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Prodotto
{
	@Id
	private Long id;
	private String title;
	private Double price;
	@Column(length = 1000)
	private String description;
	private String category;
	private String image;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id", referencedColumnName = "id")
	private Rating rating;

	@ManyToMany(mappedBy = "prodotti")
	@JsonIgnore
	private List<Ordine> ordini;

	public Prodotto()
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
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

	public List<Ordine> getOrdini()
	{
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini)
	{
		this.ordini = ordini;
	}

}

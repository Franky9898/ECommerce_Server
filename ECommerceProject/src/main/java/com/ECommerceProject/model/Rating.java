package com.ECommerceProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Rating
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double rate; 
	private int count;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Prodotto prodotto;
	
	public double getRate()
	{
		return rate;
	}
	public void setRate(double rate)
	{
		this.rate = rate;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	
	
}

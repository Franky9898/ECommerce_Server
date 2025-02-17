package com.ECommerceProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECommerceProject.model.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long>
{
	
}

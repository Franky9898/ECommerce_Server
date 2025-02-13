package com.ECommerceProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECommerceProject.model.Ordine;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long>
{

}

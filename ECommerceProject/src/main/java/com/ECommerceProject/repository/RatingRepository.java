package com.ECommerceProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECommerceProject.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>
{

}

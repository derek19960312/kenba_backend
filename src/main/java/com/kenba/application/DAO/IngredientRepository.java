package com.kenba.application.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kenba.application.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	
	List<Ingredient> findAll();
	
	List<Ingredient> findByName(String name);
}

package com.kenba.application.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kenba.application.DAO.IngredientRepository;
import com.kenba.application.model.ingredient.Ingredient;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {
	private List<Ingredient> ingredientDB = new ArrayList<>();
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	@PostMapping
	public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient request) {
		boolean isIdDuplicated  = ingredientDB.stream().anyMatch(p -> p.getId().equals(request.getId()));
		
		if(isIdDuplicated) {
			return ResponseEntity.unprocessableEntity().build();
		}
		Ingredient ingredient = new Ingredient();
		ingredient.setId(request.getId());
		ingredient.setName(request.getName());
		ingredient.setUnit(request.getUnit());
		ingredientDB.add(ingredient);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ingredient.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(ingredient);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") int id, @RequestBody Ingredient request) {
	    Optional<Ingredient> ingredientOp = ingredientDB.stream()
	            .filter(p -> p.getId().equals(id))
	            .findFirst();
		if(ingredientOp.isPresent()) {
			Ingredient ingredient = ingredientOp.get();
			ingredientDB.remove(ingredient);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Ingredient> replaceIngredient(
	        @PathVariable("id") int id, @RequestBody Ingredient request) {
	    Optional<Ingredient> ingredientOp = ingredientDB.stream()
	            .filter(p -> p.getId().equals(id))
	            .findFirst();

	    if (!ingredientOp.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Ingredient oldIngredient = ingredientOp.get();
	    int productIndex = ingredientDB.indexOf(oldIngredient);

	    Ingredient ingredient = new Ingredient();
	    ingredient.setId(oldIngredient.getId());
	    ingredient.setName(request.getName());
	    ingredientDB.set(productIndex, ingredient);

	    return ResponseEntity.ok().body(ingredient);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Ingredient> getIngredient (@PathVariable int id) {
		Optional<Ingredient> ingredientOp = ingredientDB.stream().filter(i -> i.getId().equals(id)).findFirst();
		
		if(ingredientOp.isPresent()) {
			Ingredient ingredient =ingredientOp.get();
			return ResponseEntity.ok().body(ingredient);
		}
		
		return ResponseEntity.notFound().build();

	}
	
	@GetMapping()
	public ResponseEntity<List<Ingredient>> getIngredients (@ModelAttribute QueryParameter param) {
		
		List<Ingredient> ingredients = ingredientRepository.findAll();
		return ResponseEntity.ok().body(ingredients);

	}
}

package com.kenba.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipe_ingredient")
public class Recipe_Ingredient {
	@Id
	private Integer ingredientId;
	@Id
	private Integer recipeId;
	@Column(name = "RIcount")
	private String riCount;
	@Column(name = "RIprice")
	private String riPrice;
	public Integer getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Integer ingredientId) {
		this.ingredientId = ingredientId;
	}
	public Integer getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}
	public String getRiCount() {
		return riCount;
	}
	public void setRiCount(String riCount) {
		this.riCount = riCount;
	}
	public String getRiPrice() {
		return riPrice;
	}
	public void setRiPrice(String riPrice) {
		this.riPrice = riPrice;
	}
	

}

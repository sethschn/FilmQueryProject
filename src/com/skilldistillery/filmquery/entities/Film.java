package com.skilldistillery.filmquery.entities;

public class Film {
	private int id;
	private String rating;
	private double replacementCost;
	private Integer releaseYear;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	
}

package com.ft.learning;

import java.util.List;

public class Modules {

	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Resources> getResources() {
		return resources;
	}
	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}
	public long getRatings() {
		return ratings;
	}
	public void setRatings(long ratings) {
		this.ratings = ratings;
	}
	private String description;
	private List<Resources> resources;
	private long ratings;
}

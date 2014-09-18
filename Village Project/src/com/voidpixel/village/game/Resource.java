package com.voidpixel.village.game;

public class Resource {
	public String name;
	private int amount;
	
	public Resource(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public int getAmount() { return this.amount; }
	
	public boolean remove(int amount) {
		if(this.amount - amount < 0) return false;
		this.amount -= amount;
		return true;
	}
	
	public void empty() { 
		this.remove(this.amount);
	}
	
	public void add(int amount) { this.amount += amount; }
	public void add(Resource resource) { 
		if(resource.name == this.name) {
			this.add(resource.getAmount());
			resource.empty();
		}	
	}
}

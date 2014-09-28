package com.voidpixel.village.game;

public class Resource {
	public String name;
	private int amount;

	public Resource(String name) {
		this.name = name;
		this.amount = 0;
	}
	
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
	public void add(Resource resource, int amount) {
		if(resource.name == this.name) {
			int available = resource.getAmount();
			int newAmount = available - amount; if(newAmount < 0) newAmount = 0;
			int gathered = available - newAmount;
			
			this.add(gathered);
			resource.remove(gathered);
		}
	}

}

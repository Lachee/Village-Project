package com.voidpixel.village.person;

import com.voidpixel.village.game.Resource;

public class Person {
	public int x, y;
	public char symbol;
	public String name;
	
	public Resource storedFood, storedWood, storedMetal, storedStone;
	
	public Person(int x, int y, char symbol, String name) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.name = name;
		
		storedFood = new Resource("Food");
		storedWood = new Resource("Wood");
		storedMetal = new Resource("Metal");
		storedStone = new Resource("Stone");
	}
}

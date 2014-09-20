package com.voidpixel.village.game;

import java.awt.Graphics;

import com.voidpixel.village.main.Canvas;
import com.voidpixel.village.world.World;


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
	
	public void render(Graphics g) {
		g.drawString(symbol + "", x * World.scale, y * World.scale);
	}
}

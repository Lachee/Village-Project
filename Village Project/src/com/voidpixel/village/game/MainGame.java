package com.voidpixel.village.game;

import java.awt.Graphics;
import java.util.ArrayList;

import com.voidpixel.village.main.*;
import com.voidpixel.village.person.*;

public class MainGame{
	
	public Program program;
	public Canvas canvas;
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public Resource collectiveFood, collectiveWood, collectiveMetal, collectiveStone, collectiveScience;
	
	public MainGame(Program program, Canvas canvas) {
		this.program = program;
		this.canvas = canvas;

		collectiveFood = new Resource("Food", 200);
		collectiveWood = new Resource("Wood", 200);
		collectiveMetal = new Resource("Metal", 200);
		collectiveStone = new Resource("Stone", 200);
		collectiveScience = new Resource("Science", 0);
		
	}
	
	public void update(double delta) {
	}
	
	public void render(Graphics g) {
	}
	
}

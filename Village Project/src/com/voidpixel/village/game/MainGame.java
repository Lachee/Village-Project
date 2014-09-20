package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.voidpixel.village.main.*;
import com.voidpixel.village.world.World;

public class MainGame{
	
	public Program program;
	public Canvas canvas;
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public World world;
	
	public Resource collectiveFood, collectiveWood, collectiveMetal, collectiveStone, collectiveScience;
	
	public MainGame(Program program, Canvas canvas) {
		this.program = program;
		this.canvas = canvas;

		collectiveFood = new Resource("Food", 200);
		collectiveWood = new Resource("Wood", 200);
		collectiveMetal = new Resource("Metal", 200);
		collectiveStone = new Resource("Stone", 200);
		collectiveScience = new Resource("Science", 0);
		
		world = new World(Program.WIDTH / 10, Program.HEIGHT / 10);
		
		people.add(new Person(10, 10, 'P', "Bob F Jones"));
		
	}
	
	public void update(double delta) {
	}
	
	public void render(Graphics g) {
		world.render(g);
		
		g.setColor(new Color(0,0,0,25));
		for(int x = 0; x < world.width; x++) {
			g.drawLine(x * World.scale, 0, x * World.scale, world.height * World.scale);
		}
		
		for(int y = 0; y < world.height; y++) {
			g.drawLine(0, y * World.scale, world.width * World.scale, y * World.scale);
		}
		
		g.setColor(Color.black);
		for(Person person : people) {
			person.render(g);
		}	
	}
	
}

package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.voidpixel.village.main.*;
import com.voidpixel.village.world.World;

public class MainGame{
	
	//Make this a singleton
	public static MainGame instance;
	
	public Program program;
	public Canvas canvas;
	
	//How long (in seconds) a tick should be.
	public double tickRate = 0;
	protected long tickStart = 0;
	
	protected int frameCount = 0;
	public boolean secondFlash = false;
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public World world;
	
	public Resource collectiveFood, collectiveWood, collectiveMetal, collectiveStone, collectiveScience;
	
	public MainGame(Program program, Canvas canvas) {
		MainGame.instance = this;
		
		this.program = program;
		this.canvas = canvas;

		collectiveFood = new Resource("Food", 200);
		collectiveWood = new Resource("Wood", 200);
		collectiveMetal = new Resource("Metal", 200);
		collectiveStone = new Resource("Stone", 200);
		collectiveScience = new Resource("Science", 0);
		
		world = new World(Program.WIDTH / 10, Program.HEIGHT / 10);

		for(int i = 0; i < 5; i++)
			people.add(new Person(this, 1 + i, 1));
		
		//TODO: Implement the main feature of this program...
		//tick(n);
	}
	
	public void update(double delta) {
		frameCount++;
		if(frameCount >= program.framerate) {
			secondFlash = !secondFlash;
			frameCount = 0;
		}
		
		for(Person person : people) 
			person.update(delta);
		
		if(System.nanoTime() - tickStart >= tickRate * 1e9) {
			tickStart = System.nanoTime();
			tick();
		}

	}
	
	public void tick() { 
		for(Person person : people) 
			person.tick();
	}
	
	public void tick(int n) {
		for(int i = 0; i < n; i++)
			tick();
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

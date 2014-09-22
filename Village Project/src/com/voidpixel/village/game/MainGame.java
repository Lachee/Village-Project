package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.voidpixel.village.main.*;
import com.voidpixel.village.task.*;
import com.voidpixel.village.village.Village;
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
	public boolean drawGrid = false;
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public World world;

	public Village village;
	
	public ArrayList<PersonTaskQueue> taskQueue = new ArrayList<PersonTaskQueue>();
	
	public MainGame(Program program, Canvas canvas) {
		MainGame.instance = this;
		
		this.program = program;
		this.canvas = canvas;
		
		world = new World(Program.WIDTH / 10, Program.HEIGHT / 10);

		village = new Village(this, 10, 10);
		
		for(int i = 0; i < 5; i++) {
			people.add(new Person(this, 1 + i, 1));
			if(i == 0) {
				people.get(0).clearTasks();
				people.get(0).setTask(new TaskBuildVillage(), true);
			}
		}
	
		//TODO: Implement the main feature of this program...
		//tick(n);
	}
	
	public void addTask(PersonTask task, int maxWorkers) {
		PersonTaskQueue queue = new PersonTaskQueue(task, maxWorkers);
		if(taskQueue.contains(queue)) return;
		taskQueue.add(queue);
	}
	
	public PersonTask getPriorityTask() {
		for(int i = 0; i < taskQueue.size(); i++) {
			PersonTask task = taskQueue.get(i).addWorker();
			if(task != null) return task;
		}
		
		return null;
	}
	
	public void update(double delta) {
		frameCount++;
		if(frameCount >= program.framerate) {
			secondFlash = !secondFlash;
			frameCount = 0;
		}
		
		for(Person person : people) 
			person.update(delta);
		
		village.update(delta);
		
		if(System.nanoTime() - tickStart >= tickRate * 1e9) {
			tickStart = System.nanoTime();
			tick();
		}

	}
	
	public void tick() { 
		for(Person person : people) 
			person.tick();
		
		village.tick();
	}
	
	public void tick(int n) {
		for(int i = 0; i < n; i++)
			tick();
	}
	
	public void render(Graphics g) {
		world.render(g);
		
		village.render(g);
		
		if(drawGrid) {
			g.setColor(new Color(0,0,0,25));
			for(int x = 0; x < world.width; x++) {
				g.drawLine(x * World.scale, 0, x * World.scale, world.height * World.scale);
			}
			
			for(int y = 0; y < world.height; y++) {
				g.drawLine(0, y * World.scale, world.width * World.scale, y * World.scale);
			}
		}
		
		g.setColor(Color.black);
		for(Person person : people) {
			person.render(g);
		}	
		
	}
	
}

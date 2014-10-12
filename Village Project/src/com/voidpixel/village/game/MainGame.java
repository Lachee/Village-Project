package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.voidpixel.village.building.Village;
import com.voidpixel.village.main.*;
import com.voidpixel.village.task.*;
import com.voidpixel.village.task.global.*;
import com.voidpixel.village.world.World;

public class MainGame {
	
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
	
	//Camera Controll
	public double minZoom = 2.5;
	public double maxZoom = 0.25;
	
	//The next person to cycle camera on
	public int cameraCycleIndex = 0;
	
	public Point mouseLastPosition = new Point();
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public World world;

	public Village village;
	
	public HashMap<String, PersonTask> tasks = new HashMap<String, PersonTask>();
	
	public MainGame(Program program, Canvas canvas) {
		MainGame.instance = this;
		
		this.program = program;
		this.canvas = canvas;
		
		world = new World(250, 250); //Program.WIDTH / 10, Program.HEIGHT / 10);

		village = new Village(this, 10, 10);
				
		//Add all posible (and inital) tasks
		addTask(new TaskBuildVillage());
		addTask(new TaskBuildTent());
		addTask(new TaskGatherWood());
				
		for(int i = 0; i < 5; i++) {
			people.add(new Person(this, 1 + i, 1));
			requestTask(new TaskBuildTent().getName());
		}
		
		//TODO: Implement the main feature of this program...
		//tick(n);
	}
	
	protected void addTask(PersonTask task) {
		String name = task.getName();
		tasks.put(name, task);
	}
	
	public void requestTask(String task) {
		if(!tasks.containsKey(task)) return;
		tasks.get(task).requests++;
	}
	
	public PersonTask getPriorityTask() {
		PersonTask ht = null;
		double hp = 0;
		
		for(Entry<String, PersonTask> entry : tasks.entrySet()) {
		    String key = entry.getKey();
		    PersonTask task = entry.getValue();

		    double tp = (task.priority * task.requests) / (task.workers + 1);
		    if(tp > hp) {
		    	hp = tp;
		    	ht = task;
		    }
		}
		
		return ht;
	}
	
	public void cameraCyclePeople() {

		Person p = people.get(cameraCycleIndex);
		cameraCycleIndex++;
		if(cameraCycleIndex >= people.size()) cameraCycleIndex = 0;
		
		canvas.camera.centerPosition(p.x * World.scale, p.y * World.scale);
	}
	
	public void update(double delta) {
		frameCount++;
		if(frameCount >= program.framerate) {
			secondFlash = !secondFlash;
			frameCount = 0;
			
		}
		
		//Camera Zoom
		canvas.camera.zoom(Input.getMouseScroll() / 10.0);
		if(canvas.camera.getScale() < maxZoom) canvas.camera.setScale(maxZoom);
		if(canvas.camera.getScale() > minZoom) canvas.camera.setScale(minZoom);
		
		//Camera movement
		if(Input.getMouse(Input.MOUSE_MIDDLE)) {
			Point p = Input.getMousePosition();
			int x = p.x - mouseLastPosition.x;
			int y = p.y - mouseLastPosition.y;
			canvas.camera.translate(x, y);
		}
		
		//Cycle between people
		if(Input.getKeyDown(KeyEvent.VK_C)) {
			cameraCyclePeople();
		}
		
		//Debug Mode
		program.stats.setEnabled(Input.getKey(KeyEvent.VK_F3));
		
		for(Person person : people) 
			person.update(delta);
		
		village.update(delta);
		
		if(System.nanoTime() - tickStart >= tickRate * 1e9) {
			tickStart = System.nanoTime();
			tick();
		}
		
		
		mouseLastPosition = Input.getMousePosition();	
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
	
	public void renderCamera(Camera c) {

		c.setColor(Color.black);
		world.renderCamera(c);				

		c.setColor(Color.black);
		village.renderCamera(c);
		

		c.setColor(Color.black);
		if(drawGrid) {
			c.setColor(new Color(0,0,0,25));
			for(int x = 0; x < world.width; x++) {
				c.drawLine(x * World.scale, 0, x * World.scale, world.height * World.scale);
			}
			
			for(int y = 0; y < world.height; y++) {
				c.drawLine(0, y * World.scale, world.width * World.scale, y * World.scale);
			}
		}
		
		c.setColor(Color.black);
		for(Person person : people) {
			person.renderCamera(c);
		}	
		

		// This is a little test code to see where the cursor is at in world space
		/*
		c.setColor(Color.magenta);
		Point mp = c.screenToWorld(Input.getMousePosition());	
		c.drawRect((int)Math.floor(mp.x / World.scale) * World.scale , (int)Math.floor(mp.y / World.scale) * World.scale, World.scale, World.scale);
		*/
		
	}
		
	public void renderGUI(Graphics g) {
		village.renderGUI(g);
		

		
		//This is a little example code to see where the cursor is at in world space
		/*
		g.setColor(Color.magenta);
		Point mp = Input.getMousePosition();			
		g.drawLine(mp.x - 5, mp.y, mp.x + 5, mp.y);
		g.drawLine(mp.x, mp.y - 5, mp.x, mp.y + 5);
		*/
	}

	public void renderDebug(Graphics g) { 
		//g.drawString("People: " + people.size(), 10, 250);
		
		//The Camera
		g.setColor(Color.white);
		g.drawString("=== Camera ===", 10, 250);		
		g.setColor(Color.lightGray);		
		g.drawString("scale: " + canvas.camera.getScale(), 10, 270);
		g.drawString("zoom: " + (1/canvas.camera.getScale()), 10, 290);
		g.drawString("x: " + canvas.camera.getRealX(), 10, 310);
		g.drawString("y: " + canvas.camera.getRealY(), 10, 330);
		
		
		//Players
		g.setColor(Color.white);
		g.drawString("=== People ===", 10, 360);		
		g.setColor(Color.lightGray);		
		g.drawString("People Count: " + people.size(), 10, 380);
		//g.drawString("Queued Tasks: " + taskQueue.size(), 10, 400);
		
		//Players
		g.setColor(Color.white);
		g.drawString("=== General ===", 10, 430);		
		g.setColor(Color.lightGray);		
		g.drawString("Resource Folder: " + GameResources.getResourceFolder(), 10, 450);
		g.drawString("Loaded Resources: " + GameResources.size(), 10, 470);
		
		
		//Village
		g.setColor(Color.white);
		g.drawString("=== Village ===", 250, 250);		
		g.setColor(Color.lightGray);		
		g.drawString("Wood: " + village.collectiveWood.getAmount(), 250, 270);
		g.drawString("Stone: " + village.collectiveStone.getAmount(), 250, 290);
		g.drawString("Metal: " + village.collectiveMetal.getAmount(), 250, 310);
		g.drawString("Food: " + village.collectiveFood.getAmount(), 250, 330);
		
		//General
		g.setColor(Color.white);
		g.drawString("=== World ===", 250, 360);		
		g.setColor(Color.lightGray);		
		g.drawString("World Seed: " + world.seed, 250, 380);
		g.drawString("World Size: " + world.width + " x " + world.height, 250, 400);
		
		
		
		//Next Row
		
		g.setColor(Color.white);
		g.drawString("=== Tasks Avalible ===", 600, 20);		
		g.setColor(Color.lightGray);		
		
		int i = 0;
		for(Entry<String, PersonTask> entry : tasks.entrySet()) {
		    String key = entry.getKey();
		    PersonTask task = entry.getValue();

		    double tp = (task.priority * task.requests) / (task.workers + 1);
			g.drawString(task.getName() + 
					" [W: " + task.workers + "]" +
					" [R: " + task.requests + "]" +
					" [P: " + task.priority + "]" +
					" [TP: " + (tp*100) + "%]", 
					600, (20 * i) + 40);
			i++;
		}
	
		
	}

}

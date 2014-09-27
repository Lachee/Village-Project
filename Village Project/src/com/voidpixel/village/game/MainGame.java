package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import com.voidpixel.village.interfaces.listeners.InputKeyListener;
import com.voidpixel.village.interfaces.listeners.InputMouseListener;
import com.voidpixel.village.main.*;
import com.voidpixel.village.task.*;
import com.voidpixel.village.village.Village;
import com.voidpixel.village.world.World;

public class MainGame implements InputKeyListener, InputMouseListener{
	
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
	
	//Mouse Keys
	public boolean mouseRightButton = false;
	public boolean mouseLeftButton = false;
	public boolean mouseMiddleButton = false;
	
	//Camera Controll
	public double minZoom = 2.5;
	public double maxZoom = 0.25;
	
	public Point mousePosition = new Point();
	public Point mouseLastPosition = new Point();
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public World world;

	public Village village;
	
	public ArrayList<PersonTaskQueue> taskQueue = new ArrayList<PersonTaskQueue>();
	
	public MainGame(Program program, Canvas canvas) {
		MainGame.instance = this;
		
		Listener.registerKeyListener(this);
		Listener.registerMouseListener(this);
		
		this.program = program;
		this.canvas = canvas;
		
		world = new World(250, 250); //Program.WIDTH / 10, Program.HEIGHT / 10);

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
		
		if(mouseMiddleButton) {
			int x = mousePosition.x - mouseLastPosition.x;
			int y = mousePosition.y - mouseLastPosition.y;
			canvas.camera.translate(x, y);
		}
		
		mouseLastPosition = mousePosition;

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
		world.renderCamera(c);		
		village.renderCamera(c);
		
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
		
	}
	
	
	public void renderGUI(Graphics g) {
		g.drawString("Test", 10, 10);
	}


	@Override
	public void OnKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) 
			canvas.camera.translate(0, 10);
		if(e.getKeyCode() == KeyEvent.VK_DOWN) 
			canvas.camera.translate(0, -10);
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
				canvas.camera.translate(10, 0);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
			canvas.camera.translate(-10, 0);
		
		if(e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET)
			canvas.camera.zoom(0.1);
		else if(e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET)
			canvas.camera.zoom(-0.1);
	}

	@Override
	public void OnKeyReleased(KeyEvent e) { }

	@Override
	public void OnMousePressed(MouseEvent e) { 
		if(e.getButton() == 1)
			mouseLeftButton = true;
		
		if(e.getButton() == 2)
			mouseMiddleButton = true;
		
		if(e.getButton() == 3)
			mouseRightButton = true;		
	}

	@Override
	public void OnMouseReleased(MouseEvent e) { 
		if(e.getButton() == 1)
			mouseLeftButton = false;
		
		if(e.getButton() == 2)
			mouseMiddleButton = false;
		
		if(e.getButton() == 3)
			mouseRightButton = false;
	}

	@Override
	public void OnMouseMoved(Point mousePoint) {
		mousePosition = mousePoint;
	}

	@Override
	public void OnMouseScroll(MouseWheelEvent e) {
		canvas.camera.zoom(-(double)e.getPreciseWheelRotation() / 10.0);
		
		//Confusing? Yes! But there is a reason for it! zoom = 1 / scale!
		if(canvas.camera.getScale() < maxZoom) canvas.camera.setScale(maxZoom);
		if(canvas.camera.getScale() > minZoom) canvas.camera.setScale(minZoom);
	}
}

package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.voidpixel.village.interfaces.*;
import com.voidpixel.village.task.PersonTask;
import com.voidpixel.village.task.TaskGatherWood;
import com.voidpixel.village.task.TaskStoreResources;
import com.voidpixel.village.world.World;


public class Person  implements GameElement{
	public MainGame game;
	
	//Actuall Position
	public int x, y;
	
	//Target Position
	public int tx, ty;
	
	public char symbol;
	public String name;
	
	public Resource storedFood, storedWood, storedMetal, storedStone;
	public int maxResource = 25;
	
	public ArrayList<PersonTask> tasks = new ArrayList<PersonTask>();
	
	public Person(MainGame game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.tx = x;
		this.ty = y;
		
		this.name = PersonName.getName();
		this.symbol = this.name.charAt(0);
		
		storedFood = new Resource("Food");
		storedWood = new Resource("Wood");
		storedMetal = new Resource("Metal");
		storedStone = new Resource("Stone");
		
		System.out.println("Created " + this.name + " (" + this.symbol + ")");
		
		
		//TODO: Remove this
		this.setTask(new TaskGatherWood(), false);
	}
	
	public Person(MainGame game, int x, int y, char symbol, String name) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.name = name;
		
		storedFood = new Resource("Food");
		storedWood = new Resource("Wood");
		storedMetal = new Resource("Metal");
		storedStone = new Resource("Stone");
		
		System.out.println("Created " + this.name + " (" + this.symbol + ")");
	}
	
	
	
	public void render(Graphics g) {
		
		if(tasks.size() > 0) {
			//double alpha = 1.0 / ((double)game.people.size() / 5);
			
			g.setColor(new Color(255, 200, 50, 255));
			g.fillRect(tx * World.scale, ty * World.scale, World.scale, World.scale);
			g.setColor(Color.black);	
		}
	
		g.drawString(symbol + "", x * World.scale, (y+1) * World.scale);
		
	}

	public void setTask(PersonTask task, boolean topOfStack) {
		if(topOfStack)
			tasks.add(0, task);
		else
			tasks.add(task);
	}
	
	public void endTask() {
		if(tasks.size() == 0) return;
		tasks.get(0).endTask(this);
		tasks.remove(0);
	}
	
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		if(tx >= 0 || ty >= 0) {
			double dx = (tx - x);
			double dy = (ty - y);
			
			if(dx > 1) dx = 1;
			if(dy > 1) dy = 1;
			if(dx < -1) dx = -1;
			if(dy < -1) dy = -1;
			
			x += dx;
			y += dy;			
		}
		
		//Keep the person's position withing the map at all times
		if(x < 0) x = 0; if(x >= game.world.width) x = game.world.width - 1;
		if(y < 0) y = 0; if(y >= game.world.height) y = game.world.height - 1;
	}

	public void setTargetPosition(int x, int y) {
		tx = x;
		ty = y;
	}
	
	@Override
	public void tick() {
		if(storedWood.getAmount() > maxResource || storedFood.getAmount() > maxResource || storedMetal.getAmount() > maxResource || storedStone.getAmount() > maxResource) {
			//We are carring to much, we must store our resources!
			TaskStoreResources task = new TaskStoreResources();
			if(tasks.get(0).getName() != task.getName()) {
				setTask(task, true);
				System.out.println(this.name + " is full, unloading resources to closest stockpile");
			}
		}
		
		if(tasks.size() > 0)  {
			tasks.get(0).tick(game, this);
		}else{
			Random rand = new Random();
			if(rand.nextDouble() <= 0.05) {
				tx = x + rand.nextInt(20) - 10;
				ty = y + rand.nextInt(20) - 10;
			}
		}
	}
}

class PersonName {
	//TODO: Get better names. These are just temporary!
	public static String[] maleNames = new String[] { "John", "Fred", "Bob", "Lachee", "Newman", "Ramidreju", "Kamek", "Luke" };
	public static String[] femaleNames = new String[] { "Lea", "Justine", "Chell", "Monica", "Marion" };
	public static String[] lastNames = new String[] { "Kenway", "Anderson", "Smith", "Jones", "Skywalker" };
	
	
	public static String getName() {
		return getName(Math.random() <= 0.5);
	}
	
	public static String getName(boolean male) {
		Random rand = new Random();
		String name = "";
		
		if(male)
			name = maleNames[rand.nextInt(maleNames.length)];
		else
			name = femaleNames[rand.nextInt(femaleNames.length)];
		
		name += " " + lastNames[rand.nextInt(lastNames.length)];
		
		return name;
	}
}

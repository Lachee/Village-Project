package com.voidpixel.village.task.global;

import java.awt.Color;
import java.awt.Point;

import com.voidpixel.village.building.Building;
import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;
import com.voidpixel.village.task.*;

public class TaskBuildTent extends PersonTask{
	public Building tent;
	public Point tentLoc;
	public int requiredResources = 50;
	public int currentResources = 0;
	public int buildRate = 1;
	
	public TaskBuildTent() {
		super("Task/Build/Tent", 1);
	}

	@Override
	public void onTaskStart(Person person) {
		if(tentLoc == null) locateNewTent(person.game, person);
	}
	
	@Override
	public void tick(MainGame game, Person person) {
		if(tentLoc == null) locateNewTent(game, person);
		
		if(person.storedWood.getAmount() < buildRate)
			person.changeTask(new TaskCollectResources("Wood", 50 - person.storedWood.getAmount()));
		
		person.setTargetPosition(tentLoc.x, tentLoc.y);
		
		if(person.getX() == tentLoc.x && person.getY() == tentLoc.y) 
		{
			int available = person.storedWood.getAmount();
			int newAmount = available - buildRate; if(newAmount < 0) newAmount = 0;
			int amount = available - newAmount;
			int ncr = currentResources + amount;
			
			//eg 50 - 49 = 1 || 50 - 55 = -5
			int reqDiff = requiredResources - ncr; if (reqDiff > 0) reqDiff = 0;
			amount = amount + reqDiff;
			
			currentResources += amount;
			person.storedWood.remove(amount);
			
			if(currentResources == requiredResources) completeTask(person);
		}
	}
	
	@Override
	public void completeTask(Person person) {
		tent.x = tentLoc.x;
		tent.y = tentLoc.y;
		tentLoc = null;
		
		super.completeTask(person);
	}
	
	protected void locateNewTent(MainGame game, Person person) {
		currentResources = 0;
		tent = new Building(1, 1, "Tent", Color.DARK_GRAY);
		tentLoc = game.village.assignBuildingLocation(tent);
	}
}

package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;

public class TaskCollectResources extends PersonTask{
	
	public String resource;
	public int amount;
	
	public TaskCollectResources(String resource, int amount) {
		super("Task/Collect/" + resource);
		this.resource = resource;
		this.amount = amount;
	}
	
	@Override
	public void endTask(Person person) {
		/*
		System.out.println(person.name + " has collected their resources. Total now at: ");
		System.out.println(" - " + person.game.village.collectiveFood.getAmount() + " food");
		System.out.println(" - " + person.game.village.collectiveMetal.getAmount() + " metal");
		System.out.println(" - " + person.game.village.collectiveStone.getAmount() + " stone");
		System.out.println(" - " + person.game.village.collectiveWood.getAmount() + " wood");
		*/
	}

	@Override
	public void tick(MainGame game, Person person) {
		boolean enoughResources = false;
		switch(resource) {
		
		default: enoughResources = true; break;
		case "Food": enoughResources = person.storedFood.getAmount() >= amount; break;
		case "Metal": enoughResources = person.storedMetal.getAmount() >= amount; break;
		case "Stone": enoughResources = person.storedStone.getAmount() >= amount; break;
		case "Wood": enoughResources = person.storedWood.getAmount() >= amount; break;
		
		}
		
		if(!enoughResources) {
		
			//Make the player go to the store point
			person.setTargetPosition(game.village.x, game.village.y);
			
			//if we are at the store point, collect our resources
			if(person.getX() == game.village.x && person.getY() == game.village.y ) {
			
				switch(resource) {
				case "Food" :
					if(game.village.collectiveFood.getAmount() < amount) { requestMoreResource(game, person); return; }
					person.storedFood.add(game.village.collectiveFood, amount);
					break;
					
				case "Metal" :
					if(game.village.collectiveMetal.getAmount() < amount) { requestMoreResource(game, person); return; }
					person.storedMetal.add(game.village.collectiveMetal, amount);
					break;
					
				case "Stone" :
					if(game.village.collectiveStone.getAmount() < amount) { requestMoreResource(game, person); return; }
					person.storedMetal.add(game.village.collectiveStone, amount);
					break;
					
				case "Wood" :
					if(game.village.collectiveWood.getAmount() < amount) { requestMoreResource(game, person); return; }
					person.storedWood.add(game.village.collectiveWood, amount);
					break;			
				}
				
			}
		}else{
			person.endTask();
		}
	}

	public void requestMoreResource(MainGame game, Person person){
		game.addTask(new TaskGatherWood(), 3);
	}
	
}

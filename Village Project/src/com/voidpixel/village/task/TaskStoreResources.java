package com.voidpixel.village.task;

import java.awt.Point;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;
import com.voidpixel.village.tiles.Tile;
import com.voidpixel.village.world.World;

public class TaskStoreResources implements PersonTask{

	public Point storePoint = null;
	
	@Override
	public void endTask(Person person) {
		System.out.println(person.name + " has stored their resources. Total now at: ");
		System.out.println(" - " + person.game.collectiveFood.getAmount() + " food");
		System.out.println(" - " + person.game.collectiveMetal.getAmount() + " metal");
		System.out.println(" - " + person.game.collectiveStone.getAmount() + " stone");
		System.out.println(" - " + person.game.collectiveWood.getAmount() + " wood");
	}

	@Override
	public void tick(MainGame game, Person person) {
		if(person.storedWood.getAmount() >person. maxResource || person.storedFood.getAmount() > person.maxResource || 
				person.storedMetal.getAmount() > person.maxResource || person.storedStone.getAmount() > person.maxResource) {
		
			//Find the nearest settlment if we have not already done so
			if(storePoint == null)
				locateSettlement(game.world, person.x, person.y);

			//Make the player go to the store point
			person.setTargetPosition(storePoint.x, storePoint.y);
			
			//if we are at the store point, unload our resources
			if(person.x == storePoint.x && person.y == storePoint.y ) {
				game.collectiveFood.add(person.storedFood);
				game.collectiveMetal.add(person.storedMetal);
				game.collectiveStone.add(person.storedStone);
				game.collectiveWood.add(person.storedWood);
			}
		}else{
			person.endTask();
		}
	}

	public void locateSettlement(World world, int cx, int cy) {
		//If no one has a tree already, find one
		for(int r = 0; r < world.width; r++) {
			for(int a = 0; a < 360; a++) {
				double angle = Math.toRadians(a);
				int x = (int)(cx + r * Math.cos(angle));
				int y = (int)(cy + r * Math.sin(angle));
				
				Tile tile = world.getTile(x, y);
				if(tile == null) continue;
				if(tile.getID() != World.settlement.getID()) continue;
				
				storePoint = new Point(x,y);
				return;
			}
		}
	}
	
	@Override
	public String getName() {
		return "Task/Store/Resource";
	}

}

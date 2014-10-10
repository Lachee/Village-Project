package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;
public class TaskStoreResources extends PersonTask{

	public TaskStoreResources() {
		super("Task/Store/Resources", 0);
	}

	@Override
	public void tick(MainGame game, Person person) {
		if(person.storedWood.getAmount() >person. maxResource || person.storedFood.getAmount() > person.maxResource || 
				person.storedMetal.getAmount() > person.maxResource || person.storedStone.getAmount() > person.maxResource) {
		

			//Make the player go to the store point
			person.setTargetPosition(game.village.x, game.village.y);
			
			//if we are at the store point, unload our resources
			if(person.getX() == game.village.x && person.getY() == game.village.y ) {
				game.village.collectiveFood.add(person.storedFood);
				game.village.collectiveMetal.add(person.storedMetal);
				game.village.collectiveStone.add(person.storedStone);
				game.village.collectiveWood.add(person.storedWood);
			}
		}else{
			person.endTask();
		}
	}


}

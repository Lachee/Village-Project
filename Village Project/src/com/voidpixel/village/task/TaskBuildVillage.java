package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;

public class TaskBuildVillage implements PersonTask{

	@Override
	public void endTask(Person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(MainGame game, Person person) {
		if(person.storedWood.getAmount() == 0){
			person.setTask(new TaskCollectResources("Wood", 50 - person.storedWood.getAmount()), true);
		}
		
		person.setTargetPosition(1, 1);
		
		if(person.getX() == 1 && person.getY() == 1)
			person.storedWood.remove(10);		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Task/Build/Store";
	}

}

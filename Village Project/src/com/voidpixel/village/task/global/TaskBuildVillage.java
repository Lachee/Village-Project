package com.voidpixel.village.task.global;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;
import com.voidpixel.village.task.PersonTask;
import com.voidpixel.village.task.TaskCollectResources;

public class TaskBuildVillage extends PersonTask{
	
	public TaskBuildVillage() {
		super("Task/Build/Test", 1);
	}
	
	@Override
	public void tick(MainGame game, Person person) {
		if(person.storedWood.getAmount() == 0){
			person.changeTask(new TaskCollectResources("Wood", 50 - person.storedWood.getAmount()));
		}
		
		person.setTargetPosition(1, 1);
		
		if(person.getX() == 1 && person.getY() == 1)
			person.storedWood.remove(10);		
	}
	
	@Override
	public void onTaskEnd(Person person) {
		super.onTaskEnd(person);
		this.requests++;
	}

}

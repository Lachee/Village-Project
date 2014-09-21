package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;

public interface PersonTask {
	public void endTask(Person person);
	public void tick(MainGame game, Person person);
	public String getName();
}

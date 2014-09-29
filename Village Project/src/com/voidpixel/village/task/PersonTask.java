package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;

public abstract class PersonTask {
	public final String name;
	public PersonTask(String name) { this.name = name;}
	public void endTask(Person person) {}
	public void tick(MainGame game, Person person) {}
	public String getName() { return name; }
}

package com.voidpixel.village.task;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;

public abstract class PersonTask {
	public final String name;
	public double priority;
	public int workers;
	public int requests;
	
	public PersonTask(String name, double priority) { this.name = name; this.priority = priority;}

	public void onTaskStart(Person person) { workers++; }
	public void onTaskEnd(Person person) { workers--; if(workers < 0) workers = 0; }
	
	public void completeTask(Person person) {
		person.endTask();
		requests--; if(requests < 0) requests = 0;
	}
	
	public void tick(MainGame game, Person person) {}
	public String getName() { return name; }
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof PersonTask)) return false;
		if(obj == this) return true;
		PersonTask tsk = (PersonTask)obj;
		return tsk.name == this.name;
	}

	public void onTaskChange(Person person, PersonTask task) { }
}

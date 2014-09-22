package com.voidpixel.village.task;


public class PersonTaskQueue {
	public PersonTask task;
	public int workers;
	public int maxWorkers;
	
	public PersonTaskQueue(PersonTask task, int maxWorkers) {
		this.task = task;
		this.maxWorkers = maxWorkers;
	}
	
	public PersonTask addWorker() {
		if(workers == maxWorkers) return null;
		workers++;
		return task;
	}
	
	public void removeWorker() {
		workers--;
	}

}

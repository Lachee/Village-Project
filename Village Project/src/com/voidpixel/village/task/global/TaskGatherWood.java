package com.voidpixel.village.task.global;

import java.awt.Point;
import java.util.Random;

import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.game.Person;
import com.voidpixel.village.game.Resource;
import com.voidpixel.village.task.PersonTask;
import com.voidpixel.village.task.TaskStoreResources;
import com.voidpixel.village.tiles.Tile;
import com.voidpixel.village.tiles.TileResource;
import com.voidpixel.village.world.World;

public class TaskGatherWood extends PersonTask{
	public static int searchRadius = 50;
	public Point targetTree;

	//public int tickCount = 0;
	public int treeAttempts = 0;
	
	/* Psudo of finding wood */
	/*
	 * if (fellow people have a tree selected) {
	 * 		selectedTree = fellow person's tree;
	 * }else{
	 * 		for(int r = 0; r < maxRadius; r++) {
	 * 			Check every point on a circle with 'r' radius;
	 * 			if(point on circle is tree)
	 * 				selectedTree = tree;
	 * 		}
	 * }
	 * 	
	 */
	
	public TaskGatherWood() {
		super("Task/Gather/Wood", 0.5);
	}
	
	public void onTaskChange(Person person, PersonTask task) {
		if(!new TaskStoreResources().equals(task)) return;
		completeTask(person);
	}
	
	@Override
	public void tick(MainGame game, Person person) {
		if(targetTree != null && game.world.getTile(targetTree.x, targetTree.y).getID() != World.tree.getID())
			targetTree = null;
		
		//Locate the tree
		if(targetTree == null) 
			locateTree(game, person);

		//Move to the tree
		if(targetTree != null) {
			person.setTargetPosition(targetTree.x, targetTree.y);
			treeAttempts = 0;
		
			//if we are at the tree and it is a tree, gather some of it's resources.
			if(person.getX() == targetTree.x && person.getY() == targetTree.y) {
				Resource resource = World.tree.gatherResources(game.world, targetTree.x, targetTree.y, 1);
				person.storedWood.add(resource);
			}
		}else{
			treeAttempts++;
		}
		
		
		//Otherwise, move onto the next task at hand
		if(treeAttempts >= 3)
			person.endTask();
		
		//tickCount++;
	}
	
	public void locateTree(MainGame game, Person person) {
		targetTree = null;

		Random rand = new Random();
		int cx = person.getX() + (rand.nextInt(4) - 2);
		int cy = person.getY() + (rand.nextInt(4) - 2);
		
		for(Person fellowPerson : game.people) {
			if(fellowPerson.task == null || !fellowPerson.task.equals(this)) continue;
			
			TaskGatherWood task = (TaskGatherWood)fellowPerson.task;			
			if(task.targetTree != null) {

				cx = task.targetTree.x;
				cy = task.targetTree.y;
				
				while(cx == task.targetTree.x && cy == task.targetTree.y) {
					cx += (rand.nextInt(4) - 2);
					cy += (rand.nextInt(4) - 2);
				}
				
				break;
			}
		}

		this.searchRadius = game.world.width > game.world.height ? game.world.width : game.world.height;
		
		//If no one has a tree already, find one
		for(int r = 0; r < searchRadius; r++) {
			for(int a = 0; a < 360; a++) {
				double angle = Math.toRadians(a);
				int x = (int)(cx + r * Math.cos(angle));
				int y = (int)(cy + r * Math.sin(angle));
				
				Tile tile = game.world.getTile(x, y);
				if(tile == null) continue;
				if(tile.getID() != World.tree.getID()) continue;
				if(((TileResource)tile).getResource() != "Wood") continue;
				
				targetTree = new Point(x,y);
				return;
			}
		}
	}

}

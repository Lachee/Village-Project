package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.voidpixel.village.interfaces.*;
import com.voidpixel.village.main.Camera;
import com.voidpixel.village.task.PersonTask;
import com.voidpixel.village.task.TaskStoreResources;
import com.voidpixel.village.task.global.TaskGatherWood;
import com.voidpixel.village.world.World;


public class Person  implements GameElement{
	public MainGame game;

	//Actuall Position
	public double x, y;
	public double speed = 10;
	
	//Target Position
	public int tx, ty;

	public char symbol;
	public String name;
	

	public Resource storedFood, storedWood, storedMetal, storedStone;
	public int maxResource = 50;

	public PersonTask task;
	
	public Person(MainGame game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.tx = x;
		this.ty = y;

		this.name = PersonName.getName();
		this.symbol = this.name.charAt(0);

			
		storedFood = new Resource("Food");
		storedWood = new Resource("Wood");
		storedMetal = new Resource("Metal");
		storedStone = new Resource("Stone");

		System.out.println("Created " + this.name + " (" + this.symbol + ")");
	}

	public int getX() { 
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public Person(MainGame game, int x, int y, char symbol, String name) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.name = name;

		storedFood = new Resource("Food");
		storedWood = new Resource("Wood");
		storedMetal = new Resource("Metal");
		storedStone = new Resource("Stone");

		System.out.println("Created " + this.name + " (" + this.symbol + ")");
	}



	public void renderCamera(Camera c) {

		if(task != null) {
			c.setColor(new Color(255, 200, 50, 255/2));
			c.drawRect(tx * World.scale, ty * World.scale, World.scale, World.scale);
		}

		
		c.setColor(Color.white);
		c.drawString(symbol + "", getX() * World.scale, (getY()+1) * World.scale);
		
	}
	
	public void getNewTask() {
		task = game.getPriorityTask();
		if(task == null) return;
		task.onTaskStart(this);
	}
	
	public void endTask() {
		task.onTaskEnd(this);
		task = null;
	}
	
	public void changeTask(PersonTask task) {
		endTask();
		this.task = task;
		this.task.onTaskStart(this);
	}
	
	@Override
	public void update(double delta) {
		//TODO: NOTHING should be called on the update. It all is to be tick dependent!
		if(tx >= 0 || ty >= 0) {
			double dx = (tx - getX());
			double dy = (ty - getY());

			if(dx > 1) dx = 1;
			if(dy > 1) dy = 1;
			
			if(dx < -1) dx = -1;
			if(dy < -1) dy = -1;

			double spd = speed * game.world.getTile(getX(), getY()).speedModifier;
			
			x += dx * spd * delta;
			y += dy * spd * delta;   
		}

		//Keep the person's position within the map at all times
		if(getX() < 0) x = 0; if(getX() >= game.world.width) x = game.world.width - 1;
		if(getY() < 0) y = 0; if(getY() >= game.world.height) y = game.world.height - 1;
	}

	public void setTargetPosition(int x, int y) {
		tx = x;
		ty = y;
	}

	@Override
	public void tick() {
		if(storedWood.getAmount() > maxResource || storedFood.getAmount() > maxResource || storedMetal.getAmount() > maxResource || storedStone.getAmount() > maxResource) {
			changeTask(new TaskStoreResources());
		}

		if(task == null) getNewTask();
		
		if(task != null)  {
			task.tick(game, this);
		}else{
			Random rand = new Random();
			if(rand.nextDouble() <= 0.05) {
				tx = getX() + rand.nextInt(20) - 10;
				ty = getY() + rand.nextInt(20) - 10;
			}
		}
	}


	@Override
	public void renderGUI(Graphics g) {
	}
}

class PersonName {
	public static String[] maleNames = new String[] {
		"Kamek", "Luke", "Arl", "Barak","Atellus" ,"Alaric" ,
		"Able", "Brull", "Cain", "Brutis", "Attilas", "Bones",
		"Dar", "Dariel", "Callidon", "Barbosa", "Crisis",
		"Frak", "Eli", "Castus", "Cortez", "Cutter",
		"Fral", "Enoch", "Drustos", "Constantine", "Dekko",
		"Garm", "Frastus", "Flavion", "Cromwell", "Dakka",
		"Grish", "Gaius", "Gallus", "Dorn", "Frag",
		"Grak","Garvel","Haxtes","Drake","Flair",
		"Hak","Hastus","Intios","Eisen","Finial",
		"Jarr","Ignace","Jastilus","Ferrus","Grim",
		"Kar","Ishmael","Kaltos","Grendal","Gob",
		"Kaarl","Jericus","Litilus","Guilliman","Gunner",
		"Krell","Lazerus","Mallear","Iacton","Jakes",
		"Mar","Mordeci","Metalus","Jaghatai","Krak",
		"Mir","Mirthas","Nihilius","Khan","Lug",
		"Narl","Nicodemus","Novus","Leman","Mongrel",
		"Orl","Pontus","Octus","Lionus","Plex",
		"Phrenz","Quint","Praetus","Magnus","Rat",
		"Quarl","Rabalias","Quintos","Mercutio","Red",
		"Roth","Reestheus","Raltus","Nixios","Sawney",
		"Ragaa","Silvanus","Ravion","Ramirez","Scab",
		"Stig","Solomon","Regis","Serghar","Scammer",
		"Strag","Thaddius","Severus","Sigismund","Skive",
		"Thak","Titus","Silon","Tybalt","Shanks",
		"Ulth","Uriah","Tauron","Vern","Shiv",
		"Varn","Varnias","Trantor","Wolfe","Sham",
		"Wrax","Xerxes","Venris","Wollsey","Stern",
		"Yarn","Zaddion","Victus","Zane","Stubber",
		"Zak","Zuriel","Xanthis","Zarkov","Verbal" };

	public static String[] femaleNames = new String[] { 
		"Arla","Akadia","Atella","Aenid","Alpha",
		"Brulla","Chaldia","Brutilla","Albia","Blaze",
		"Darl","Cyrine","Callidia","Borgia","Blue",
		"Fraka","Diona","Castella","Cimbria","Cat",
		"Fraal","Deatrix","Drustilla","Devi","Calamity",
		"Garma","Ethina","Flavia","Ephese","Dame",
		"Grisha","Ephrael","Gallia","Euphrati","Dice",
		"Graki","Fenria","Haxta","Inez","Flair",
		"Haka","Gaia","Intias","Imperatrice","Gold",
		"Jarra","Galatia","Jestilla","Jemadar","Gunner",
		"Karna","Hazael","Kalta", "Jezail","Hack",
		"Kaarli","Isha","Litila","Joss","Halo",
		"Krella","Ishta","Lupa","Kadis","Lady",
		"Lekka","Judicca","Meta","Lethe","Modesty",
		"Mira","Lyra","Nihila","Mae","Moll",
		"Narla","Magdela","Novia","Millicent","Pistol",
		"Orla","Narcia","Octia","Merica","Plex",
		"Phrix","Ophilia","Praetia","Midkiff","Pris",
		"Quali","Phebia","Quintilla","Megehra","Rat",
		"Rothra","Qualia","Raltia","Odessa","Red",
		"Ragaana","Rhia","Ravia","Orlean","Ruby",
		"Stigga","Salomis","Regia","Plath","Scarlet",
		"Stranga","Solaria","Scythia","Severine","Spike",
		"Thakka","Thyratia","Sila","Thiopia","Steel",
		"Ultha","Thebe","Taura","Thrace","Starr",
		"Varna","Uriel","Trantia","Tzarine","Trauma",
		"Wraxa","Veyda","Venria","Venus","Trick",
		"Yarni","Xantippe","Xanthia","Walperga","Trix",
		"Zekka","Ziapatra","Zethina","Zetkin","Zee" };

	public static String[] lastNames = new String[] { 
		"Arla","Akadia","Atella","Aenid","Alpha",
		"Brulla","Chaldia","Brutilla","Albia","Blaze",
		"Darl","Cyrine","Callidia","Borgia","Blue",
		"Fraka","Diona","Castella","Cimbria","Cat",
		"Fraal","Deatrix","Drustilla","Devi","Calamity",
		"Garma","Ethina","Flavia","Ephese","Dame",
		"Grisha","Ephrael","Gallia","Euphrati","Dice",
		"Graki","Fenria","Haxta","Inez","Flair",
		"Haka","Gaia","Intias","Imperatrice","Gold",
		"Jarra","Galatia","Jestilla","Jemadar","Gunner",
		"Karna","Hazael","Kalta", "Jezail","Hack",
		"Kaarli","Isha","Litila","Joss","Halo",
		"Krella","Ishta","Lupa","Kadis","Lady",
		"Lekka","Judicca","Meta","Lethe","Modesty",
		"Mira","Lyra","Nihila","Mae","Moll",
		"Narla","Magdela","Novia","Millicent","Pistol",
		"Orla","Narcia","Octia","Merica","Plex",
		"Phrix","Ophilia","Praetia","Midkiff","Pris",
		"Quali","Phebia","Quintilla","Megehra","Rat",
		"Rothra","Qualia","Raltia","Odessa","Red",
		"Ragaana","Rhia","Ravia","Orlean","Ruby",
		"Stigga","Salomis","Regia","Plath","Scarlet",
		"Stranga","Solaria","Scythia","Severine","Spike",
		"Thakka","Thyratia","Sila","Thiopia","Steel",
		"Ultha","Thebe","Taura","Thrace","Starr",
		"Varna","Uriel","Trantia","Tzarine","Trauma",
		"Wraxa","Veyda","Venria","Venus","Trick",
		"Yarni","Xantippe","Xanthia","Walperga","Trix",
		"Zekka","Ziapatra","Zethina","Zetkin","Zee", 
		"Arl","Barak","Atellus","Alaric","Able",
		"Brull","Cain","Brutis","Attilas","Bones",
		"Dar","Dariel","Callidon","Barbosa","Crisis",
		"Frak","Eli","Castus","Cortez","Cutter",
		"Fral","Enoch","Drustos","Constantine","Dekko",
		"Garm","Frastus","Flavion","Cromwell","Dakka",
		"Grish","Gaius","Gallus","Dorn","Frag",
		"Grak","Garvel","Haxtes","Drake","Flair",
		"Hak","Hastus","Intios","Eisen","Finial",
		"Jarr","Ignace","Jastilus","Ferrus","Grim",
		"Kar","Ishmael","Kaltos","Grendal","Gob",
		"Kaarl","Jericus","Litilus","Guilliman","Gunner",
		"Krell","Lazerus","Mallear","Iacton","Jakes",
		"Mar","Mordeci","Metalus","Jaghatai","Krak",
		"Mir","Mirthas","Nihilius","Khan","Lug",
		"Narl","Nicodemus","Novus","Leman","Mongrel",
		"Orl","Pontus","Octus","Lionus","Plex",
		"Phrenz","Quint","Praetus","Magnus","Rat",
		"Quarl","Rabalias","Quintos","Mercutio","Red",
		"Roth","Reestheus","Raltus","Nixios","Sawney",
		"Ragaa","Silvanus","Ravion","Ramirez","Scab",
		"Stig","Solomon","Regis","Serghar","Scammer",
		"Strag","Thaddius","Severus","Sigismund","Skive",
		"Thak","Titus","Silon","Tybalt","Shanks",
		"Ulth","Uriah","Tauron","Vern","Shiv",
		"Varn","Varnias","Trantor","Wolfe","Sham",
		"Wrax","Xerxes","Venris","Wollsey","Stern",
		"Yarn","Zaddion","Victus","Zane","Stubber",
		"Zak","Zuriel","Xanthis","Zarkov","Verbal"   

	};


	public static String getName() {
		return getName(Math.random() <= 0.5);
	}

	public static String getName(boolean male) {
		Random rand = new Random();
		String name = "";

		if(male)
			name = maleNames[rand.nextInt(maleNames.length)];
		else
			name = femaleNames[rand.nextInt(femaleNames.length)];

		name += " " + lastNames[rand.nextInt(lastNames.length)];

		return name;
	}
}

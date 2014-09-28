package com.voidpixel.village.main;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class GameResources {
	//I wish to be able to access these images anywhere, no matter what
	protected static HashMap<String, Image> imageResources = new HashMap<String, Image>();
	
	public static Image getImage(String name) {
		if(!imageResources.containsKey(name)) {
			return loadImage(name, "image/" + name + ".png");	
		}else{
			return imageResources.get(name);
		}
	}
	
	public static Image loadImage(String name, String path) {
		String fullPath = GameResources.getResourceFolder() + path;
		System.out.println("Loading resource " + name + " [" + fullPath + "]");
		
		try{			
			Image img = ImageIO.read(new File(fullPath));
			if(img != null) {
				System.out.println(" - done");
				imageResources.put(name, img);
				return img;
			}
		}catch(Exception e) {
			System.err.println(" - could not load " + name + ": " + e.getMessage() + " ["+ fullPath +"]");
		}
			
		imageResources.put(name, null);
		return null;
	}
	
	public static String getResourceFolder() {
		return System.getProperty("user.dir") + "/resources/";
	}
	
	public static int size() {
		return imageResources.size();
	}
}

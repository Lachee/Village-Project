package com.voidpixel.village.main;

import java.awt.Point;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Set;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	public static Canvas canvas;
	
	public static int TYPE_NONE = 0, TYPE_PRESSED = 1, TYPE_DOWN = 2, TYPE_RELEASED = 3;	
	public static int MOUSE_LEFT = 0, MOUSE_MIDDLE = 1, MOUSE_RIGHT = 2;
	protected static HashMap<Integer, Integer> keyEvents = new HashMap<Integer, Integer>();
	protected static int[] mouseEvents = new int[3];
	protected static double mouseScroll = 0;
	protected static Point mousePoint = new Point(0, 0);
	
	public static Point getMousePosition() {
		if(mousePoint == null) return new Point();
		return mousePoint;
	}
	
	public static double getMouseScroll() {
		return mouseScroll;
	}
	
	/**
	 * Get's if the mouse button is down
	 * @param mouseButton. 0 is left, 1 is middle and 2 is right
	 * @return if its down this frame only
	 */
	public static boolean getMouseDown(int mouseButton) {
		if(mouseButton < 0 || mouseButton >= 3) return false;
		return mouseEvents[mouseButton] == TYPE_PRESSED;
	}
	public static boolean getMouseUp(int mouseButton) {
		if(mouseButton < 0 || mouseButton >= 3) return false;
		return mouseEvents[mouseButton] == TYPE_RELEASED;
	}
	public static boolean getMouse(int mouseButton) {
		if(mouseButton < 0 || mouseButton >= 3) return false;
		return mouseEvents[mouseButton] == TYPE_DOWN;
	}
	
	public static boolean getAnyKey() {
		Set<Integer> keys = keyEvents.keySet();
		for(Integer key : keys) {
			if(getKey(key)) return true;
		}
		
		return false;
	}
	public static boolean getKey(int key) {
		return keyEvents.containsKey(key) ? keyEvents.get(key) == Input.TYPE_DOWN : false;
	}
	public static boolean getKeyDown(int key) {
		return keyEvents.containsKey(key) ? keyEvents.get(key) == Input.TYPE_PRESSED : false;
	}	
	public static boolean getKeyUp(int key) {
		return keyEvents.containsKey(key) ? keyEvents.get(key) == Input.TYPE_RELEASED : false;
	}
	
	
	public static void update() {
		mouseScroll = 0;

		if(canvas != null && canvas.getMousePosition() != null)
			mousePoint = canvas.getMousePosition();
		
		
		for(int i = 0; i < 3; i++) {
			if(mouseEvents[i] == TYPE_RELEASED)
				mouseEvents[i] = TYPE_NONE;
			
			if(mouseEvents[i] == TYPE_PRESSED)
				mouseEvents[i] = TYPE_DOWN;
		}
		
		Set<Integer> keys = keyEvents.keySet();
		for(Integer key : keys) {
			
			int type = keyEvents.get(key);
			if(type == TYPE_RELEASED)
				keyEvents.put(key, TYPE_NONE);
			
			if(type == TYPE_PRESSED)
				keyEvents.put(key, TYPE_DOWN);				
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//If this key is already down, end it here!
		if(Input.getKey(e.getKeyCode())) return;
		
		//Otherwise, add the key to the list!
		keyEvents.put(e.getKeyCode(), Input.TYPE_PRESSED);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keyEvents.put(e.getKeyCode(), Input.TYPE_RELEASED);
	}


	//Used Listeners
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseScroll = -e.getPreciseWheelRotation();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//mousePoint = e.getPoint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		//mousePoint = e.getPoint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int code = e.getButton() - 1;
		if(code >= 3 || mouseEvents[code] == TYPE_DOWN) return;
		mouseEvents[code] = TYPE_PRESSED;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int code = e.getButton() - 1;
		if(code >= 3) return;
		mouseEvents[code] = TYPE_RELEASED;
	}
	
	
	//Unused Listeners
	@Override
	public void mouseClicked(MouseEvent e) {}
	

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	

}

package com.voidpixel.village.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import com.voidpixel.village.interfaces.listeners.*;

public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public static ArrayList<InputKeyListener> keyListeners = new ArrayList<InputKeyListener>();
	public static ArrayList<InputMouseListener> mouseListeners = new ArrayList<InputMouseListener>();
	
	public static void registerKeyListener(InputKeyListener l) {
		keyListeners.add(l);
	}
	public static void registerMouseListener(InputMouseListener l) {
		mouseListeners.add(l);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {		
		for(InputKeyListener l : keyListeners)
			l.OnKeyPressed(e);	
	}

	@Override
	public void keyReleased(KeyEvent e) {		
		for(InputKeyListener l : keyListeners)
			l.OnKeyReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(InputMouseListener l : mouseListeners)
			l.OnMousePressed(e);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(InputMouseListener l : mouseListeners)
			l.OnMouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		for(InputMouseListener l : mouseListeners)
			l.OnMouseMoved(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(InputMouseListener l : mouseListeners)
			l.OnMouseMoved(e.getPoint());
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for(InputMouseListener l : mouseListeners)
			l.OnMouseScroll(e);
		
	}

}

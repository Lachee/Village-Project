package com.voidpixel.village.interfaces.listeners;

import java.awt.event.KeyEvent;

public abstract interface InputKeyListener {
	public void OnKeyPressed(KeyEvent e);
	public void OnKeyReleased(KeyEvent e);
}

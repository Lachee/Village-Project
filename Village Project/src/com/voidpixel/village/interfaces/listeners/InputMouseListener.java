package com.voidpixel.village.interfaces.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface InputMouseListener {
	public void OnMousePressed(MouseEvent e);
	public void OnMouseReleased(MouseEvent e);
	public void OnMouseMoved(Point mousePoint);
	public void OnMouseScroll(MouseWheelEvent e);
}

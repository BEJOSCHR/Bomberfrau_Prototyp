/*
 * MouseHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Verwaltet alle Mouse-Evente die vom Frame aus aufgerufen werden
 */
package uni.bombenstimmung.de.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import uni.bombenstimmung.de.objects.MouseActionArea;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int MAPPING_X = 7, MAPPING_Y = 29;
	private static int mouseX = 0, mouseY = 0;
	
	public void mouseWheelMoved(MouseWheelEvent e) {}

	public void mouseDragged(MouseEvent e) {
		mouseMove(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		mouseMove(e.getX(), e.getY());
	}

	public void mouseClicked(MouseEvent e) {} //Wird durck Press und release abgedeckt

	public void mousePressed(MouseEvent e) {
		mousePress(e.getX(), e.getY(), e.getButton());
	}

	public void mouseReleased(MouseEvent e) {
		mouseRelease(e.getX(), e.getY(), e.getButton());
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	/**
	 * Wird aufgerufen wenn sich die Maus bewegt
	 * @param x - Die X-Koorinate der Maus
	 * @param y - Die Y-Koorinate der Maus
	 */
	private static void mouseMove(int x, int y) {
		
		mouseX = x-MAPPING_X;
		mouseY = y-MAPPING_Y;
		
	}
	
	/**
	 * Wird aufgerufen wenn eine Maustaste GEDRÜCKT wird
	 * @param x - Die X-Koorinate der Maus
	 * @param y - Die Y-Koorinate der Maus
	 * @param key - Der KeyCode der eingedrückten Taste ({@link MouseEvent}, BUTTON1 = LINKS, BUTTON2 = MIDDLE, BUTTON3 = RECHTS)
	 */
	private static void mousePress(int x, int y, int key) {
		
		if(key == MouseEvent.BUTTON1) {
			//LINKS
			
			//MAA
			for(MouseActionArea maa : MouseActionAreaHandler.mouseActionAreas) {
				if(maa.isActiv() && maa.isHovered()) {
					maa.performAction_LEFT_PRESS();
				}
			}
			
		}else if(key == MouseEvent.BUTTON3) {
			//RECHTS
			
			//MAA
			for(MouseActionArea maa : MouseActionAreaHandler.mouseActionAreas) {
				if(maa.isActiv() && maa.isHovered()) {
					maa.performAction_RIGHT_PRESS();
				}
			}
			
		}
		
	}
	
	/**
	 * Wird aufgerufen wenn eine Maustaste LOSGELASSEN wird
	 * @param x - Die X-Koorinate der Maus
	 * @param y - Die Y-Koorinate der Maus
	 * @param key - Der KeyCode der losgelassenen Taste ({@link MouseEvent})
	 */
	private static void mouseRelease(int x, int y, int key) {
		
		if(key == MouseEvent.BUTTON1) {
			//LINKS
			
			//MAA
			for(MouseActionArea maa : MouseActionAreaHandler.mouseActionAreas) {
				if(maa.isActiv() && maa.isHovered()) {
					maa.performAction_LEFT_RELEASE();
				}
			}
			
		}else if(key == MouseEvent.BUTTON3) {
			//RECHTS
			
			//MAA
			for(MouseActionArea maa : MouseActionAreaHandler.mouseActionAreas) {
				if(maa.isActiv() && maa.isHovered()) {
					maa.performAction_RIGHT_RELEASE();
				}
			}
			
		}
		
	}
	
	public static int getMouseX() {
		return mouseX;
	}
	public static int getMouseY() {
		return mouseY;
	}
	
}

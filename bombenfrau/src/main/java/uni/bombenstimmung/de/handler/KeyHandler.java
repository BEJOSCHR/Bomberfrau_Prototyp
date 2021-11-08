/*
 * keyHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Verwaltet alle Tastatur-Events die vom Frame aus aufgerufen werden
 */

package uni.bombenstimmung.de.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public void keyTyped(KeyEvent e) {}

	/**
	 * Wird vom Frame aufgerufen wenn eine Taste gedrückt wird.
	 * Vergleiche den keyCode mit KeyEvent. und dann die jeweilige Taste (zb. keyCode == KeyEvent.VK_ENTER)
	 */
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		switch(keyCode) {
		case KeyEvent.VK_W:
			MovementHandler.movementValue = 1;
			break;
		case KeyEvent.VK_A:
			MovementHandler.movementValue = 2;		
			break;
		case KeyEvent.VK_S:
			MovementHandler.movementValue = -1;
			break;
		case KeyEvent.VK_D:
			MovementHandler.movementValue = -2;
			break;
		default:
			//Kein Event für diese taste regestriert
			break;
		}
		
	}

	/**
	 * Wird vom Frame aufgerufen wenn eine Taste losgelassen wird.
	 * Vergleiche den keyCode mit KeyEvent. und dann die jeweilige Taste (zb. keyCode == KeyEvent.VK_ENTER)
	 */
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		switch(keyCode) {
		case KeyEvent.VK_W:
			if(MovementHandler.movementValue == 1) {
				MovementHandler.movementValue = 0;
			}
			break;
		case KeyEvent.VK_A:
			if(MovementHandler.movementValue == 2) {
				MovementHandler.movementValue = 0;
			}	
			break;
		case KeyEvent.VK_S:
			if(MovementHandler.movementValue == -1) {
				MovementHandler.movementValue = 0;
			}
			break;
		case KeyEvent.VK_D:
			if(MovementHandler.movementValue == -2) {
				MovementHandler.movementValue = 0;
			}
			break;
		default:
			//Kein Event für diese taste regestriert
			break;
		}
		
	}

}

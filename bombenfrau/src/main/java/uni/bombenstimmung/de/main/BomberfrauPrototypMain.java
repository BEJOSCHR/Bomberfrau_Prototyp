/*
 * BomberfrauPrototypMain
 *
 * Version 1.0
 * Author: Benni
 *
 * Der Beginn des Prototyps
 */

package uni.bombenstimmung.de.main;

import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.handler.MouseActionAreaHandler;

public class BomberfrauPrototypMain {
	
	public static final String AUTHOR = "Bombenstimmung - Uni Wuppertal - ©2021";
	public static final String VERSION = "Prototyp - Alpha - V0.0.1";
	
	/**
	 * Der Start von allem (in diesem Projekt)
	 * @param args Die Argumente die dem Start mitgegeben werden können
	 */
	public static void main(String[] args) {
		
		ConsoleDebugger.printMessage("Starting Bomberfrau PROTOTYP!");
		
		//START GRAPHICS (Muss als erstes sein da Frame width und height gesetzt werden)
		GraphicsHandler.initGraphics();
		
		//MAA INIT
		MouseActionAreaHandler.initMAAs();
		
	}
	
}


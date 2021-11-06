/*
 * MouseActionAreaHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Dient als statischer Verwalter der MAA Objekte
 */

package uni.bombenstimmung.de.handler;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

import uni.bombenstimmung.de.graphics.GraphicsData;
import uni.bombenstimmung.de.objects.MouseActionArea;

public class MouseActionAreaHandler {

	public static List<MouseActionArea> mouseActionAreas = new ArrayList<MouseActionArea>();
	
	/**
	 * Initialisiert alle MMAs mit ihren Custom Methoden (@Overwrite)
	 */
	public static void initMAAs() {
		
		int exit_width = 200, exit_height = 30;
		new MouseActionArea(GraphicsData.width/2-exit_width/2, GraphicsData.height/2-exit_height/2, exit_width, exit_height, "id_Exit", "Exit", 24, Color.WHITE, Color.ORANGE) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				System.exit(0);
			}
			
			@Override
			public boolean isActiv() {
				return true; //TODO
			}
			
		};
		
	}
	
}

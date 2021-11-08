/*
 * DrawParts
 *
 * Version 1.0
 * Author: Benni
 *
 * Stellt die einzelnen Parts dar die unterschiedlich dargestellt werden (Menu, Optionen, Spiel, Lobby...)
 * Wird vom Label je nach DrawState aufgerufen
 */

package uni.bombenstimmung.de.graphics;

import java.awt.Color;
import java.awt.Graphics;

import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.main.BomberfrauPrototypMain;

public class DrawParts {

	/**
	 * Wird aufgerufen wenn der DrawState auf Menu steht ({@link DisplayType}) und ist für die Darstellung des Menu verantwortlich
	 * @param g - Der Grafikparameter
	 */
	public static void drawMenu(Graphics g) {
		
		//BACKGROUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, GraphicsData.width, GraphicsData.height);
		
		//TITLE
		int topBorder = 180, borderBetweenText = 40;
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 44, "BOMBERFRAU", GraphicsData.width/2, 0+topBorder);
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 28, "Prototyp", GraphicsData.width/2, 0+topBorder+borderBetweenText);
		
		//AUTHOR NOTE
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 12, BomberfrauPrototypMain.AUTHOR, GraphicsData.width/2, GraphicsData.height-30);
		//VERSION NOTE
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 12, BomberfrauPrototypMain.VERSION, GraphicsData.width/2, GraphicsData.height-15);
		
	}
	
	/**
	 * Wird aufgerufen wenn der DrawState auf Game steht ({@link DisplayType}) und ist für die Darstellung des Games verantwortlich
	 * @param g - Der Grafikparameter
	 */
	public static void drawInGame(Graphics g) {
		
		if(GameData.runningGame != null) {
			GameData.runningGame.drawGame(g);
		}
		
	}
	
}

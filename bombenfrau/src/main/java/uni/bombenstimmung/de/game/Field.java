/*
 * Field
 *
 * Version 1.0
 * Author: Benni
 *
 * Das Objekt das ein Field für die Map repräsentiert, vorallem seinen FieldType
 */

package uni.bombenstimmung.de.game;

import java.awt.Color;
import java.awt.Graphics;

import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.objects.Player;

public class Field {

	private int X, Y;
	private FieldType type;
	
	/**
	 * Das Field Objekt das jeweils einen Teil der Map darstellt
	 * @param X - int - Die X-Koordinate
	 * @param Y - int - Die Y-Koordinate
	 * @param type - {@link FieldType} - Der Type des Fields
	 */
	public Field(int X, int Y, FieldType type) {
		
		this.X = X;
		this.Y = Y;
		this.type = type;
		
	}
	
	/**
	 * Wird aufgerufen wenn eine Bombe dieses Feld trifft
	 */
	public void explode() {
		
		int lokalPlayerX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(GameData.runningGame.getMoveX(), true), true);
		int lokalPlayerY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(GameData.runningGame.getMoveY(), false), false);
		if(lokalPlayerX == this.X && lokalPlayerY == this.Y) {
			//THIS PLAYER HIT
			if(GameData.runningGame.isExploded() == false) {
				GameData.runningGame.explode();
			}
		}
		
		for(Player player : GameData.runningGame.getPlayers()) {
			int playerX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getX(), true), true);
			int playerY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getY(), false), false);
			if(playerX == this.X && playerY == this.Y) {
				//PLAYER HIT
				if(player.isExploded() == false) {
					player.explode();
				}
			}
		}
		
		if(this.type == FieldType.WALL) {
			this.changeType(FieldType.DEFAULT);
		}
		
		//CHECK FOR WIN
		int alive = 0;
		if(GameData.runningGame.isExploded() == false) {
			alive++;
		}
		for(Player player : GameData.runningGame.getPlayers()) {
			if(player.isExploded() == false) {
				alive++;
			}
		}
		if(alive <= 1) {
			GameData.runningGame.finishGame();
		}
		
	}
	
	/**
	 * Stellt das field dar
	 * @param g - Das Graphics-Objekt
	 */
	public void draw(Graphics g) {
		
		int pixelX = GraphicsHandler.getPixlesByCoordinate(X, true);
		int pixelY = GraphicsHandler.getPixlesByCoordinate(Y, false);
		
		g.setColor(FieldType.getColorForFieldType(type));
		g.fillRect(pixelX, pixelY, GameData.FIELD_DIMENSION, GameData.FIELD_DIMENSION);
		
	}
	
	/**
	 * Highlightet das field dar
	 * @param g - Das Graphics-Objekt
	 * @param color - Die Farbe des Highlights
	 */
	public void drawHighlight(Graphics g, Color color) {
		
		int pixelX = GraphicsHandler.getPixlesByCoordinate(X, true);
		int pixelY = GraphicsHandler.getPixlesByCoordinate(Y, false);
		
		g.setColor(color);
		g.drawRect(pixelX-1, pixelY-1, GameData.FIELD_DIMENSION, GameData.FIELD_DIMENSION);
		
	}
	
	/**
	 * Wechselt den type dieses Fields
	 * @param newType - Der neue Type
	 */
	public void changeType(FieldType newType) {
		this.type = newType;
	}
	
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
	public FieldType getType() {
		return type;
	}
	
}

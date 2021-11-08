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

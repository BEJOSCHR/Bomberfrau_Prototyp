/*
 * MouseActionArea
 *
 * Version 1.0
 * Author: Benni
 *
 * Die eigene Variante eines Buttons mit einigen mehr M�glichkeiten
 */

package uni.bombenstimmung.de.objects;

import java.util.ConcurrentModificationException;

import java.awt.Color;
import java.awt.Graphics;

import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.handler.MouseActionAreaHandler;
import uni.bombenstimmung.de.handler.MouseHandler;

public class MouseActionArea {

	private int x = 0, y = 0, width = 0, height = 0;
	private String idName = "missingName", displayText = "missing";
	private int textSize = 20;
	private Color standardColor = Color.WHITE;
	private Color hoverColor = Color.WHITE;
	
//==========================================================================================================
	/**
	 * Die Representation eines Buttons
	 * @param x - int - Die X-Koordinate in der obenren linken Ecke
	 * @param y - int - Die Y-Koordinate in der obenren linken Ecke
	 * @param width - int - Die Breite der Area
	 * @param height - int - Die H�he der Area
	 * @param idName - String - Der Name der diese Are von den anderen Unterscheidet bzw zum identifizieren genutzt wird
	 * @param displayText - String - Der dargestellte Text in der MAA (mittig)
	 * @param textSize - int - Die Schriftgr��e des Textes
	 * @param standardColor - {@link Color} - Die Farbe die der Rand der Area standardm��ig hat
	 * @param hoverColor - {@link Color} - Die Farbe, die der Rand der Area beim Hovern hat
	 **/
	public MouseActionArea(int x, int y, int width, int height, String idName, String displayText, int textSize, Color standardColor, Color hoverColor) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.idName = idName;
		this.displayText = displayText;
		this.textSize = textSize;
		this.standardColor = standardColor;
		this.hoverColor = hoverColor;
		
		//MAKES SURE THAT CONCURRENT ERRORS DONT PREVENT ADDING
		while(!MouseActionAreaHandler.mouseActionAreas.contains(this)) {
			try{
				MouseActionAreaHandler.mouseActionAreas.add(this);
			}catch(ConcurrentModificationException error) {}
		}
		
	}
	
//==========================================================================================================
	/**
	 * �berpr�ft ob sich bestimmte Koordinaten in der Area dieser MAA befinden
	 * @param mouseX - int - Die X-Koordinate
	 * @param mouseY - int - Die Y-Koordinate
	 * @return boolean - True wenn sich die Koordinaten in der Area befindet, false wenn nicht (Auch false wenn nicht aktive!)
	 **/
	public boolean checkArea(int mouseX, int mouseY) {
		
		if(isActiv() == true) {
			if(mouseX >= x && mouseX <= x+width  &&  mouseY >= y && mouseY <= y+height) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}
	
//==========================================================================================================
	/**
	 * �berpr�ft ob die Mause in der Area ist (Also �ber die Area hovered)
	 * @return boolean - Den selben status von checkArea() nur mit den Mouse-Koorinaten als Input
	 **/
	public boolean isHovered() {
		return checkArea(MouseHandler.getMouseX(), MouseHandler.getMouseY());
	}
	
//==========================================================================================================
	/**
	 * �berpr�ft ob diese Area gerade aktiv ist.
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 * @return boolean - True wenn die Aktivierungsbedingung erf�llt ist, sont false (Wenn nicht �berschrieben/ver�ndert dann standardm��ig true)
	 **/
	public boolean isActiv() {
		return true;
	}
	
//==========================================================================================================
	/**
	 * Wird aufgerufen wenn diese MAA mit der LINKEN Taste GEDR�CKT wird - LEFT PRESS
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 **/
	public void performAction_LEFT_PRESS() { }
//==========================================================================================================
	/**
	 * Wird aufgerufen wenn diese MAA mit der RECHTEN Taste GEDR�CKT wird - RIGHT PRESS
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 **/
	public void performAction_RIGHT_PRESS() { }
//==========================================================================================================
	/**
	 * Wird aufgerufen wenn diese MAA mit der LINKEN Taste LOSGELASSEN wird - LEFT RELEASE
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 **/
	public void performAction_LEFT_RELEASE() { }
//==========================================================================================================
	/**
	 * Wird aufgerufen wenn diese MAA mit der RECHTEN Taste LOSGELASSEN wird - RIGHT RELEASE
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 **/
	public void performAction_RIGHT_RELEASE() { }
	
//==========================================================================================================
	/**
	 * Der graphische Darstellungsteil jeder MAA (Wird nur aufgerufen wenn die MAA aktiv ist)
	 * Standardm��ig wird der Rand der MAA in der jeweiligen Farbe (Abh�ngig ob gehovered oder nicht) mit dem dispplaytext dargestellt
	 * Custom ver�nderungen k�nnen in der drawCustomParts methode �berschrieben/ver�ndert (@Overwrite) werden
	 * @param g - {@link Graphics} - Der Grafik-Parameter
	 **/
	public void draw(Graphics g) { 
		
		if(isHovered()) {
			g.setColor(hoverColor);
			g.drawRect(x, y, width, height);
			GraphicsHandler.drawCentralisedText(g, hoverColor, textSize, displayText, x+width/2, y+height/2); 
		}else {
			g.setColor(standardColor);
			g.drawRect(x, y, width, height);
			GraphicsHandler.drawCentralisedText(g, standardColor, textSize, displayText, x+width/2, y+height/2); 
		}
		drawCustomParts(g);
		
	}
	
	/**
	 * Stellt zus�tzliche grafische Ver�nderungen dar
	 * (Soll �berschrieben/ver�ndert werden wenn eine neue MAA erzeugt wird (@Overwrite))
	 * @param g - {@link Graphics} - Der Grafik-Parameter
	 */
	public void drawCustomParts(Graphics g) {}
	
//==========================================================================================================
	/**
	 * Removes/Delete this actionArea
	 **/
	public void remove() {
		
		while(MouseActionAreaHandler.mouseActionAreas.contains(this)) {
			try{
				MouseActionAreaHandler.mouseActionAreas.remove(this);
			}catch(ConcurrentModificationException error) {}
		}
		
	}
	
	public String getIdName() {
		return idName;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}

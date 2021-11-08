/*
 * GraphicsHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Setup und Verwaltung der graphischen Leistungen
 */

package uni.bombenstimmung.de.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.handler.KeyHandler;
import uni.bombenstimmung.de.handler.MouseHandler;
import uni.bombenstimmung.de.handler.WindowHandler;
import uni.bombenstimmung.de.main.ConsoleDebugger;

public class GraphicsHandler {
	
	/**
	 * Allgemeine methode um einen beliebigen text mit parametern relativ zu einem Punkt (x,y) mittig darzustellen
	 * @param g, das Graphics object
	 * @param color, die Textfarbe
	 * @param textSize, die Textgröße
	 * @param text, der eigentliche Text
	 * @param x, die X-Koordinate (Links-Rechts-Verschiebung) zu der der Text mittig dargestellt wird
	 * @param y, die Y-Koordinate (Oben-Unten-Verschiebung) zu der der Text mittig dargestellt wird
	 */
	public static void drawCentralisedText(Graphics g, Color color, int textSize, String text, int x, int y) {
		
		g.setColor(color);
		g.setFont(new Font("Arial", Font.BOLD, textSize));
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight()*2/3;
		g.drawString(text, x-width/2, y+height/2);
		
	}
	
	/**
	 * Berechnet die Pixel-Koordinate einer Field-Koordinate relativ dazu wie gerade das Bild "verschoben ist".
	 * Ein Pixel-Ergebnis kann auch außerhalb der eigentlichen Screen Größe liegen, sollte dann aber nicht dargestellt werden
	 * @param coordinate - int - Die Field-Koordindate
	 * @param isItX - boolean - Gibt an ob wir den X oder Y Pixelwert zu der gegebenen Koordinate berechnen wollen
	 * @return Den berechneten Wert oder -1 wenn kein Game läuft
	 */
	public static int getPixlesByCoordinate(int coordinate, boolean isItX) {
		
		if(GameData.runningGame == null) {
			ConsoleDebugger.printMessage("Cant calculate Pixelcoordinate: No running game!");
			return -1;
		}
		
		//NORMAL GAME
		int totalMapDimension = GameData.FIELD_DIMENSION*GameData.MAP_DIMENSION;
		if(isItX == true) {
			//X
			return GraphicsData.width/2-totalMapDimension/2+(coordinate*GameData.FIELD_DIMENSION);
		}else {
			//Y
			return GraphicsData.height/2-totalMapDimension/2+(coordinate*GameData.FIELD_DIMENSION);
		}
		
	}
	
	/**
	 * Berechnet die Field-Koordinate einer Pixel-Koordinate relativ dazu wie gerade das Bild "verschoben ist".
	 * Ein Field-Ergebnis kann auch außerhalb der eigentlichen Screen Größe liegen, sollte dann aber nicht dargestellt werden
	 * @param pixel - int - Die Field-Koordindate
	 * @param isItX - boolean - Gibt an ob wir den X oder Y Wert berechnen wollen
	 * @return Den berechneten Wert oder -1 wenn kein Game läuft
	 */
	public static int getCoordianteByPixel(int pixel, boolean isItX) {
		
		if(GameData.runningGame == null) {
			ConsoleDebugger.printMessage("Cant calculate field coordinate: No running game!");
			return -1;
		}
		
		//NORMAL GAME
		int totalMapDimension = GameData.FIELD_DIMENSION*GameData.MAP_DIMENSION;
		if(isItX == true) {
			//X
			return (pixel-GraphicsData.width/2+totalMapDimension/2)/GameData.FIELD_DIMENSION;
		}else {
			//Y
			return (pixel-GraphicsData.height/2+totalMapDimension/2)/GameData.FIELD_DIMENSION;
		}
		
	}
	
	/**
	 * Berechnet die Koordinaten des Spielers an denen dieser dargestellt wird abhängig von seinem Movefaktor
	 * @param moveFactor - int - Der Faktor der die Verscheibung des Spielers relativ zur Oberen-Linken-Ecke angibt
	 * @param isItX - boolean - Gibt an ob wir den X oder Y Wert berechnen wollen
	 * @return - Den berechneten Wert oder -1 wenn kein Game läuft
	 */
	public static int getPlayerCoordianteByMoveFactor(int moveFactor, boolean isItX) {
		
		if(GameData.runningGame == null) {
			ConsoleDebugger.printMessage("Cant calculate player coordinate: No running game!");
			return -1;
		}
		
		//NORMAL GAME
		if(isItX == true) {
			//X
			return GraphicsHandler.getPixlesByCoordinate(0, true)+moveFactor;
		}else {
			//Y
			return GraphicsHandler.getPixlesByCoordinate(0, false)+moveFactor;
		}
		
	}
	
	/**
	 * Erstellt und initialisiert alle grafischen Komponenten
	 * Insbesondere den frame und das zugehörige label
	 */
	public static void initGraphics() {
		
		GraphicsData.frame = createFrame();
		GraphicsData.label = new Label();
		System.out.println("Created Graphics");
		
	}
	
	/**
	 * Erstellt einen eingestellten Frame
	 * @return {@link JFrame} der eingestellte Frame
	 */
	private static JFrame createFrame() {
		
		JFrame tempFrame = new JFrame();
		
		tempFrame.setLocationRelativeTo(null);
		tempFrame.setLocation(0, 0);
		tempFrame.setTitle("BomberFrau - Prototyp");
		tempFrame.setResizable(false);
		tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tempFrame.setUndecorated(false); //TODO 
		tempFrame.setVisible(true);
		
		tempFrame.addKeyListener(new KeyHandler());
		tempFrame.addMouseListener(new MouseHandler());
		tempFrame.addMouseMotionListener(new MouseHandler());
		tempFrame.addMouseWheelListener(new MouseHandler());
		tempFrame.addWindowListener(new WindowHandler());
		
//		try { //TRY TO SET ICON
//			tempFrame.setIconImage(ImageIO.read(Bomberfrau_Main.class.getResourceAsStream("images/Icon.png")));
//		} catch (Exception error) {
//			System.out.println("The Window Icon couldn't be loaded!");
//		}
		
		tempFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		Frame1.setSize(1400, 800);
		tempFrame.setPreferredSize(tempFrame.getSize());
		tempFrame.setMinimumSize(tempFrame.getSize());
		tempFrame.setMaximumSize(tempFrame.getSize());
		
		GraphicsData.width = tempFrame.getWidth();
		GraphicsData.height = tempFrame.getHeight();
		
		GameData.FIELD_DIMENSION = (int) ((GraphicsData.height-GameData.MAP_SIDE_BORDER*2)/GameData.MAP_DIMENSION);
		GameData.PLAYER_DIMENSION = (int) (GameData.FIELD_DIMENSION*0.75);
		GameData.BOMB_DIMENSION = (int) (GameData.FIELD_DIMENSION*0.65);
		
		tempFrame.requestFocus();
		
		return tempFrame;
	}

	
}

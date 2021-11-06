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

import uni.bombenstimmung.de.handler.MouseHandler;

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
		tempFrame.setUndecorated(true); 
		tempFrame.setVisible(true);
		
//		tempFrame.addKeyListener(new InputHandler());
		tempFrame.addMouseListener(new MouseHandler());
		tempFrame.addMouseMotionListener(new MouseHandler());
		tempFrame.addMouseWheelListener(new MouseHandler());
//		tempFrame.addWindowListener(new InputHandler());
		
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
		
		tempFrame.requestFocus();
		
		return tempFrame;
	}

	
}

/*
 * Label
 *
 * Version 1.0
 * Author: Benni
 *
 * Eigene Label Klasse, die eingebetet in den Frame das eigentliche darstellen übernimmt
 */

package uni.bombenstimmung.de.graphics;

import java.awt.*;

import javax.swing.JLabel;

import uni.bombenstimmung.de.handler.MouseActionAreaHandler;
import uni.bombenstimmung.de.main.ConsoleDebugger;
import uni.bombenstimmung.de.objects.MouseActionArea;

@SuppressWarnings("serial")
public class Label extends JLabel {

	//FPS
	private int displayedFPS = 0;
	private long nextSecond = System.currentTimeMillis() + 1000;
	private int FramesInCurrentSecond = 0;
	private int FramesInLastSecond = 0;
	private long nextRepaintDelay = 0;
	private int maxFPS = 100;
	
//==========================================================================================================
	/**
	 * The first settings of the new label - Constructor
	 */
	public Label() {
		
		this.setBounds(0, 0, GraphicsData.width, GraphicsData.height);
		this.setVisible(true);
		GraphicsData.frame.add(this, BorderLayout.CENTER);
		
	}
	
//==========================================================================================================
	/**
	 * Die methode die dauerhaft aufgerufen wird vom {@link JLabel} und somit die FPS representiert
	 * Enthält automatische FPS Limitierung
	 * Von hier aus werden über das 'g' Komponent alle grafischen Methoden aufgerufen
	 */
	protected void paintComponent(Graphics g) {
		
		//MAX FPS GRENZE SCHAFFEN
		long now = System.currentTimeMillis();
		try {
		   if (nextRepaintDelay > now) {
			   Thread.sleep(nextRepaintDelay - now);
		   }
		   nextRepaintDelay = now + 1000/(maxFPS-20);
		} catch (InterruptedException e) { }
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//(Reihenfolge der Aufrufe ist wichtig, spätere Aufrufe überschreiben frühere)
		
		//PARTS
		switch(GraphicsData.drawState) {
		case AFTERGAME:
			break;
		case INGAME:
			break;
		case LOBBY:
			break;
		case MENU:
			DrawParts.drawMenu(g);
			break;
		case OPTIONS:
			break;
		default:
			ConsoleDebugger.printMessage("Illegal drawState! Can't draw part for state '"+GraphicsData.drawState+"'");
			break;
		}
		
		//MAA
		for(MouseActionArea maa : MouseActionAreaHandler.mouseActionAreas) {
			if(maa.isActiv()) {
				maa.draw(g);
			}
		}
		
		//DRAW FPS
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString(""+getCurrentFPSValue(), 0+3, 0+12);
		
		//CALCULATE FPS
		calculateFPS();
		
		repaint();
		
	}
	
	
//==========================================================================================================
	/**
	 * Berechnet und updatet die FPS
	 */
	private void calculateFPS() {
		long currentTime = System.currentTimeMillis();
		if(currentTime > nextSecond) {
			nextSecond += 1000;
			FramesInLastSecond = FramesInCurrentSecond;
			FramesInCurrentSecond = 0;
		}
		FramesInCurrentSecond++;
		displayedFPS = FramesInLastSecond;
	}
	
//==========================================================================================================
	/**
	 * Gibt die derzeitigen FPS an
	 * @return {@link Integer}, die derzeitigen FPS
	 */
	public int getCurrentFPSValue() {
		return displayedFPS;
	}
	
}

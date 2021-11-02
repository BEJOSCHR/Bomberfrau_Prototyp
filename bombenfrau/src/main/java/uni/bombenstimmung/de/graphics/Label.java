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
		
		this.setBounds(0, 0, GraphicsData.WIDTH, GraphicsData.HEIGHT);
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
		
		//PARTS: (Reihenfolge der Aufrufe ist wichtig)
		draw_Background(g);
		draw_Title(g);
		
		//DRAW FPS
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString(""+getCurrentFPSValue(), 0+3, 0+12);
		
		//CALCULATE FPS
		calculateFPS();
		
		repaint();
		
	}
	
	/**
	 * Stellt den Titel dar
	 */
	private void draw_Title(Graphics g) {
		
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 26, "BOMBERFRAU", GraphicsData.WIDTH/2, GraphicsData.HEIGHT/2-100);
		
		GraphicsHandler.drawCentralisedText(g, Color.WHITE, 20, "Prototyp", GraphicsData.WIDTH/2, GraphicsData.HEIGHT/2-75);
		
	}

	/**
	 * Stellt den Hintergrund dar
	 */
	private void draw_Background(Graphics g) {
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, GraphicsData.WIDTH, GraphicsData.HEIGHT);
		
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

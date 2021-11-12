package uni.bombenstimmung.de.objects;

import java.awt.Color;
import java.awt.Graphics;

import uni.bombenstimmung.de.game.Field;
import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.main.ConsoleDebugger;

public class Bomb {

	private int playerID, x, y;
	private int liveTime = GameData.BOMB_LIVETIME;
	private int explodeRadius = GameData.BOMB_LIVETIME;
	
	/**
	 * Repr‰sentiert eine Bombe, wer h‰tte es gedacht
	 * @param x - int - X-Koordinate der Bombe
	 * @param y - int - Y-Koordinate der Bombe
	 * @param playerId - int - Die PlayerID des Spielers der die Bombe gelegt hat
	 * @param liveTime - int - Die Zeit in Sekunden bis die Bombe explodiert
	 * @param explodeRadius - int - Der Radius der bei der Explosion getroffen wird
	 */
	public Bomb(int playerId, int x, int y, int liveTime, int explodeRadius) {
		
		this.playerID = playerId;
		this.x = x;
		this.y = y;
		this.liveTime = liveTime;
		this.explodeRadius = explodeRadius;
		
	}
	
	/**
	 * Wird jede sekunde aufgerufen und z‰hlt runter und ruft bei 0 die explode funktion auf
	 */
	public void reduceLiveTime() {
		
		this.liveTime--;
		if(this.liveTime == 0) {
			this.explode();
		}else if(this.liveTime == -1) {
			GameData.runningGame.removeBomb(this.x, this.y);
		}
		
	}
	
	/**
	 * L‰sst die Bombe explodieren, heiﬂt das sie schaden auﬂenrum macht
	 */
	public void explode() {
		
		if(GameData.runningGame == null) {
			ConsoleDebugger.printMessage("Exploding bomb found no running game!");
			return;
		}
		
		Field thisField = GameData.runningGame.getMap()[this.x][this.y];
		thisField.explode();
		
		//LEFT RIGHT
		for(int xModifier = -this.explodeRadius ; xModifier <= this.explodeRadius ; xModifier++) {
			if(xModifier != 0) {
				int realX = this.x+xModifier;
				int realY = this.y;
				if(realX >= 0 && realX < GameData.MAP_DIMENSION && realY >= 0 && realY < GameData.MAP_DIMENSION) {
					Field field = GameData.runningGame.getMap()[realX][realY];
					field.explode();
				}
			}
		}
		
		//UP DOWN
		for(int yModifier = -this.explodeRadius ; yModifier <= this.explodeRadius ; yModifier++) {
			if(yModifier != 0) {
				int realX = this.x;
				int realY = this.y+yModifier;
				if(realX >= 0 && realX < GameData.MAP_DIMENSION && realY >= 0 && realY < GameData.MAP_DIMENSION) {
					Field field = GameData.runningGame.getMap()[realX][realY];
					field.explode();
				}
			}
		}
		
	}
	
	/**
	 * Stellt die Bombe grafisch dar
	 * @param g - Der Grafikparameter
	 */
	public void draw(Graphics g) {
		
		if(this.liveTime > 0) {
			int pixelX = GraphicsHandler.getPixlesByCoordinate(this.x, true)+GameData.FIELD_DIMENSION/2-GameData.BOMB_DIMENSION/2;
			int pixelY = GraphicsHandler.getPixlesByCoordinate(this.y, false)+GameData.FIELD_DIMENSION/2-GameData.BOMB_DIMENSION/2;
			
			g.setColor(Color.BLACK);
			g.fillOval(pixelX, pixelY, GameData.BOMB_DIMENSION, GameData.BOMB_DIMENSION);
			GraphicsHandler.drawCentralisedText(g, Color.RED, 20, ""+this.liveTime, pixelX+GameData.BOMB_DIMENSION/2, pixelY+GameData.BOMB_DIMENSION/2);
			
		}else {
			Field thisField = GameData.runningGame.getMap()[this.x][this.y];
			thisField.drawHighlight(g, Color.RED);
			
			//LEFT RIGHT
			for(int xModifier = -this.explodeRadius ; xModifier <= this.explodeRadius ; xModifier++) {
				if(xModifier != 0) {
					int realX = this.x+xModifier;
					int realY = this.y;
					if(realX >= 0 && realX < GameData.MAP_DIMENSION && realY >= 0 && realY < GameData.MAP_DIMENSION) {
						Field field = GameData.runningGame.getMap()[realX][realY];
						field.drawHighlight(g, Color.RED);
					}
				}
			}
			
			//UP DOWN
			for(int yModifier = -this.explodeRadius ; yModifier <= this.explodeRadius ; yModifier++) {
				if(yModifier != 0) {
					int realX = this.x;
					int realY = this.y+yModifier;
					if(realX >= 0 && realX < GameData.MAP_DIMENSION && realY >= 0 && realY < GameData.MAP_DIMENSION) {
						Field field = GameData.runningGame.getMap()[realX][realY];
						field.drawHighlight(g, Color.RED);
					}
				}
			}
		}
		
	}
	
	public int getPlayerID() {
		return playerID;
	}
	public int getLiveTime() {
		return liveTime;
	}
	public int getExplodeRadius() {
		return explodeRadius;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}

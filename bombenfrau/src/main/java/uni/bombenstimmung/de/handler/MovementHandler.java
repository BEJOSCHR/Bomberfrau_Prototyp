package uni.bombenstimmung.de.handler;

import java.util.Timer;
import java.util.TimerTask;

import uni.bombenstimmung.de.game.Field;
import uni.bombenstimmung.de.game.FieldType;
import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.objects.Player;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;
import uni.bombenstimmung.de.serverconnection.client.MinaClient;

public class MovementHandler {

	public static final int PIXEL_MOVEMENT_PER_TICK = 2;
	
	public static int movementValue = 0;
	private static Timer movementTimer = null;
	
	/**
	 * Startet den Timer der für die Karten Bewegung verantwortlich ist.
	 * Wird automatisch gestoppt wenn kein Spiel läuft!
	 */
	public static void startMovementTimer() {
		
		if(movementTimer == null) {
			
			movementTimer = new Timer();
			movementTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					
					if(GameData.runningGame == null) {
						this.cancel();
						movementTimer = null;
						return;
					}
					
					if(GameData.runningGame.isMovementAllowed()) {
						switch(movementValue) {
						case 1:
							//UP
							int newX = GameData.runningGame.getMoveX();
							int newY = GameData.runningGame.getMoveY()-PIXEL_MOVEMENT_PER_TICK;
							if(checkMoveFactorsForFreeField(newX, newY) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								MinaClient.sendMessageToServer(400, newX+":"+newY);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX, newY);
							}
							break;
						case -1:
							//DOWN
							int newX1 = GameData.runningGame.getMoveX();
							int newY1 = GameData.runningGame.getMoveY()+PIXEL_MOVEMENT_PER_TICK;
							if(checkMoveFactorsForFreeField(newX1, newY1) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								MinaClient.sendMessageToServer(400, newX1+":"+newY1);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX1, newY1);
							}
							break;
						case 2:
							//LEFT
							int newX2 = GameData.runningGame.getMoveX()-PIXEL_MOVEMENT_PER_TICK;
							int newY2 = GameData.runningGame.getMoveY();
							if(checkMoveFactorsForFreeField(newX2, newY2) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								MinaClient.sendMessageToServer(400, newX2+":"+newY2);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX2, newY2);
							}
							break;
						case -2:
							//RIGHT
							int newX3 = GameData.runningGame.getMoveX()+PIXEL_MOVEMENT_PER_TICK;
							int newY3 = GameData.runningGame.getMoveY();
							if(checkMoveFactorsForFreeField(newX3, newY3) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								MinaClient.sendMessageToServer(400, newX3+":"+newY3);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX3, newY3);
							}
							break;
						}
					}
					
				}
			}, 0, 10);
			
		}
		
	}
	
	/**
	 * Checkt ob ein Feld mit den gegebenen movefacktoren betreten werden kann, also kein Spieler oder anderes Objekt dort ist
	 * @param moveX - int - Der X-Movefaktor
	 * @param moveY - int - Der Y-Movefaktor
	 * @return true wenn das feld frei ist, also die moveFaktoren gültig/erlaubt sind, false wenn nicht
	 */
	private static boolean checkMoveFactorsForFreeField(int moveX, int moveY) {
		
		int fieldX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(moveX, true), true);
		int fieldY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(moveY, false), false);
		
		if(GameData.runningGame == null) {
			return false;
		}else {
			Field field = GameData.runningGame.getMap()[fieldX][fieldY];
			if(field.getType() == FieldType.DEFAULT) {
				for(Player player : GameData.runningGame.getPlayers()) {
					int playerX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getX(), true), true);
					int playerY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getY(), false), false);
					if(playerX == fieldX && playerY == fieldY) {
						return false;
					}
				}
				return true;
			}else {
				return false;
			}
		}
		
	}
	
}

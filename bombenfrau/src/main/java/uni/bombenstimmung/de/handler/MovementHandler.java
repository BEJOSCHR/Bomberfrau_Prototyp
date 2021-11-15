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

	public static final int PIXEL_MOVEMENT_PER_TICK = 6;
	
	public static boolean wantsToPlaceBomb = false;
	public static int movementValue = 0;
	private static Timer movementTimer = null;
	
	public static boolean awaitingMoveUpdate = false;
	
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
					
					if(GameData.runningGame.isMovementAllowed() && GameData.runningGame.isGameFinished() == false && GameData.runningGame.isExploded() == false && awaitingMoveUpdate == false) {
						
						//BOMB
						if(GameData.runningGame.isGameStarted()) {
							int fieldX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(GameData.runningGame.getMoveX(), true), true);
							int fieldY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(GameData.runningGame.getMoveY(), false), false);
							if(wantsToPlaceBomb == true) {
//								if(GameData.runningGame.getPlacedBombs() < 2) {
									if(ConnectionData.connectionType == ConnectionType.CLIENT) {
										MinaClient.sendMessageToServer(500, ConnectionData.clientID+":"+fieldX+":"+fieldY);
									}else {
										GameData.runningGame.registerBomb(0, fieldX, fieldY);
									}
//								}
							}
						}
						
						//MOVEMENT
						switch(movementValue) {
						case 1:
							//UP
							int newX = GameData.runningGame.getMoveX();
							int newY = GameData.runningGame.getMoveY()-PIXEL_MOVEMENT_PER_TICK;
							if(checkAllDirections(newX, newY) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								awaitingMoveUpdate = true;
								MinaClient.sendMessageToServer(400, newX+":"+newY);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX, newY);
							}
							break;
						case -1:
							//DOWN
							int newX1 = GameData.runningGame.getMoveX();
							int newY1 = GameData.runningGame.getMoveY()+PIXEL_MOVEMENT_PER_TICK;
							if(checkAllDirections(newX1, newY1) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								awaitingMoveUpdate = true;
								MinaClient.sendMessageToServer(400, newX1+":"+newY1);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX1, newY1);
							}
							break;
						case 2:
							//LEFT
							int newX2 = GameData.runningGame.getMoveX()-PIXEL_MOVEMENT_PER_TICK;
							int newY2 = GameData.runningGame.getMoveY();
							if(checkAllDirections(newX2, newY2) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								awaitingMoveUpdate = true;
								MinaClient.sendMessageToServer(400, newX2+":"+newY2);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX2, newY2);
							}
							break;
						case -2:
							//RIGHT
							int newX3 = GameData.runningGame.getMoveX()+PIXEL_MOVEMENT_PER_TICK;
							int newY3 = GameData.runningGame.getMoveY();
							if(checkAllDirections(newX3, newY3) == false) { return; }
							if(ConnectionData.connectionType == ConnectionType.CLIENT) {
								awaitingMoveUpdate = true;
								MinaClient.sendMessageToServer(400, newX3+":"+newY3);
							}else {
								GameData.runningGame.updatePlayerPos(0, newX3, newY3);
							}
							break;
						}
						
					}
					
				}
			}, 0, 40);
			
		}
		
	}
	
	/**
	 * Checkt mit einem kleinen offset alle richtungen der eigentlichen moveFaktoren ob dort ein Hinderniss ist
	 * @param moveX - int - Der X-Movefaktor
	 * @param moveY - int - Der Y-Movefaktor
	 * @return true wenn die Umgebung frei ist, also die moveFaktoren gültig/erlaubt sind, false wenn nicht
	 */
	private static boolean checkAllDirections(int moveX, int moveY) {
		
		if(checkMoveFactorsForFreeField(moveX+GameData.GAME_MOVE_CHECK_DISTANCE, moveY) == false) { return false; }
		if(checkMoveFactorsForFreeField(moveX-GameData.GAME_MOVE_CHECK_DISTANCE, moveY) == false) { return false; }
		if(checkMoveFactorsForFreeField(moveX, moveY+GameData.GAME_MOVE_CHECK_DISTANCE) == false) { return false; }
		if(checkMoveFactorsForFreeField(moveX, moveY-GameData.GAME_MOVE_CHECK_DISTANCE) == false) { return false; }
		return true;
		
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
		
		if(GameData.runningGame == null || GameData.runningGame.getMap() == null || GameData.runningGame.getMap().length == 0) {
			return false;
		}else {
			//BEGEHBARES FELD
			Field field = GameData.runningGame.getMap()[fieldX][fieldY];
			if(field.getType() == FieldType.DEFAULT) {
				//KEIN SPIELER
				for(Player player : GameData.runningGame.getPlayers()) {
					int playerX = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getX(), true), true);
					int playerY = GraphicsHandler.getCoordianteByPixel(GraphicsHandler.getPlayerCoordianteByMoveFactor(player.getY(), false), false);
					if(playerX == fieldX && playerY == fieldY) {
						return false;
					}
				}
				//KEINE BOMBE
//				for(Bomb bomb : GameData.runningGame.getBombs()) {
//					if(bomb.getX() == fieldX && bomb.getY() == fieldY) {
//						return false;
//					}
//				}
				return true;
			}else {
				return false;
			}
		}
		
	}
	
}

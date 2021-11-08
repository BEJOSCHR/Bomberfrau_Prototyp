package uni.bombenstimmung.de.handler;

import java.util.Timer;
import java.util.TimerTask;

import uni.bombenstimmung.de.game.GameData;

public class MovementHandler {

	public static final int PIXEL_MOVEMENT_PER_TICK = 4;
	
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
					
//					if(GameData.runningGame.isMovementAllowed()) {
						switch(movementValue) {
						case 1:
							//UP
							GameData.runningGame.addMoveY(PIXEL_MOVEMENT_PER_TICK);
							break;
						case -1:
							//DOWN
							GameData.runningGame.addMoveY(-PIXEL_MOVEMENT_PER_TICK);
							break;
						case 2:
							//LEFT
							GameData.runningGame.addMoveX(PIXEL_MOVEMENT_PER_TICK);
							break;
						case -2:
							//RIGHT
							GameData.runningGame.addMoveX(-PIXEL_MOVEMENT_PER_TICK);
							break;
						}
//					}
					
				}
			}, 0, 10);
			
		}
		
	}
	
}

/*
 * GameData
 *
 * Version 1.0
 * Author: Benni
 *
 * Enthält alle Spiel relevanten Daten
 */

package uni.bombenstimmung.de.game;

public class GameData {

	public static final int MAP_DIMENSION = 21; //SOLLTE UNGERADE SEIN
	public static final int MAP_SIDE_BORDER = 20;
	
	public static final int COUNTDOWN_DURATION = 5;
	
	public static int PLAYER_DIMENSION = 30;
	public static int BOMB_DIMENSION = 20;
	public static int FIELD_DIMENSION = 50;
	
	public static boolean isRunning = false;
	public static Game runningGame = null;
	
}

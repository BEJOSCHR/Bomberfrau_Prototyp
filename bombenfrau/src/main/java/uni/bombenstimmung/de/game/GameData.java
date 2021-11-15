/*
 * GameData
 *
 * Version 1.0
 * Author: Benni
 *
 * Enth�lt alle Spiel relevanten Daten
 */

package uni.bombenstimmung.de.game;

public class GameData {

	public static final int MAP_DIMENSION = 21; //SOLLTE UNGERADE SEIN
	public static final int MAP_SIDE_BORDER = 20;
	
	public static final int GAME_MOVE_CHECK_DISTANCE = 10;
	
	public static final int COUNTDOWN_DURATION = 5;
	
	public static int PLAYER_DIMENSION = 30;
	public static int BOMB_DIMENSION = 20;
	public static int FIELD_DIMENSION = 50;
	
	public static boolean isRunning = false;
	public static Game runningGame = null;
	
	public static final String MAP_1 = "0,0,BO:0,1,BO:0,2,BO:0,3,BO:0,4,BO:0,5,BO:0,6,BO:0,7,BO:0,8,BO:0,9,BO:0,10,BO:0,11,BO:0,12,BO:0,13,BO:0,14,BO:0,15,BO:0,16,BO:0,17,BO:0,18,BO:0,19,BO:0,20,BO:1,0,BO:1,1,DE:1,2,DE:1,3,DE:1,4,DE:1,5,DE:1,6,DE:1,7,DE:1,8,DE:1,9,DE:1,10,DE:1,11,DE:1,12,DE:1,13,DE:1,14,DE:1,15,DE:1,16,DE:1,17,DE:1,18,DE:1,19,DE:1,20,BO:2,0,BO:2,1,WA:2,2,BL:2,3,WA:2,4,BL:2,5,WA:2,6,BL:2,7,WA:2,8,BL:2,9,WA:2,10,BL:2,11,WA:2,12,BL:2,13,WA:2,14,BL:2,15,WA:2,16,BL:2,17,WA:2,18,BL:2,19,WA:2,20,BO:3,0,BO:3,1,DE:3,2,DE:3,3,DE:3,4,DE:3,5,DE:3,6,DE:3,7,DE:3,8,DE:3,9,DE:3,10,DE:3,11,DE:3,12,DE:3,13,DE:3,14,DE:3,15,DE:3,16,DE:3,17,DE:3,18,DE:3,19,DE:3,20,BO:4,0,BO:4,1,WA:4,2,BL:4,3,WA:4,4,BL:4,5,WA:4,6,BL:4,7,WA:4,8,BL:4,9,WA:4,10,BL:4,11,WA:4,12,BL:4,13,WA:4,14,BL:4,15,WA:4,16,BL:4,17,WA:4,18,BL:4,19,WA:4,20,BO:5,0,BO:5,1,DE:5,2,DE:5,3,DE:5,4,DE:5,5,DE:5,6,DE:5,7,DE:5,8,DE:5,9,DE:5,10,DE:5,11,DE:5,12,DE:5,13,DE:5,14,DE:5,15,DE:5,16,DE:5,17,DE:5,18,DE:5,19,DE:5,20,BO:6,0,BO:6,1,WA:6,2,BL:6,3,WA:6,4,BL:6,5,WA:6,6,BL:6,7,WA:6,8,BL:6,9,WA:6,10,BL:6,11,WA:6,12,BL:6,13,WA:6,14,BL:6,15,WA:6,16,BL:6,17,WA:6,18,BL:6,19,WA:6,20,BO:7,0,BO:7,1,DE:7,2,DE:7,3,DE:7,4,DE:7,5,DE:7,6,DE:7,7,DE:7,8,DE:7,9,DE:7,10,DE:7,11,DE:7,12,DE:7,13,DE:7,14,DE:7,15,DE:7,16,DE:7,17,DE:7,18,DE:7,19,DE:7,20,BO:8,0,BO:8,1,WA:8,2,BL:8,3,WA:8,4,BL:8,5,WA:8,6,BL:8,7,WA:8,8,BL:8,9,WA:8,10,BL:8,11,WA:8,12,BL:8,13,WA:8,14,BL:8,15,WA:8,16,BL:8,17,WA:8,18,BL:8,19,WA:8,20,BO:9,0,BO:9,1,DE:9,2,DE:9,3,DE:9,4,DE:9,5,DE:9,6,DE:9,7,DE:9,8,DE:9,9,DE:9,10,DE:9,11,DE:9,12,DE:9,13,DE:9,14,DE:9,15,DE:9,16,DE:9,17,DE:9,18,DE:9,19,DE:9,20,BO:10,0,BO:10,1,WA:10,2,BL:10,3,WA:10,4,BL:10,5,WA:10,6,BL:10,7,WA:10,8,BL:10,9,WA:10,10,BL:10,11,WA:10,12,BL:10,13,WA:10,14,BL:10,15,WA:10,16,BL:10,17,WA:10,18,BL:10,19,WA:10,20,BO:11,0,BO:11,1,DE:11,2,DE:11,3,DE:11,4,DE:11,5,DE:11,6,DE:11,7,DE:11,8,DE:11,9,DE:11,10,DE:11,11,DE:11,12,DE:11,13,DE:11,14,DE:11,15,DE:11,16,DE:11,17,DE:11,18,DE:11,19,DE:11,20,BO:12,0,BO:12,1,WA:12,2,BL:12,3,WA:12,4,BL:12,5,WA:12,6,BL:12,7,WA:12,8,BL:12,9,WA:12,10,BL:12,11,WA:12,12,BL:12,13,WA:12,14,BL:12,15,WA:12,16,BL:12,17,WA:12,18,BL:12,19,WA:12,20,BO:13,0,BO:13,1,DE:13,2,DE:13,3,DE:13,4,DE:13,5,DE:13,6,DE:13,7,DE:13,8,DE:13,9,DE:13,10,DE:13,11,DE:13,12,DE:13,13,DE:13,14,DE:13,15,DE:13,16,DE:13,17,DE:13,18,DE:13,19,DE:13,20,BO:14,0,BO:14,1,WA:14,2,BL:14,3,WA:14,4,BL:14,5,WA:14,6,BL:14,7,WA:14,8,BL:14,9,WA:14,10,BL:14,11,WA:14,12,BL:14,13,WA:14,14,BL:14,15,WA:14,16,BL:14,17,WA:14,18,BL:14,19,WA:14,20,BO:15,0,BO:15,1,DE:15,2,DE:15,3,DE:15,4,DE:15,5,DE:15,6,DE:15,7,DE:15,8,DE:15,9,DE:15,10,DE:15,11,DE:15,12,DE:15,13,DE:15,14,DE:15,15,DE:15,16,DE:15,17,DE:15,18,DE:15,19,DE:15,20,BO:16,0,BO:16,1,WA:16,2,BL:16,3,WA:16,4,BL:16,5,WA:16,6,BL:16,7,WA:16,8,BL:16,9,WA:16,10,BL:16,11,WA:16,12,BL:16,13,WA:16,14,BL:16,15,WA:16,16,BL:16,17,WA:16,18,BL:16,19,WA:16,20,BO:17,0,BO:17,1,DE:17,2,DE:17,3,DE:17,4,DE:17,5,DE:17,6,DE:17,7,DE:17,8,DE:17,9,DE:17,10,DE:17,11,DE:17,12,DE:17,13,DE:17,14,DE:17,15,DE:17,16,DE:17,17,DE:17,18,DE:17,19,DE:17,20,BO:18,0,BO:18,1,WA:18,2,BL:18,3,WA:18,4,BL:18,5,WA:18,6,BL:18,7,WA:18,8,BL:18,9,WA:18,10,BL:18,11,WA:18,12,BL:18,13,WA:18,14,BL:18,15,WA:18,16,BL:18,17,WA:18,18,BL:18,19,WA:18,20,BO:19,0,BO:19,1,DE:19,2,DE:19,3,DE:19,4,DE:19,5,DE:19,6,DE:19,7,DE:19,8,DE:19,9,DE:19,10,DE:19,11,DE:19,12,DE:19,13,DE:19,14,DE:19,15,DE:19,16,DE:19,17,DE:19,18,DE:19,19,DE:19,20,BO:20,0,BO:20,1,BO:20,2,BO:20,3,BO:20,4,BO:20,5,BO:20,6,BO:20,7,BO:20,8,BO:20,9,BO:20,10,BO:20,11,BO:20,12,BO:20,13,BO:20,14,BO:20,15,BO:20,16,BO:20,17,BO:20,18,BO:20,19,BO:20,20,BO";
	
	public static final int BOMB_LIVETIME = 3;
	public static final int BOMB_EXPLODE_RADIUS = 3;
	
}

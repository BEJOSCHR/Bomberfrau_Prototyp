/*
 * MouseActionAreaHandler
 *
 * Version 1.0
 * Author: Benni
 *
 * Dient als statischer Verwalter der MAA Objekte
 */

package uni.bombenstimmung.de.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

import uni.bombenstimmung.de.game.Game;
import uni.bombenstimmung.de.game.GameData;
import uni.bombenstimmung.de.graphics.DisplayType;
import uni.bombenstimmung.de.graphics.GraphicsData;
import uni.bombenstimmung.de.objects.MouseActionArea;
import uni.bombenstimmung.de.serverconnection.ConnectionData;
import uni.bombenstimmung.de.serverconnection.ConnectionType;
import uni.bombenstimmung.de.serverconnection.client.MinaClient;
import uni.bombenstimmung.de.serverconnection.server.MinaServer;

public class MouseActionAreaHandler {

	public static List<MouseActionArea> mouseActionAreas = new ArrayList<MouseActionArea>();
	
	/**
	 * Findet die passende MAA zum idName wenn diese in der Liste existiert
	 * @param idName - String - Der Name nach dem gesucht wird
	 * @return Die MAA wenn es eine mit dem idName gibt, sonst null
	 */
	public static MouseActionArea getMouseActionArea(String idName) {
		
		for(MouseActionArea maa : mouseActionAreas) {
			if(maa.getIdName().equals(idName)) {
				return maa;
			}
		}
		
		return null;
	}
	
	/**
	 * Initialisiert alle MMAs mit ihren Custom Methoden (@Overwrite)
	 */
	public static void initMAAs() {
		
		//MENU
		
		int menu_downBorder = 400, menu_borderBetweenMAAs = 90;
		int menu_width = 200, menu_height = 40;
		
		new MouseActionArea(GraphicsData.width/2-menu_width/2, GraphicsData.height-menu_downBorder-(menu_borderBetweenMAAs*2)-menu_height/2, menu_width, menu_height
				, "id_JoinGame", "Join", 24, Color.WHITE, Color.ORANGE) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				try {
					MinaClient.initClientConnection();
				} catch (IOException | InterruptedException e) {}
			}
			
			@Override
			public boolean isActiv() {
				return GraphicsData.drawState == DisplayType.MENU;
			}
			
		};
		new MouseActionArea(GraphicsData.width/2-menu_width/2, GraphicsData.height-menu_downBorder-(menu_borderBetweenMAAs*1)-menu_height/2, menu_width, menu_height
				, "id_CreateGame", "Create", 24, Color.WHITE, Color.ORANGE) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				try {
					MinaServer.initServerConnection();
				} catch (IOException e) {}
				GameData.runningGame = new Game(true);
				GraphicsData.drawState = DisplayType.INGAME;
			}
			
			@Override
			public boolean isActiv() {
				return GraphicsData.drawState == DisplayType.MENU;
			}
			
		};
		new MouseActionArea(GraphicsData.width/2-menu_width/2, GraphicsData.height-menu_downBorder-(menu_borderBetweenMAAs*0)-menu_height/2, menu_width, menu_height
				, "id_Exit", "Exit", 24, Color.WHITE, Color.ORANGE) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				System.exit(0);
			}
			
			@Override
			public boolean isActiv() {
				return GraphicsData.drawState == DisplayType.MENU;
			}
			
		};
		
		//LOBBY
		
		
		
		
		//INGAME
		new MouseActionArea(GraphicsData.width/2-menu_width/2, GraphicsData.height-30-menu_height/2, menu_width, menu_height
				, "id_StartGame", "Start", 24, Color.WHITE, Color.GREEN) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				GameData.runningGame.startGame();
			}
			
			@Override
			public boolean isActiv() {
				return GraphicsData.drawState == DisplayType.INGAME && GameData.runningGame.isThisClientHost() && GameData.runningGame.isGameStarted() == false  && GameData.runningGame.isMovementAllowed();
			}
			
		};
		new MouseActionArea(GraphicsData.width-20-menu_width/2, GraphicsData.height-30-menu_height/2, menu_width/2, menu_height
				, "id_LeaveGame", "Leave", 24, Color.WHITE, Color.RED) {
			
			@Override
			public void performAction_LEFT_RELEASE() {
				if(ConnectionData.connectionType == ConnectionType.CLIENT) {
					MinaClient.disconnectFromServer();
				}else {
					MinaServer.shutDownServerConnection();
				}
				GraphicsData.drawState = DisplayType.MENU;
				GameData.isRunning = false;
				GameData.runningGame = null;
			}
			
			@Override
			public boolean isActiv() {
				return GraphicsData.drawState == DisplayType.INGAME && GameData.runningGame.isGameStarted() == false  && GameData.runningGame.isMovementAllowed();
			}
			
		};
		
		
		//AFTERGAME
		
		
		
		
		//OPTIONS
		
		
		
		
	}
	
}

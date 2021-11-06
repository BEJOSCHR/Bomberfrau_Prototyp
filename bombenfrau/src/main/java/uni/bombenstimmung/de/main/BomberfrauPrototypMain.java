/*
 * BomberfrauPrototypMain
 *
 * Version 1.0
 * Author: Benni
 *
 * Der Beginn des Prototyps
 */

package uni.bombenstimmung.de.main;

import java.io.IOException;
import java.util.Scanner;

import uni.bombenstimmung.de.graphics.GraphicsHandler;
import uni.bombenstimmung.de.handler.MouseActionAreaHandler;
import uni.bombenstimmung.de.serverconnection.client.MinaClient;
import uni.bombenstimmung.de.serverconnection.server.MinaServer;

public class BomberfrauPrototypMain {
	
	public static final String AUTHOR = "Bombenstimmung - Uni Wuppertal - ©2021";
	public static final String VERSION = "Prototyp - Alpha - V0.0.1";
	
	/**
	 * Der Start von allem (in diesem Projekt)
	 * @param args Die Argumente die dem Start mitgegeben werden können
	 */
	public static void main(String[] args) {
		
		ConsoleDebugger.printMessage("Starting Bomberfrau PROTOTYP!");
		
		//START GRAPHICS (Muss als erstes sein da Frame width und height gesetzt werden)
		GraphicsHandler.initGraphics();
		
		//MAA INIT
		MouseActionAreaHandler.initMAAs();
		
		//USAGE: 2 Mal das Programm starten und dann ersten mit s und zweiten mit c starten... dann connected client zum server
		ConsoleDebugger.printMessage("Server or Client? (s|c)");
		Scanner userInputScanner = new Scanner(System.in);
		String userInput = userInputScanner.nextLine();
		userInputScanner.close();
		if(userInput.equals("s")) {
			//Server
			try {
				MinaServer.initServerConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(userInput.equals("c")) {
			//Client
			try {
				MinaClient.initClientConnection();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			ConsoleDebugger.printMessage("Invalid answer! Please restart...");
		}
		
	}
	
}


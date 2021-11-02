/*
 * BomberfrauPrototypMain
 *
 * Version 1.0
 * Author: Benni
 *
 * Der Beginn des Prototyps
 */

package uni.bombenstimmung.de.main;

import java.util.Scanner;

import uni.bombenstimmung.de.serverconnection.ServerConnection;

public class BomberfrauPrototypMain {

	/**
	 * Der Start von allem (in diesem Projekt)
	 * @param args Die Argumente die dem Start mitgegeben werden können
	 */
	public static void main(String[] args) {
		
		ConsoleDebugger.printMessage("Starting Bomberfrau PROTOTYP!");
		
		ConsoleDebugger.printMessage("Server or Client? (s|c)");
		Scanner userInputScanner = new Scanner(System.in);
		String userInput = userInputScanner.nextLine();
		userInputScanner.close();
		if(userInput.equals("s")) {
			//Server
			ServerConnection.openServerConnection();
		}else if(userInput.equals("c")) {
			//Client
			//TODO
		}else {
			ConsoleDebugger.printMessage("Invalid answer! Please restart...");
		}
		
	}
	
}


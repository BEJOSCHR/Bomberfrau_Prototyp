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

import uni.bombenstimmung.de.serverconnection.ClientConnection;
import uni.bombenstimmung.de.serverconnection.ServerConnection;

public class BomberfrauPrototypMain {

	/**
	 * Der Start von allem (in diesem Projekt)
	 * @param args Die Argumente die dem Start mitgegeben werden können
	 */
	public static void main(String[] args) {
		
		ConsoleDebugger.printMessage("Starting Bomberfrau PROTOTYP!");
		
		//USAGE: 2 Mal das Programm starten und dann ersten mit s und zweiten mit c starten... dann connected client zum server
		ConsoleDebugger.printMessage("Server or Client or Autoclient? (s|c|a)");
		Scanner userInputScanner = new Scanner(System.in);
		String userInput = userInputScanner.nextLine();
		userInputScanner.close();
		if(userInput.equals("s")) {
			//Server
			ServerConnection.openServerConnection();
		}else if(userInput.equals("c")) {
			//Client
			ClientConnection.connectToServer();
		}else if(userInput.equals("a")) {
			//Auto Client
			ClientConnection.autoConnectToServer();
		}else {
			ConsoleDebugger.printMessage("Invalid answer! Please restart...");
		}
		
	}
	
}


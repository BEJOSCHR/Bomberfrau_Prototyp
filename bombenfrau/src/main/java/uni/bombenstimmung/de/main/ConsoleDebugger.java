/*
 * ConsoleDebugger
 *
 * Version 1.0
 * Author: Benni
 *
 * Die zentrale Schnittstelle um mit der Konsole zu interagieren
 */

package uni.bombenstimmung.de.main;

public class ConsoleDebugger {

	/**
	 * Gibt den angegebenen Text aus - Gibt immer eine volle Zeile aus
	 * @param text Die auszugebene Nachricht
	 */
	public static void printMessage(String text) {

		printMessage(text, true);
		
	}
	/**
	 * Gibt den angegebenen Text aus - Kann bestimmt werden ob Vollezeile oder nicht
	 * @param text Die auszugebene Nachricht
	 * @param fullLine Wenn false, dann wird die Zeile nicht abgeschlossen (kein println)
	 */
	public static void printMessage(String text, boolean fullLine) {

		if(fullLine == true) {
			System.out.println(""+text);
		}else {
			System.out.print(""+text);
		}
		
	}
	
}

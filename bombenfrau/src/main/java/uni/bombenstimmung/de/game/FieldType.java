package uni.bombenstimmung.de.game;

import java.awt.Color;

import uni.bombenstimmung.de.main.ConsoleDebugger;

public enum FieldType {

	DEFAULT,
	WALL, //ZERSTÖRBAR
	BLOCK, //NICHT ZERSTÖRBAR
	BORDER;
	
	/**
	 * Konvertiert einen fieldType zu seiner abkürzenden Darstellung
	 * @param type - {@link FieldType} - Der abzukürzende FieldType
	 * @return Die Representation für den FieldType als String (Standardmäßig ##)
	 */
	public static String getFieldTypeRepresentation(FieldType type) {
		
		switch(type) {
		case BLOCK:
			return "BL";
		case BORDER:
			return "BO";
		case DEFAULT:
			return "DE";
		case WALL:
			return "WA";
		default:
			ConsoleDebugger.printMessage("Unknown fieldtype '"+type+"'!");
			return "##";
		}
		
	}
	
	/**
	 * Konvertiert eine FieldType Abkürzung/Representation zurück zu einem FieldType
	 * @param representation - String - Die zurückzuwandelnde Representation
	 * @return Der representierte FieldType (Standardmäßig DEFAULT)
	 */
	public static FieldType getFieldTypeFromRepresentation(String representation) {
		
		switch(representation) {
		case "BL":
			return BLOCK;
		case "BO":
			return BORDER;
		case "DE":
			return DEFAULT;
		case "WA":
			return WALL;
		default:
			ConsoleDebugger.printMessage("Unknown fieldtype representation '"+representation+"'!");
			return DEFAULT;
		}
		
	}
	
	public static Color getColorForFieldType(FieldType type) {
		
		switch(type) {
		case BLOCK:
			return Color.DARK_GRAY;
		case BORDER:
			return Color.RED;
		case DEFAULT:
			return Color.GREEN.darker();
		case WALL:
			return Color.LIGHT_GRAY;
		default:
			ConsoleDebugger.printMessage("Unknown fieldtype '"+type+"'!");
			return Color.PINK;
		}
		
	}
	
}

package uni.bombenstimmung.de.game;

import java.awt.Color;

import uni.bombenstimmung.de.main.ConsoleDebugger;

public enum FieldType {

	DEFAULT,
	WALL, //ZERST�RBAR
	BLOCK, //NICHT ZERST�RBAR
	BORDER;
	
	/**
	 * Konvertiert einen fieldType zu seiner abk�rzenden Darstellung
	 * @param type - {@link FieldType} - Der abzuk�rzende FieldType
	 * @return Die Representation f�r den FieldType als String (Standardm��ig ##)
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
	 * Konvertiert eine FieldType Abk�rzung/Representation zur�ck zu einem FieldType
	 * @param representation - String - Die zur�ckzuwandelnde Representation
	 * @return Der representierte FieldType (Standardm��ig DEFAULT)
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

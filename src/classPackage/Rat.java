package classPackage;

import interfaces.RatInterface;

/**
 * Randomly gives a direction to be sent.
 * @author Peter Fortin
 */

public class Rat implements RatInterface {

	/*Private Data Fields*/
	
	private int numberOfRats;
	private final String RatID;
	private int aliveStatus;
	private int exhaustionLevel;
	
	/*Constructors*/
	
	/**
	 * Rat constructor.
	 * @param int n number of Rats which came before.
	 */
	public Rat(int n) {
		
		this.numberOfRats = n;
		this.RatID = genID(this.numberOfRats);
		this.aliveStatus = 0;
		this.exhaustionLevel = 6;
	}
		
	/*Private helpers*/
	
	/** 
	 * Randomly generates an abominable name for the rat and postfixes it's number.
	 * @param int n number of Rats so far.
	 * @return String RatID name.
	 */
	private static String genID(int n) {
		
		String name;
		int choice = (int)(Math.random() * 10);
		//******I'm reusing this from my Name class project last module*******
		switch (choice) {
		case 0:
			name = "Kreethlawn";
			break;
		case 1:
			name = "Belthgar";
			break;
		case 2:
			name = "Xanthax";
			break;
		case 3:
			name = "Treshk";
			break;
		case 4:
			name = "Melbiah";
			break;
		case 5:
			name = "Toogoo";
			break;
		case 6:
			name = "Quhalthoth";
			break;
		case 7:
			name = "Zxyzxyzxyx";
			break;
		case 8:
			name = "*Unspeakable murmurs*";
			break;
		case 9:
			name = "Timothy";
			break;
		default: 
			name = "Something terrible has happened, no rat deserves a name like this!";
			break;
		}
		if (n == 11 || n % 100 == 11 || n == 12 || n % 100 == 12) {
			name = name + " the " + n + "th";
		} else if (n % 10 == 1) {
			name = name + " the " + n + "st";
		} else if (n % 10 == 2){
			name = name + " the " + n + "nd";
		} else if (n % 10 == 3){
			name = name + " the " + n + "rd";
		} else {
			name = name + " the " + n + "th";
		}
		return name;
	}
			
	/*RatInterface implementation*/
	
	/**
	 * Generates the direction of movement for the Rat.
	 * @return String movement direction.
	 */
	public String move() {
		
		int choice = (int)(Math.random() * 8);
		switch (choice) {
		case 0:
			return "N";
		case 1:
			return "NE";
		case 2:
			return "E";
		case 3:
			return "SE";
		case 4:
			return "S";
		case 5:
			return "SW";
		case 6:
			return "W";
		case 7: 
			return "NW";
		default:
			return "SE";
		}
		
	}
	
	/**
	 * Alive status getter. 
	 * @return int 0 Rat is alive, 1 Rat is dead.
	 */
	public int getAliveState() {
		
		return this.aliveStatus;
	}
	
	/**
	 * Refreshes the Rat's exhaustion level by 1. A Rat cannot have more than 6.
	 */
	public void refresh() {
		
		if (this.exhaustionLevel >= 6) {
			this.exhaustionLevel = 6;
		} else if (this.exhaustionLevel > 0) {
			this.exhaustionLevel += 1;
		} else {
			this.exhaustionLevel = 0;
		}
		if (this.exhaustionLevel == 0) this.aliveStatus = 1;
	}
	
	/**
	 * Wears down the Rat's exhaustion level by 1. If exhaustion hits 0, the Rat dies.
	 */
	public void wearDown() {
		
		if (this.exhaustionLevel >= 6) {
			this.exhaustionLevel = 5;
		} else if (this.exhaustionLevel > 0) {
			this.exhaustionLevel -= 1;
		} else {
			this.exhaustionLevel = 0;
		}
		if (this.exhaustionLevel == 0) this.aliveStatus = 1;
	}
	
	/**
	 * Getter for Rat's ID.
	 * @return String Rat's ID.
	 */
	public String getId() {
		
		return this.RatID;
	}
	
} 

package classPackage;

import java.util.ArrayList;

import interfaces.HoleInterface;
import interfaces.RatInterface;

/**
 * Randomly teleports, instantly kills, or lets a Rat escape when entered.
 * @author Peter Fortin
 */

public class Hole implements HoleInterface {

	
	/*Private Data Fields*/
	
	private final int[] COORDINATES;
	private final char CELLTYPE;
	private RatInterface heldRat;
	private ArrayList<RatInterface> deadRats;
	
	/*Constructors*/
	
	/**
	 * Hole constructor.
	 * @param int array of Hole's coordinates.
	 */
	public Hole(int[] coordinates) {
		
		this.COORDINATES = coordinates;
		this.CELLTYPE = 'H';
		this.deadRats = new ArrayList<RatInterface>();
	}
	
	/*Private helpers*/
			
	/*CellInterface implementation*/
	
	/**
	 * Getter for Hole's Waste or Shelter type.
	 * @return char Hole's type.
	 */
	public char getCellType() {
		
		return this.CELLTYPE;
	}

	
	/**
	 * Gives the Rat currently being helpd to the Desert.
	 * @return RatInterface of currently held Rat.
	 */
	public RatInterface retrieveRat() {
		
		RatInterface tmpRat = this.heldRat;
		this.heldRat = null;
		return tmpRat;
	}

	/**
	 * Add Rat who has died to the list of dead Rats.
	 * @param RatInterface of the Rat who died.
	 */
	public void storeTheDead(RatInterface pRat) {
		
		this.deadRats.add(pRat);
		this.heldRat = null;
	}

	/**
	 * Return the list of dead rats to the Desert.
	 * @return ArrayList for RatInterfaces of dead rats.
	 */
	public ArrayList<RatInterface> returnTheDead() {
		
		return this.deadRats;
	}

	
	/*HoleInterface implementation*/
	/**
	 * Stores a RatInterface and gives caller the Hole's coordinates.
	 * @param RatInterface of Rat to be stored.
	 * @return Array of Hole's coordinates on grid.
	 */
	public int[] receiveRat(RatInterface pRat) {
		
		this.heldRat = pRat;
		return this.COORDINATES;
	}

	/**
	 * Count number of dead Rats being held.
	 * @return int number of dead Rats.
	 */
	public int countLostSouls() {
		
		return this.deadRats.size();
	}

}

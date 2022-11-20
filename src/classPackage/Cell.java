package classPackage;

import java.util.ArrayList;
import java.util.Objects;

import interfaces.CellInterface;
import interfaces.RatInterface;

/**
 * Wears down or refreshes a Rat when entered.
 * @author Peter Fortin
 */
public class Cell implements CellInterface {

	/*Private Data Fields*/
	
	private final int[] COORDINATES;
	private final char CELLTYPE;
	private RatInterface heldRat;
	private ArrayList<RatInterface> deadRats;
	
	/*Constructors*/
	/**
	 * Cell constructor.
	 * @param int array of Cell's coordinates.
	 */
	public Cell(int[] coordinates) {
		this.COORDINATES = coordinates;
		this.CELLTYPE = chooseCellType();
		this.deadRats = new ArrayList<RatInterface>();
	}
	
	/*Private helpers*/
	
	/**
	 * Randomly chooses either Waste or Shelter
	 * @return char 'W' or 'S'
	 */
	private char chooseCellType() {
		
		int choice = (int)(Math.random() * 10) % 2;
		switch(choice) {
		case 0:
			return 'W';
		case 1: 
			return 'S';
		default: 
			return 'S'; 
		}
	}
	
	/*CellInterface implementation*/
	
	/**
	 * Cell type getter
	 * @return char 'W' or 'S'
	 */
	public char getCellType() {
		
		return this.CELLTYPE;
	}

	/**
	 * Stores a RatInterface and gives caller the Cell's coordinates.
	 * @param RatInterface of Rat to be stored.
	 * @return Array of Cell's coordinates on grid.
	 */
	public int[] receiveRat(RatInterface pRat) {
		
		if (Objects.isNull(this.heldRat)) {
			this.heldRat = pRat;
			return this.COORDINATES;
		} else {
			int[] cellFull = new int[] {-1, -1};
			return cellFull;
		}
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
	
	
	
}

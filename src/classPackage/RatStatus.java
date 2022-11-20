package classPackage;

import interfaces.RatStatusInterface;

/**
 * Contains duplicated copies of the Rat's Status and ID to be passed instead of the Rat.
 * @author Peter Fortin
 */

public class RatStatus implements RatStatusInterface{
	
	/*Private Data Fields*/
	
	private final String RAT_ID;
	private int status;
	
	/*Constructors*/
	
	/**
	 * RatStatus constructor.
	 * @param String id correponding Rat's name.
	 * @param int status corresponding Rat's Alive status.
	 */
	public RatStatus(String id, int status) {
		
		this.RAT_ID = id;
		this.status = status;
	}
	
	/*RatStatusInterface implementation*/
	
	/**
	 * Getter for RatID.
	 * @return String of Rat's ID.
	 */
	public String getRatID() {
		
		return this.RAT_ID;
	}

	/**
	 * Rat state getter.
	 * @return Int Rat's state.
	 */
	public int getRatState() { 
		
		return this.status;	
	}

	/**
	 * Rat state setter.
	 * @param Int reflective of the Rat's new state.
	 */
	public void setRatState(int pRateState) {
		
		switch(pRateState) {
		case -1: 
			this.status = -1;
			break;
		case 0: 
			this.status = 0;
			break;
		case 1: 
			this.status = 1;
			break;
		default: 
			this.status = 1;
		}
	}

}


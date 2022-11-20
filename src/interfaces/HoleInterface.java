package interfaces;

/**
 * 
 * @author Larry Shannon
 *
 */

public interface HoleInterface extends CellInterface
{
	/**
	 * The receiveRat() method receives a rat,
	 * then randomly determines if the rat went down the hole
	 *   to another cell
	 *   to disappear/escape
	 *   or stayed at this location
	 * @param RatInterface holds reference to Rat object
	 * @return 
	 * Returns {row,col}
	 * new row , col of cell rat is ends up in
	 * -1,-1 if rat disappears 
	 */
	public int[] receiveRat(RatInterface pRat);
	
	/**
	 * @return
	 * count of rats that have disappeared
	 */
	public int countLostSouls();
}

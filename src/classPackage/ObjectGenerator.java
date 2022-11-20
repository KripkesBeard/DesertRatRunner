/**
 * 
 */ 
package classPackage;

import interfaces.CellInterface;
import interfaces.DesertInterface;
import interfaces.HoleInterface;
import interfaces.RatInterface;
import interfaces.RatStatusInterface;

/**
 * @author Larry Shannon
 * 
 */ 
public class ObjectGenerator
{
	public static DesertInterface createNewDesert(int rows, int columns)
	{
		return new Desert(rows, columns);
	} 
	public static CellInterface createNewCell(int[] coordinates)
	{
		return new Cell(coordinates);
	}
	public static HoleInterface createNewHoleCell(int[] coordinates)
	{
		return new Hole(coordinates);
	}
	public static RatInterface createNewRat(int n)
	{
		return new Rat(n);
	}
	public static RatStatusInterface createNewRatID(String pID, int pState)
	{
		return new RatStatus(pID, pState);
	}
}

package classPackage;

import java.util.ArrayList;
import java.util.Arrays;


import interfaces.DesertInterface;
import interfaces.RatInterface;
import interfaces.RatStatusInterface;
import interfaces.CellInterface;

/**
 * Runs Rats through itself.
 * @author Peter Fortin
 */
public class Desert implements DesertInterface {
	
	/*Private Data Fields*/
	
	private final int ROWS;
	private final int COLUMNS;
	private final int[] EXIT_COORDS;
	private final CellInterface[][] DESERT_GRID;
	private ArrayList<int[]> listOfHoles;
	private RatInterface currentRat;
	private RatStatusInterface currentRatStatus;
	private int numberOfRats;
	private int numberOfTeleports;
	private int[] CurrentRatPosition;
	private ArrayList<int[]> ratPath;
	
	/*Constructors*/
	
	/**
	 * Desert constructor.
	 * @param int rows of the grid.
	 * @param int columns of the grid.
	 */
	public Desert(int rows, int columns) {
		
		this.numberOfRats = 0;
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.EXIT_COORDS = new int[] {this.ROWS - 1, this.COLUMNS -1};
		this.listOfHoles = new ArrayList<int[]>();
		CellInterface[][] grid = new CellInterface[this.ROWS][this.COLUMNS];
		
		//Populate the grid array by looping through and creating a Cell object for each coordinate.
		for (int i = 0; i < this.ROWS; ++i) {
			for (int j = 0; j < this.COLUMNS; ++j) {
				int[] coordinates = new int[] {i, j};
				grid[i][j] = chooseCellType(coordinates);
			}
		}
		this.DESERT_GRID = grid;
		this.ratPath = new ArrayList<int[]>();
	}
	
	
	/*Private helpers*/
	
	/**
	 * Chooses which Cell type a new Cell is going to be.
	 * @param coordinates for the cell needed for the Cell constructor.
	 * @return CellInterface to be stored in the grid.
	 */
	private CellInterface chooseCellType(int[] coordinates) {
		
		//Give the Cell a 20% chance to be a Hole, otherwise a shelter or waste.
		int choice = (int)(Math.random() * 10) % 5;
		switch (choice) {
		case 0: 
			listOfHoles.add(coordinates);
			return ObjectGenerator.createNewHoleCell(coordinates);
		default:
			return ObjectGenerator.createNewCell(coordinates);
		}
	}
	
	/**
	 * Calculates the amount of change a move of the given direction would make,
	 * so that the change can be done by just adding this value componant-wise to the
	 * old coordinates.
	 * @param Directions gotten from Rat.
	 * @return Array of scalars to be added to the current position.
	 */
	private int[] coordinateChangeAmount(String directions) {
		
		int[] coords = new int[2];
		
		switch(directions) {
		case "N":
			coords[0] = -1;
			coords[1] = 0;
			break;
		case "NE":
			coords[0] = -1;
			coords[1] = 1;
			break;
		case "E":
			coords[0] = 0;
			coords[1] = 1;
			break;
		case "SE":
			coords[0] = 1;
			coords[1] = 1;
			break;
		case "S":
			coords[0] = 1;
			coords[1] = 0;
			break;
		case "SW":
			coords[0] = 1;
			coords[1] = -1;
			break;
		case "W":
			coords[0] = 0;
			coords[1] = -1;
			break;
		case "NW":
			coords[0] = -1;
			coords[1] = -1;
			break;
		default:
			coords[0] = 0;
			coords[1] = 0;
		}
		return coords;
	}
	
	
	/*DesertInterface implementation*/
	

	/**
	 * Creates a Rat and a corresponding Status, and initializes the Rat's position and path history.
	 * @return RatStatusInterface associated with newly created Rat.
	 */
	public RatStatusInterface startRat() {
		
		++this.numberOfRats;
		this.numberOfTeleports = 0;
		this.currentRat = ObjectGenerator.createNewRat(this.numberOfRats);
		this.currentRatStatus = ObjectGenerator.createNewRatID(this.currentRat.getId(), this.currentRat.getAliveState());
		this.CurrentRatPosition = new int[]{0, 0};
		this.ratPath.clear();
		this.ratPath.add(new int[]{0,0});
		return this.currentRatStatus;
	}


	/**
	 * Central function of the Desert, gets a direction from the Rat and makes sure it's in bounds.
	 * Processes the effect that the type of cell has on the Rat and then returns its new status.
	 * @param pRatID ID of the Rat to move.
	 * @return Updated RatInterface after movement is processed.
	 */
	public RatStatusInterface moveRat(String pRatID) {
		
		
		String directions = this.currentRat.move();
		int[] coordChange = coordinateChangeAmount(directions);
		int[] newCoords = new int[2];
		
		int currentX = this.CurrentRatPosition[0];
		int currentY = this.CurrentRatPosition[1];
		int deltaX = coordChange[0];
		int deltaY = coordChange[1];
		
		if (currentX + deltaX < 0 || currentX + deltaX >= this.ROWS || currentY + deltaY < 0 || currentY + deltaY >= this.ROWS) {
			//If the rat would move out of bounds, don't move it and don't update its status.
			newCoords[0] = currentX; 
			newCoords[1] = currentY;
		} else {
			//Otherwise move it.
			newCoords[0] = this.CurrentRatPosition[0] + deltaX;
			newCoords[1] = this.CurrentRatPosition[1] + deltaY;
			//update its status according to the type of Cell.
			char cellType = this.DESERT_GRID[newCoords[0]][newCoords[1]].getCellType();
			switch (cellType) {
			case 'S':
				this.currentRat.refresh();
				break;
			case 'W':
				this.currentRat.wearDown();
				break;
			case 'H':
				int holeChoice = (int)(Math.random() * 100) % 10;
				if (holeChoice == 0) {
					this.currentRatStatus.setRatState(1);//Kill rat
					return this.currentRatStatus;//Return early
				} else if (holeChoice < 4 ) {
					++this.numberOfTeleports;
					int numberOfHoles = this.listOfHoles.size();
					int holeIndex = (int)(Math.random() * numberOfHoles);
					newCoords = this.listOfHoles.get(holeIndex);//Teleport to random hole
				}
				break;
			default:
				break;//Equivalent to no movement
			}
		}
		
		//Update CurrentRatPosition and add new position to path.
		this.CurrentRatPosition = newCoords;
		this.ratPath.add(CurrentRatPosition);
		
		//Decide whether the Rat is alive, dead, or finished, and return the updated RatStatus.
		this.currentRatStatus.setRatState(this.currentRat.getAliveState());
		if (Arrays.equals(this.CurrentRatPosition, this.EXIT_COORDS) && this.currentRat.getAliveState() == 0) {
			this.currentRatStatus.setRatState(-1);
		} else {
			this.currentRatStatus.setRatState(this.currentRat.getAliveState());
		}
		return this.currentRatStatus;
	}


	/**
	 * Displays the final results of the Rat run, including the name of the Rat who finished,
	 * the path the Rat took, and how many Rats before it died
	 */
	public void displayStatistics() {
		
		System.out.println("The eldritch beast " + this.currentRat.getId() + " has made it out of the desert alive!");
		System.out.println("This was its path:");
		displayRatPath();
		System.out.println("There were " + this.numberOfTeleports + " hole teleports.");
		System.out.println(this.numberOfRats - 1 + " other abominations were damned to spend eternity in the desert's slumbering maw.");
	}


	/**
	 * Prints the Desert as a grid of letters representing the Cell types
	 */
	public void printMap() {
		
		for (int i = 0; i < this.ROWS; ++i) {
			for (int j = 0; j < this.COLUMNS; ++j) {
				if (j == this.COLUMNS - 1) {
					System.out.println(this.DESERT_GRID[i][j].getCellType());
				} else {
					System.out.print(this.DESERT_GRID[i][j].getCellType() + " ");
				}
			}
		}
	}
	
	/**
	 * Displays the ordered pair of the cells the Rat visited on its trip. If there's a sudden jump in the numbers, that means
	 * there was a teleport
	 */
	public void displayRatPath() {
		int n = 0;
		
		for (int i = 0; i < this.ratPath.size(); ++i) {
			if (n > 9) {
				n = 0;
				System.out.println("(" + this.ratPath.get(i)[0] + "," + this.ratPath.get(i)[1] + ")");
			} else {
				++n;
				System.out.print("(" + this.ratPath.get(i)[0] + "," + this.ratPath.get(i)[1] + ") ");
			}
		}
		System.out.println();
	}
	
}

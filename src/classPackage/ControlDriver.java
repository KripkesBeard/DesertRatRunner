package classPackage;

import java.util.Scanner;

import interfaces.DesertInterface;
import interfaces.RatStatusInterface;

/**
 * Main class that controls a run of the game.
 * @author Peter Fortin
 *
 */
public class ControlDriver {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		welcomeMessage();
		runGameLoop(input);
		farewellMessage();
		input.close();
	}

	/**
	 * Greet the user
	 */
	private static void welcomeMessage() {
		
		System.out.println("Welcome to Eldritch Desert Rat Runner Simulator!");
	}
	
	/**
	 * Main logic loop controlling the game
	 * @param input scanner
	 */
	private static void runGameLoop(Scanner input) {
		
		boolean keepGoing;
		DesertInterface desert;
		
		do {
			desert = userChoosesDesert(input);
			runRatsThroughDesert(desert);
		} while (keepGoing = nextRun(input));
	}
	
	/**
	 * Create the desert object, rerolling if necessary
	 * @param input scanner
	 * @return DesertInterface desert
	 */
	private static DesertInterface userChoosesDesert(Scanner input) {
		
		int rows;
		int columns;
		boolean reRoll = false;
		DesertInterface desert;
		
		//Get size and map and generate first pass
		rows = getRows(input);
		columns = getColumns(input);
		System.out.println("You've chosen a " + rows + " by " + columns + " desert.");
		desert = ObjectGenerator.createNewDesert(rows, columns);
		
		//Make sure user is happy with map, otherwise reroll
		do {
			System.out.println("Here is a map of your desert.");
			desert.printMap();
			System.out.println("Legend:\nS = Shelter - Refreshes rat's exhaustion level by 1."
					+ "\nW = Waste - Wears down rat's exhaustion level by 1."
					+ "\nH = Hole - Chance to either instantly die, teleport to another hole, or crawl out unharmed.");
			System.out.println("Would you like to reroll the map?\n1) Reroll\n2) Keep Map");
			while (!input.hasNextInt()) {
				input.nextLine();
				System.out.println("Invalid choice.\nWould you like to reroll the map?\n1) Reroll\n2) Keep Map");
			}
			reRoll = input.nextInt() == 1 ? true : false;
			input.nextLine();
			if (reRoll) desert = ObjectGenerator.createNewDesert(rows, columns);
		} while (reRoll);
		return desert;
	}
	
	
	/**
	 * Get the number of rows of the desert from user
	 * @param input scanner
	 * @return number of rows
	 */
	private static int getRows(Scanner input) {
		
		int rows;
		
		System.out.println("Please enter the number of rows of the desert:");
		while(!input.hasNextInt()) {
			input.nextLine();
			System.out.println("Invalid input.\nPlease enter a positive integer:");
		}
		rows = Math.abs(input.nextInt());
		input.nextLine();
		System.out.println();
		return rows;
	}
	
	/**
	 * Get the number of columns from the user
	 * @param input scanner
	 * @return number of columns
	 */
	private static int getColumns(Scanner input) {
		
		int columns;
		
		System.out.println("Please enter the number of columns of the desert:");
		while(!input.hasNextInt()) {
			input.nextLine();
			System.out.println("Invalid input.\nPlease enter a positive integer:");
		}
		columns = Math.abs(input.nextInt());
		input.nextLine();
		return columns;
	}
	
	
	/**
	 * Logic loop that completes a run of a desert
	 * @param DesertInterface desert
	 */
	private static void runRatsThroughDesert(DesertInterface desert) {
		
		RatStatusInterface currentRatStatus; 
		boolean success = false;
		
		currentRatStatus = desert.startRat();
		do {
			currentRatStatus = desert.moveRat(currentRatStatus.getRatID());
			if (currentRatStatus.getRatState() == -1) {
				success = true;
			} else if (currentRatStatus.getRatState() == 1) {
				currentRatStatus = desert.startRat();
			}
		} while (!success);
		
		desert.displayStatistics();
	} 
	
	
	/**
	 * Get input from user to either start another run or exit
	 * @param input scanner
	 * @return boolean choice from user
	 */
	private static boolean nextRun(Scanner input) {
		
		boolean doRun;
		int choice;
		
		System.out.println("Would you like to do another run?");
		System.out.println("1) Start a new run\n2) Exit");
		while(!input.hasNextInt()) {
			input.nextLine();
			System.out.println("Invalid input.\nPlease choose\n)1 Start a new run\n)2 Exit:");
		}
		choice = input.nextInt();
		doRun = choice == 1 ? true : false;
		return doRun;
	}
		
	/**
	 * Say goodbye to user
	 */
	private static void farewellMessage() {
		
		System.out.println("Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn!");
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * COP 3530: Project 1 - Array Searches and Sorts
 * <p>
 * Main class for this project. Contains the main method, as well as methods for sorting, printing formatted
 * output, performing sorting and searching operations, and executing simple algorithms.
 * </p>
 * @author Payton Konrad Weakley n01432685
 * @version Sep 17, 2021
 */
public class Project1 {
	/**
	 * The main method for this class. Prompts the user for required inputs and processes
	 * a CSV file as specified by the user. Processes the input made by user when selecting
	 * a choice from a printed menu. This input determines which of the various functions to
	 * call.
	 *<p>
	 * Most operations within this function take place within a while loop. This loop calls a function
	 * to receive input from the user and will run until the user provides input telling the program to
	 * exit. Within the loop, the input from the user is processed through a series of <code>if, else if</code>
	 * statements
	 *</p>
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		int choice = 0;
		int index;
		boolean isAlphabetical = false;
		Scanner scan = new Scanner(System.in);
		File stateFile = getFile(scan);
		BufferedReader fileReader = new BufferedReader(new FileReader(stateFile));
		State[] stateList = new State[50]; // assumes that there are a maximum of 50 records in file

		fileReader.readLine(); // ignore top line of CSV file
		for(int i = 0; i < 50; i++) {	// load array of States with entities constructed from CSV data
			String[] elementArray = fileReader.readLine().split(",");
			stateList[i] = new State(elementArray);
		}
		do {
			choice = printMenu(scan);
			if (choice == 1) stateReport(stateList); // prints out a state report (all states)
			else if (choice == 2) {		// sorts alphabetically
				bubbleSort(stateList);
				isAlphabetical = true;
			}
			else if (choice == 3) {		// sorts by fatality rate
				selectionSort(stateList);
				isAlphabetical = false;
			}
			else if (choice == 4) {		// sorts by MHI
				insertionSort(stateList);
				isAlphabetical = false;
			}
			else if (choice == 5) {		// find state for given name
				System.out.print("enter state name: ");
				if (isAlphabetical) { // performs binary search if the list is sorted alphabetically
					index = binarySearch(scan.nextLine(), stateList);
					singleStateReport(stateList[index]);
				}
				else { // performs a selection search if the list is not sorted alphabetically
					index = selectionSearch(scan.nextLine(), stateList);
					if (index!= -1) {
						singleStateReport(stateList[index]);
					}
				}
			}
			else if (choice == 6) {
				spearmanMatrix(stateList);
			}

		} while (choice != 7);

	}

	/**
	 * Prompts the user to enter the name of a file, if the file is
	 * not found, the user is prompted to enter a valid file name.
	 *
	 * @param inputScanner scanner used for reading keyboard input
	 * @return File object corresponding to the file name input by the user
	 */
	public static File getFile(Scanner inputScanner) {
		System.out.print("File name: ");
		File userFile = new File(inputScanner.nextLine());
		while (!userFile.exists()) {
			System.out.println("File does not exist");
			System.out.println("File name: ");
			userFile = new File(inputScanner.nextLine());
		}
		return userFile;
	}

	/**
	 * <p>Prints a numbered list to console via System.out and prompts the
	 * user to choose an option on the list by entering the number corresponding
	 * to their choice.</p>
	 * <p>The method returns the integer that the user inputs. The following is the
	 * menu that the user sees.</p>
	 * <p>1. Print a states report</p>
	 * <p>2. Sort alphabetically by name</p>
	 * <p>3. Sort by case fatality rate (CFR)</p>
	 * <p>4. Sort by median household income (MHI)</p>
	 * <p>5. Find and print metrics for a given State</p>
	 * <p>6. Print Spearman's Rho Matrix</p>
	 * <p>7. quit</p>
	 * @return integer between 1 - 7 depending on the choice of the user
	 */
	public static int printMenu(Scanner menuScanner) {
		//Scanner menuScanner = new Scanner(System.in);
		String input;
		int choice = 0;
		do {
			System.out.println();
			System.out.println("1. Print a states report");
			System.out.println("2. Sort alphabetically by name");
			System.out.println("3. Sort by case fatality rate (CFR)");
			System.out.println("4. Sort by median household income (MHI)");
			System.out.println("5. Find and print metrics for a given State");
			System.out.println("6. Print Spearman's Rho Matrix");
			System.out.println("7. quit");
			System.out.println();
			System.out.print("Enter selection and press return to continue: ");
			input = menuScanner.nextLine();
			try {
				choice = Integer.parseInt(input);
			} catch (Exception NumberFormatException){
				System.out.println("  --Your input could not be processed, please only use integers as input.");
			}
			if (choice > 7 || choice < 1) {
				System.out.println("  --Invalid input, enter an integer between 1 and 7 inclusive.");
			}
		} while (choice > 7 && choice < 1);
		return choice;
	}

	/**
	 * Prints a formatted list of stats for a specific state
	 * @param state State object
	 */
	public static void singleStateReport(State state) {
		System.out.println();
		System.out.println("State: " + state.getName());
		System.out.println("Median Household Income: " + state.getMedianIncome());
		System.out.println("Violent Crime Rate: " + state.getViolentRate());
		System.out.println("Covid case Fatality Rate: " + state.getCaseFatalityRate());
		System.out.println("Case Rate: " + state.getCaseRate());
		System.out.println("Death Rate: " + state.getDeathRate());
		System.out.println();
	}

	/**
	 * Prints a table of statistics for all State objects within the array passed to the method.
	 * @param states
	 */
	public static void stateReport(State[] states){
		State s;
		System.out.println("Name           | MHI      | VCR      | CFR       | Case Rate | Death Rate");
		System.out.println("-------------------------------------------------------------------------");
		for (int i = 0; i < states.length; i++) {
			s = states[i];
			System.out.format("%-15s| %-9d| %-9.2f| %-10.6f| %-10.2f| %-10.2f%n",
					s.getName(),
					s.getMedianIncome(),
					s.getViolentRate(),
					s.getCaseFatalityRate(),
					s.getCaseRate(),
					s.getDeathRate());
		}
	}

	/**
	 * Prints an array of Spearman's Rho coefficients. To do this using arrays, new copies of the intial
	 * State array need to be made. This is done using the clone() method from java.lang.Object. This method
	 * uses custom methods to calculate 4 rho coefficients and displays them in a formatted table.
	 * @param states an array of State objects
	 */
	public static void spearmanMatrix (State[] states) {
		/* 
		 * For each intersection
		 * 		1) calculate di for each state
		 * 			a. get difference between ranking of element for both data points
		 * 				  i. start with any sorted array, get String name of element n
		 * 				 ii. get rankings of that element by finding index of that element in both arrays
		 * 				iii. (ranking.array1 - ranking.array2) = difference
		 * 			b. square that value
		 * 			
		 * 		2) sum all di of each state (sum += currentstate.di)
		 * 		3) plug into formula
		 * 		4) save as variable for that intersection
		*/
		/*		
		 * 	For Median Household Income, Violent Crime Rate, Covid Case Rate, Covid Death Rate
		 * 		------------------------------
		 * 		|        |   MHI   |   VCR   |
		 * 		------------------------------          		  
	     *		|  CCR   |   x1    |   x2    |
	     *		------------------------------
		 *      |  CDR   |   x3    |   x4    |
		 *      ------------------------------
		 */
		float[] spearmanVal = new float[4];
		State[] caseRateList = states.clone();
		State[] deathRateList = states.clone();
		State[] violentRateList = states.clone(); 
		State[] medianIncomeList = states.clone();
		caseRateSort(caseRateList);
		deathRateSort(deathRateList);
		violentRateSort(violentRateList);
		insertionSort(medianIncomeList);
		
		spearmanVal[0] = spearmanRho(sumRankDiff(caseRateList, medianIncomeList), states.length);
		spearmanVal[1] = spearmanRho(sumRankDiff(caseRateList, violentRateList), states.length);
		spearmanVal[2] = spearmanRho(sumRankDiff(deathRateList, medianIncomeList), states.length);
		spearmanVal[3] = spearmanRho(sumRankDiff(deathRateList, violentRateList), states.length);
		
		System.out.println(" -------------------------------------");
		System.out.println( "|           |    MHI     |     VCR    |");
		System.out.println(" -------------------------------------");
		System.out.format( "|Case Rate  |  %7.4f   |  %7.4f   |%n", spearmanVal[0], spearmanVal[1]);
		System.out.println(" -------------------------------------");
		System.out.format( "|Death Rate |  %7.4f   |  %7.4f   |%n", spearmanVal[2], spearmanVal[3]);
		System.out.println(" -------------------------------------");
		
	}

	/**
	 * Calculates the Spearman's Rho coefficient between two lists of States. The method performs
	 * the following calculation where n is the size of the list and d<sub>i</sub> is the rank difference
	 * of element i in the two lists.
	 * <span>&rho;</span> = 1 - ( 6<span>&Sigma;</span>d<sub>i</sub><sup>2</sup> / n(n<sup>2</sup> - 1) )
	 *
	 * @param sum the sum of the rank differences of each element inside of two State object arrays
	 * @param size The size of the arrays of state objects
	 * @return Spearman's Rho coefficient
	 */
	public static float spearmanRho(int sum, int size) {
		float top = (6 * (float) sum);
		float bottom = (size * ((size*size) - 1));
		float result = 1 - top/bottom;

		return result;
	}
	/**
	 * calculates the sum of d<sub>i</sub><sup>2</sup> for
	 * <code>State[] list1</code> and <code>State[] list2</code>.
	 * Arrays passed to this method should be sorted ascending by different keys.
	 * @param list1 array of <code>State</code> objects
	 * @param list2 array of <code>State</code> objects
	 * @return int sum of d<sub>i</sub><sup>2</sup> for the two sorted lists.
	 */
	public static int sumRankDiff(State[] list1, State[] list2) {
		int sum = 0;
		int diff = 0;
		int rank1;
		int rank2;
		
		for (State state : list1) {
			// index of state[i] in list1 - index of that same state in list 2
			rank1 = selectionSearch(state.getName(), list1);
			rank2 = selectionSearch(state.getName(), list2);
			diff = rank1 - rank2;
			sum += (diff * diff);
		}
		return sum;
	}

	/**
	 * performs a simple swap on two State objects within an array
	 * that that passed to this method.
	 * @param s1 index of element in State array
	 * @param s2 index of element in State array
	 * @param states array containing elements that need to be swapped
	 */
	public static void stateSwap(int s1, int s2, State[] states) {
		State temp = states[s1];
		states[s1] = states[s2];
		states[s2] = temp;
	}

	/**
	 * Sorts an array of State objects by CFR via a selection sort
	 * @param states Array of states
	 * @return same array that's passed in, but sorted
	 */
	public static void selectionSort(State[] states) {
		int j;

		for (int i = 0; i < states.length - 1; i++) {
			// largest = states[i]
			for (j = i+1; j < states.length; j++) {
				if(states[j].getCaseFatalityRate() 
						< states[i].getCaseFatalityRate()){
					stateSwap(j, i, states);
				}
			}
		}
		System.out.println("State list sorted by Case Fatality Rate (CFR)");
		//return states;
	}

	/**
	 * Sorts the array by Median Household Income
	 * @param states array of State objects
	 */
	public static void insertionSort(State[] states) {
		int i = 0;
		int j = 0;
		State temp;
		for (i = 1; i < states.length; i++) {
			temp = states[i];
			for(j = i-1; j >= 0 && (states[j].getMedianIncome() > temp.getMedianIncome()); j--) {
				states[j+1] = states[j];
			}
			states[j+1] = temp;
		}
		System.out.println("State list sorted by Median Household Income (MHI)");
	}

	/**
	 * Uses a bubble sort to sort a list of states by name alphabetically
	 * @param states array of State objects
	 */
	public static void bubbleSort(State[] states) {
		/* sort by name
		 * 1) compare leftmost item with second leftmost
		 * 		if smaller, swap
		 * 		else compare with next element to left
		 * 		stop when front of array is reached
		 */
		int i;
		int j;
		State temp;

		for (i = 0; i < states.length - 1; i++) {
			for (j = states.length-1; j>i; j--) {
				if (states[j].getName().compareTo(states[j-1].getName()) < 0) { // compares names of states alphabetically
					stateSwap(j, (j-1), states);
				}
			}
		}
		System.out.println("State list sorted Alphabetically");
	}
	/**
	 * Uses an optimized bubble sort with a boolean variable to sort array of State objects by COVID case rate
	 * <p>
	 * 	Algorithm for optimised sort was found here <a href="https://www.geeksforgeeks.org/bubble-sort/">http://geeksforgeeks.org/bubble-sort/</a>
	 * </p>
	 * @param states array of State objects
	 * @return array of State objects sorted by case rate ascending
	 */	
	public static void caseRateSort (State[] states ) {
		int i;
		boolean hasSwapped = true;
		while (hasSwapped) {
			hasSwapped = false;
			for (i = 1; i < states.length; i++) {
				if (states[i].getCaseRate() < states[i-1].getCaseRate()) {
					hasSwapped = true;
					stateSwap(i, (i-1) , states);
				}
			}
		}
		System.out.println("Sorted by Case Rate");
	}
	/**
	 * Uses an optimized bubble sort with a boolean variable to sort array of State objects by Violent Crime Rate
	 * <p>
	 *	Algorithm for optimised sort was found here <a href="https://www.geeksforgeeks.org/bubble-sort/">http://geeksforgeeks.org/bubble-sort/</a>
	 * </p>
	 * @param states array of State objects
	 * @return Array of State objects sorted by violent crime rate ascending
	 */	
	public static void violentRateSort (State[] states ) {
		int i;
		boolean hasSwapped = true;
		while (hasSwapped) {
			hasSwapped = false;
			for (i = 1; i < states.length; i++) {
				if (states[i].getViolentRate() < states[i-1].getViolentRate()) {
					hasSwapped = true;
					stateSwap(i, (i-1) , states);
				}
			}
		}
		System.out.println("Sorted by Violent Crime Rate");
	}
	/**
	 * Uses an optimized bubble sort with a boolean variable to sort array of State objects by COVID death rate (note: <b>not</b> the same as COVID case death rate)
	 * <p>
	 *     Algorithm for optimised sort was found here <a href="https://www.geeksforgeeks.org/bubble-sort/">http://geeksforgeeks.org/bubble-sort/</a>
	 * </p>
	 * @param states of State objects
	 * @return Array of State objects sorted by COVID death rate  ascending
	 */	
	public static void deathRateSort (State[] states ) {
		int i;
		boolean hasSwapped = true;
		while (hasSwapped) {
			hasSwapped = false;
			for (i = 1; i < states.length; i++) {
				if (states[i].getDeathRate() < states[i-1].getDeathRate()) {
					hasSwapped = true;
					stateSwap(i, (i-1) , states);
				}
			}
		}
		System.out.println("Sorted by Death Rate");
	}
	/**
	 * Searches for a State object by its <code>name</code> attribute. This method is to
	 * be used on an array that is not sorted by state name alphabetically.
	 * @param stateName name of the state to be located in the array.
	 * @param states array of State objects
	 * @return index of State with name stateName in State[] states
	 */
	public static int selectionSearch(String stateName, State[] states){
		for (int i = 0; i < states.length; i++){
			if (stateName.equals(states[i].getName())){
				//singleStateReport(state);
				return i;
			}
		}
		System.out.println("  --Search failed.");
		return -1;
	}

	/**
	 * Searches for a State object by its <code>name</code> attribute. Method only works on lists of states sorted
	 * by name alphabetically.
	 * @param stateName
	 * @param states
	 * @return
	 */
	public static int binarySearch(String stateName, State[] states){
		int low = 0;
		int high = states.length - 1;
		int mid = (high + low) / 2;

		while (low <= high) {
			if (stateName.equals(states[mid].getName())) {
				singleStateReport(states[mid]);
				return mid;
			} else if (stateName.compareTo(states[mid].getName()) < 0 ) {
				high = mid - 1;
				mid = (high + low) / 2;
			} else if (stateName.compareTo(states[mid].getName()) > 0) {
				low = mid + 1;
				mid = (high + low) / 2;
			}
		}
		System.out.println("  --search failed");
		return -1;
	}
}


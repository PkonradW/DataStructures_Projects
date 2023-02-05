import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * COP 3530: Project 3 - Linked Lists
 * <p>
 *     Main class for this project, contains the main method, and methods for getting user input, and
 *     printing formatted output.
 * </p>
 * @author Konrad Weakley n01432685
 * @version 10/23/2021
 */
public class Project3 {
   /**
    * The main method for this class. Prompts the user for required inputs and processes
    * a CSV file as specified by the user. Processes the input made by user when selecting
    * a choice from a printed menu. This input determines which of the various functions to
    * call.
    *     <p>
    *     Before entering a while loop which allows the user to choose a variety of methods to call,
    *     this method will
    *     </p>
    *     <p>1. Load any state with a COVID-19 Death Rate (DR) between 70 and 250 inclusive into a stack.</p>
    *     <p>2. Print the stack starting from the top.</p>
    *     <p>3. Create a priority queue and insert State objects into the priority queue by popping them off of the
    *     stack one at a time.</p>
    *     <p>4. Print the priority queue from highest priority to lowest.</p>
    * @param args
    * @throws IOException
    */
    public static void main(String[] args) throws IOException {
        int choice = 0;
        File stateFile = getFile();
        BufferedReader fileReader = new BufferedReader(new FileReader(stateFile));
        Stack stateStack = new Stack();
        PriorityQ stateQ = new PriorityQ();
        fileReader.readLine().split(","); // ignore top line of CSV file

        for (int i = 0; i < 50; i++) {      // push state onto stack if the state's COVID-19 DR is >70 and <250
            String[] elementArray = fileReader.readLine().split(",");
            State s = new State(elementArray);
            if ( s.getDeathRate() >= 70
                && s.getDeathRate() <= 250) {
                stateStack.push(s);
            }
        }
        stateStack.printStack(stateStack.first);
        while (!stateStack.isEmpty()) {     // insert States into the PriorityQ by popping them off of the stack
            stateQ.insert(stateStack.pop());
        }
        stateQ.printPriorityQueue(stateQ.first);

        // start printing menu for user
        while (choice != 3) {
            choice = printMenu();
            switch (choice) {
                case 1: intervalDeleter(stateQ);
                    break;
                case 2: {
                    if (!stateQ.isEmpty()) stateQ.printPriorityQueue(stateQ.first);
                    else System.out.println("Can't print an empty queue");
                    break;
                }
                case 3: break;
            } // end switch
        } // end while
    }

    /**
     * Prompts user for the name of the CSV file to use
     * @return userInput: string that user specified with keyboard input
     */
    public static File getFile() {
        Scanner inputScanner = new Scanner(System.in);
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
     * Prints a menu to the user, receives input from the user, and checks to make sure the input is valid.
     * @return integer responding to choice from the user
     */
    public static int printMenu() {
        Scanner menuScanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println();
            System.out.println("1. Enter a DR interval for deletions");
            System.out.println("2. Print the Priority Queue");
            System.out.println("3. Quit");
            System.out.println();
            System.out.print("Enter selection and press return to continue: ");
            try {
                choice = Integer.parseInt(menuScanner.nextLine());
            } catch (Exception NumberFormatException) {
                System.out.println("  --Your input could not be processed, please only use integers as " +
                        "input.");
            }
            if (choice > 3 || choice < 1) {
                System.out.println("  --Invalid input, enter an integer between 1 and 3 inclusive.");
            }
        } while (choice > 3 || choice < 1);
        return choice;
    }

    /**
     * Prompts the user for input describing the minimum and maximum COVID-19 death rates to delete.
     * Handles exceptions and edge cases to make sure that the input is valid. (everything >0 and maximum > minimum)
     * @param queue a Priority queue of states sorted by COVID-19 Death rate
     */
    public static void intervalDeleter(PriorityQ queue) {
        Scanner scan = new Scanner(System.in);
        int low = -1;
        int high = -1;
        do {        // will ask for input until the low and high are both greater than 0
            System.out.print("Minimum COVID-19 Death Rate to delete: ");
            try {
                low = Integer.parseInt(scan.nextLine());        // get input for low, parse as int
            } catch (Exception NumberFormatException) {
                System.out.println("Your input could not be parsed,please only use integers as input");
            }
            System.out.print("Maximum COVID-19 Death Rate to delete ");
            try {
                high = Integer.parseInt(scan.nextLine());       // et input for high, parse as int
            } catch (Exception NumberFormatException) {
                System.out.println("Your input could not be parsed,please only use integers as input");
            }
            if (high <= low) {
                System.out.println("Please make sure the minimum COVID-19 DR entered is lower than the maximum" +
                                   " COVID-19 DR entered");
            }
            if (high < 0 || low < 0) {
                System.out.println("Cannot enter a negative Death Rte");
            }
        } while ( low < 0
                || high < 0
                || low > high);
        queue.intervalDelete(low, high);
    }
}
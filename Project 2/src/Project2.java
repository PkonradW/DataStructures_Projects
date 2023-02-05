import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
//import java.util.*;

/**
 * COP 3530: Project 2 - Stacks and Priority Queues
 * <p>
 *     Main class for this project, contains the main method, and methods for getting input strings from the user,
 *     printing formatted output, and creating new State objects.
 * </p>
 * @author Payton Konrad Weakley
 * @version Oct 9, 2021
 */
public class Project2 {
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
        File stateFile = getFile();
        //File stateFile = new File("States2.csv"); // hardcoded for now, don't leave me like this
        BufferedReader fileReader = new BufferedReader(new FileReader(stateFile));
        State[] stateList = new State[50]; // assumes that there are a maximum of 50 records in file
        fileReader.readLine().split(","); // ignore top line of CSV file
        Stack stateStack = new Stack(50);
        PriorityQ stateQ = new PriorityQ(50);

        for(int i = 0; i < 50; i++) {	// load array of States with entities constructed from CSV data
            String[] elementArray = fileReader.readLine().split(",");
            stateList[i] = new State(elementArray);
        }
        for (int i = 0; i < 50; i++) { // load data into Stack and Priority Queue
            stateStack.push(stateList[i]);
            stateQ.insert(stateList[i]);
        }

        while (choice != 7) {
            choice = printMenu();
            if (choice == 1) {      // Print the stack
                stateStack.printStack();
            }
            else if (choice == 2) { // Pop the top State off the stack
                System.out.println(stateStack.pop().getName() + " has been popped from the stack.");

            }
            else if (choice == 3) { // Push a state onto the stack
                stateStack.push(stateMaker());
                System.out.println("State pushed onto stack");
            }
            else if (choice == 4) { // Print Priority Q
                stateQ.printQueue();
            }
            else if (choice == 5) { // Remove state object from PriorityQ
                System.out.println(stateQ.remove().getName() + " has been removed from the Priority Queue");
            }
            else if (choice == 6) { // Insert a state object onto PriorityQ
                stateQ.insert(stateMaker());
                System.out.println("State inserted into PriorityQ");

            }
        }

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
     * <p>Prints a numbered list to console via System.out and prompts the
     * user to choose an option on the list by entering the number corresponding
     * to their choice.</p>
     * <p>The method returns the integer that the user inputs. The following is the
     * menu that the user sees.</p>
     * <p>1. Print Stack</p>
     * <p>2. Pop a state object off of stack</p>
     * <p>3. Push a state object onto stack</p>
     * <p>4. Print priority queue</p>
     * <p>5. Remove a state object from priority queue</p>
     * <p>6. Insert a state object into priority queue</p>
     * <p>7. quit</p>
     * @return integer between 1 - 7 depending on the choice of the user
     */
    public static int printMenu() {
        Scanner menuScanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println();
            System.out.println("1. Print Stack");
            System.out.println("2. Pop a state object off of stack");
            System.out.println("3. Push a state object onto stack");
            System.out.println("4. Print priority queue");
            System.out.println("5. Remove a state object from priority queue");
            System.out.println("6. Insert a state object into priority queue");
            System.out.println("7. quit");
            System.out.println();
            System.out.print("Enter selection and press return to continue: ");
            try {
                choice = Integer.parseInt(menuScanner.nextLine());
            } catch (Exception NumberFormatException) {
                System.out.println("  --Your input could not be processed, please only use integers as " +
                                        "input.");
            }
            if (choice > 7 || choice < 1) {
                System.out.println("  --Invalid input, enter an integer between 1 and 7 inclusive.");
            }
        } while (choice > 7 || choice < 1);
        return choice;
    }

    /**
     * Makes a new state object while prompting the user for custom input for every elemenet of a state
     * object.
     * @return state new state created by the user
     */
    public static State stateMaker() {
        Scanner scan = new Scanner(System.in);
        String[] elements = new String[9];

        System.out.print("Please enter the Name: ");
        elements[0] = scan.nextLine();
        System.out.print("Please enter the Capitol: ");
        elements[1] = scan.nextLine();
        System.out.print("Please enter the Region: ");
        elements[2] = scan.nextLine();
        System.out.print("Please enter the number of seats the state has in the House of " +
                            "representatives: ");
        elements[3] = scan.nextLine();
        System.out.print("Please enter the state's population: ");
        elements[4] = scan.nextLine();
        System.out.print("Please enter the number of COVID cases in the state: ");
        elements[5] = scan.nextLine();
        System.out.print("Please enter the number of COVID deaths in the state: ");
        elements[6] = scan.nextLine();
        System.out.print("Please enter the Median Household Income of the state: ");
        elements[7] = scan.nextLine();
        System.out.print("Please enter the Violent Crime Rate of the state: ");
        elements[8] = scan.nextLine();
        State newState = new State(elements);
        System.out.println(newState.getName() + " successfully created.");
        return newState;
    }
}

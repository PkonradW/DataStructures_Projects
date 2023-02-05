import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * COP 3530: Project 5 - Hash Tables
 * <p>
 *     Main class for this project, contains the main method, and methods for getting user input, and
 *     printing formatted output.
 * </p>
 * @author Konrad Weakley n01432685
 * @version 12/10/2021
 */
public class Project5 {
    /**
     * The main method for this class. Prompts the user for required inputs and processes
     * a CSV file as specified by the user. Processes the input made by user when selecting
     * a choice from a printed menu. This input determines which of the various functions to
     * call.
     *     <p>
     *     Before entering a while loop which allows the user to choose a variety of methods to call,
     *     this method will
     *     </p>
     *     <p>
     *       1. Prompt the user for the name of a CSV file as input for the program
     *     </p>
     *     <p>
     *       2. Create a  hash table and fill it with data from the parsed CSV data stored in a temporary State object
     *     </p>
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int choice = 0;
        File stateFile = getFile();
        BufferedReader fileReader = new BufferedReader(new FileReader(stateFile));
        HashTable tabel = new HashTable();
        Scanner scan = new Scanner(System.in);
        fileReader.readLine().split(","); // ignore top line of CSV file

        for (int i = 0; i < 50; i++) { // load CSV file with data from the CSV file
            String[] elementArray = fileReader.readLine().split(",");
            State s = new State(elementArray);
            tabel.insert(s.getName(), s.getPopulation(), s.getCovidDeaths());
        }
        while (choice != 6) {
            choice = printMenu();
            switch (choice) {
                case 1 -> tabel.display(); // print the table
                case 2 -> { // delete a state
                    System.out.print("Name of state to delete: ");
                    String delName = scan.nextLine();
                    tabel.delete(delName);
                }
                case 3 -> { // insert a new state
                    long population;
                    long deaths;
                    System.out.print("Enter name of state: ");
                    String insertName = scan.nextLine();
                    System.out.print("Enter state population: ");
                    try {
                        population = Long.parseLong(scan.nextLine());
                    } catch (Exception NumberFormatException) {
                        System.out.println("Your input could not be processed, please only use" +
                                "integers for this value");
                        break;
                    }
                    System.out.print("Enter state COVID deaths: ");
                    try {
                        deaths = Long.parseLong(scan.nextLine());
                    } catch (Exception NumberFormatException) {
                        System.out.println("Your input could not be processed, please only use" +
                                "integers for this value");
                        break;
                    }
                    tabel.insert(insertName, population, deaths);
                }
                case 4 -> { // print DR data for a state of a given name
                    System.out.print("Enter name of state to search for: ");
                    String searchName = scan.nextLine();
                    tabel.findAndPrint(searchName);
                }
                case 5 -> tabel.printEmptyAndCollidedCells(); // print empty and collided cells
                case 6 -> System.out.println("Goodbye!"); // quit the program
            } // end switch
        } // end while

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
            System.out.println("1. Print hash table");
            System.out.println("2. Delete a state of a given name");
            System.out.println("3. Insert a state of its name, population, and COVID deaths");
            System.out.println("4. Search and print a state and its DR for a given name");
            System.out.println("5. Print numbers of empty and collided cells");
            System.out.println("6. Exit");
            System.out.println();
            System.out.print("Enter selection and press return to continue: ");
            try {
                choice = Integer.parseInt(menuScanner.nextLine());
            } catch (Exception NumberFormatException) {
                System.out.println("  --Your input could not be processed, please only use integers as " +
                        "input.");
            }
            if (choice > 6 || choice < 1) {
                System.out.println("  --Invalid input, enter an integer between 1 and 6 inclusive.");
            }
        } while (choice > 8 || choice < 1);
        return choice;
    }

    /**
     * Prompts user for the name of the CSV file to use
     *
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
}

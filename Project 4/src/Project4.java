import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * COP 3530: Project 4 - Binary Search Trees
 * <p>
 *     Main class for this project, contains the main method, methods for creating, modifying, and performing operations
 *     on a BST, as well as  methods for getting user input and printing some formatted output like menus.
 * </p>
 * @author Konrad Weakley n01432685
 * @version 11/19/2021
 */
public class Project4 {
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
     *       2. Create a Binary Search Tree by calling the <code>BinarySearchTree.insert</code> method.
     *     </p>
     *     <p>
     *     After that, the program will enter a loop focused around a menu with selectable options. The following steps
     *     are repeated until the user chooses to exit:
     *     </p>
     *     <p>
     *        1. Print a formatted menu and retrieve input from the user
     *     </p>
     *     <p>
     *         2. Call a method in the BinarySearchTree class corresponding to the user's input.
     *     </p>
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int choice = 0;
        File stateFile = getFile();
        BufferedReader fileReader = new BufferedReader(new FileReader(stateFile));
        BinarySearchTree BST = new BinarySearchTree();
        Scanner scan = new Scanner (System.in);
        fileReader.readLine().split(","); // ignore top line of CSV file

        // insert stuff into BST
        for (int i = 0; i < 50; i++) {
            String[] elementArray = fileReader.readLine().split(",");
            State s = new State(elementArray);
            BST.insert(s.getName(), s.getDeathRate());
        }
        // start printing menu for user
        while (choice != 8) {
            choice = printMenu();
            switch (choice) {
                case 1 -> BST.printInorder();
                case 2 -> BST.printPreorder();
                case 3 -> BST.printPostorder();
                case 4 -> { // Delete a state for a given name
                    System.out.print("Enter name of state to delete: ");
                    BST.delete(scan.nextLine());
                }
                case 5 -> { // Search and print state & its path for a given name
                    System.out.print("Enter name of state to search for: ");
                    BST.find(scan.nextLine());
                }
                case 6 -> { // Print bottom states regarding DR
                    System.out.print("Enter number of states :");
                    BST.printBottomStates(scan.nextInt());
                }
                case 7 -> { // Print top states regarding DR
                    System.out.print("Enter number of states :");
                    BST.printTopStates(scan.nextInt());
                }
                case 8 -> System.out.println("Goodbye!");
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
            System.out.println("1. Print the BST Inorder");
            System.out.println("2. Print the BST Preorder");
            System.out.println("3. Print the BST Postorder");
            System.out.println("4. Delete a state for a given name");
            System.out.println("5. Search for and print a state and it's path");
            System.out.println("6. Print worst 'x' states by DR given x");
            System.out.println("7. Print best 'x' states by DR given x ");
            System.out.println("8. Quit");
            System.out.println();
            System.out.print("Enter selection and press return to continue: ");
            try {
                choice = Integer.parseInt(menuScanner.nextLine());
            } catch (Exception NumberFormatException) {
                System.out.println("  --Your input could not be processed, please only use integers as " +
                        "input.");
            }
            if (choice > 8 || choice < 1) {
                System.out.println("  --Invalid input, enter an integer between 1 and 8 inclusive.");
            }
        } while (choice > 8 || choice < 1);
        return choice;
    }
}
/**
 * Class that contains two subclasses and various methods for the implementation of
 * a Hash Table that uses Separate Chaining to handle collisions. The table is implemented
 * using an array of Linked Lists. The Linked Lists and their nodes are defined using classes
 * that are defined within the scope of this class.
 * @author Payton Konrad Weakley
 * @versino 12/10/2021
 */
public class HashTable {

    private List[] listArray;

    /**
     * No-arg constructor for HashTable. Initializes a List array of size 101.
     */
    public HashTable() {
        listArray = new List[101];
        for (int i = 0; i < 101; i++) {
            listArray[i] = new List();
        }
    }

    /**
     * Creates node with passed parameters and inserts into the hash table.
     * @param state, name of state
     * @param population population of state
     * @param deaths number of deaths
     */
    public void insert (String state, long population, long deaths) {
        Node noder = new Node(state, population, deaths);
        int hashVal = hashFunc(noder.name);
        listArray[hashVal].listInsert(noder);
    }

    /**
     * Finds index of passed state name
     * @param state Name of the state to find
     * @return table index of state if found, -1 if not found
     */
    public int find(String state) {
        int index = hashFunc(state);
        Node noder = listArray[index].listFind(state);

        if (noder == null) return -1;
        else return index;
    }

    /**
     * Deletes node in list with associated name
     * @param state name of state to delete
     */
    public void delete (String state) {
        listArray[hashFunc(state)].listDelete(state);
    }

    /**
     * Calls printList for every list in the array
     */
    public void display() {
        for (int i = 0; i < 101; i++) {
            listArray[i].printList(i);
        }
    }

    /**
     * Calculates and prints the numbers of empty and collided cells in the Hash Table.
     */
    public void printEmptyAndCollidedCells() {
        int empty = 0;
        int collided = 0;
        for (int i = 0; i <101; i++) {
            if (listArray[i].first == null)
                empty++;
            else if (listArray[i].first.nextNode != null)
                collided++;
        } // end for loop
        System.out.println("There are " + empty + " empty cells and " +
                collided + " collided cells.");
    } // end method

    /**
     * Calculates the index of an element by summing the values of all the characters in
     * the 'name' string and then calculating the value of that sum mod 101 (sum % 101)
     * @param name key for the table
     * @return hashed value of passed string
     */
    public int hashFunc(String name) {
        int value = 0;
            char[] chars = name.toCharArray();
        for (char aChar : chars) { // loop adds unicode value of every character to the return variable
            value += aChar;
        }
        return value % 101;
    }

    /**
     * Searches for the node with the name passed in by the calling function.
     * <p>
     *     Index is obtained by calling the find(String state) method, the appropriate node is
     *     then searched for and returned from the list at that index using the listFind() method.
     *     Finally, output is formatted and printed using the data within that node.
     * </p>
     * <p>
     *     If no such node is found then a message is printed to the user indicating such.
     * </p>
     *
     * @param name name of state to find and print
     */
    public void findAndPrint (String name) {
        int index = find(name);
        if (index >= 0) {
            Node noder = listArray[index].listFind(name);
            System.out.printf("%s found at index %d with a DR of %.2f", noder.name, index, (double)noder.deaths/noder.population*100000);
        } else {
            System.out.println("not found");
        }
    }


    /**
     * Node class obtained from project instructions.
     */
    private class Node {
        String name;
        long population;
        long deaths;
        Node nextNode;
        public Node(String name, long population, long deaths) { this.name = name;
            this.population = population;
            this.deaths = deaths;
        }
        public void printNode() { System.out.printf("%-30s %-20.2f\n", name,
                (double)deaths/population*100000); }
    }

    /**
     * Class for a Singly-Linked, Double-Ended list
     */
    private class List {
        private Node first;
        private Node last;

        /**
         * Constructor for a List that sets first and last to null.
         */
        public List() {
            first = null;
            last = null;
        }

        /**
         * Inserts new node at the end of the List
         * @param node to be inserted.
         */
        public void listInsert(Node node) {
            Node previous = null;
            Node current = first;

            while (current != null) { // until the last node is reached
                previous = current;
                current = current.nextNode;
            }
            if (previous == null) { // if inserting at beginning
                first = node;
            }
            else {
                previous.nextNode = node;
            }
            node.nextNode = null;
            last = node;
        }

        /**
         * Deletes state of given name
         * @param name of State to delete
         */
        public void listDelete(String name) {
            Node previous = null;
            Node current = first;
            if (first == null) { // if list is empty
                System.out.println("No such state exists in this table");
                return;
            }
            while (current != null
                    && !name.equals(current.name) ) {
                previous = current;
                current = current.nextNode;
            }
            if (first == last) { // if a list of a single node
                first = null;
                last = null;
            }
            else if (previous == null) { // if deleting first node
                first = first.nextNode;
            }
            else if (current == last) { // if deleting last node
                last = previous;
                last.nextNode = null;
            }
            else { // if deleting a node that is not first or last
                previous.nextNode = current.nextNode;
            }
        }

        /**
         * Searches for and returns node with provided name
         * @param name of state to search for
         * @return Node with name field provided if found, null if not found
         */
        public Node listFind(String name) {
            Node current = first;

            while (current != null && !name.equals(current.name) ) {
                current = current.nextNode;
            }
            if (current!=null) return current;
            return null;
        }

        /**
         * Prints and formats the contents of each node in the list according to project instructions
         */
        public void printList(int i ) {
            Node current = first;
            boolean hasPrinted = false;
            while (current != null) {
                if (current == first) System.out.printf("%3d. ", i);
                else System.out.print("     ");
                current.printNode();
                current = current.nextNode;
                hasPrinted = true;
            }
            if (!hasPrinted) System.out.printf("%3d. Empty\n", i);
        }
    }


}

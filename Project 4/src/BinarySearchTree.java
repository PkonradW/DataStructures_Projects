

/**
 * Class implements the following methods:
 * <p>1. A no-parameter constructor that creates an empty tree.</p>
 * <p>2. The method: public void insert(String name, double DR)  that will insert a node into the
 * proper position in the search tree based on state name.</p>
 * <p>3. The method: public double find(String name) that will search the tree for the state of the
 * given name and if found will return the DR or -1 if not found.  If found, this method should
 * print out the path of the found node.  The path of a node is defined as the sequence of nodes
 * from root to the node.</p>
 * <p>4. The method: public void delete(String name) that will find and delete the given state from
 * the tree.</p>
 * <p>5. The method: public void printInorder() that will traverse the tree in using a Inorder
 * traversal (LNR) and print each node.</p>
 * <p>6. The method: public void printPreorder() that will traverse the tree in using a Preorder
 * traversal (NLR) and print each node.</p>
 * <p>7. The method: public void printPostorder() that will traverse the tree in using a Postorder
 * traversal (LRN) and print each node.</p>
 * <p>8. The method: public void printBottomStates(int c) that will find and print in order the
 * bottom c states regarding DR, that is, the c states with highest DR.  These states should be
 * printed in a descending order.  If there are less than c states in the tree, print all of them.
 * (Note: for this method, you are required NOT to use any extra binary search tree and NOT
 * to use any sorting method.  Hint: you may consider using an array of size c and a few
 * other constant-memory variables.)</p>
 * <p>9. The method: public void printTopStates(int c) that will find and print in order the top c
 * states regarding DR, that is, the c states with lowest DR.  These states should be printed in an
 * ascending order.  If there are less than c states in the tree, print all of them. (Note: for this
 * method, you are required NOT to use any extra binary search tree and NOT to use any
 * sorting method.  Hint: you may consider using an array of size c and a few other constant-
 * memory variables.)</p>
 *
 * <p>
 *     Besides the above methods which were defined in the project instructions, other methods are included in
 *     this class to search for and store data pertaining to specific nodes.
 * </p>
 *
 * @author: Payton Konrad Weakley
 * @Version: 11/19/2021
 */
public class BinarySearchTree {
    private Node root;
    private Node smallest;
    private Node largest;
    private Node floor;
    private Node ceiling;

    /**
     * A no-parameter constructor for a BST
     */
    public BinarySearchTree() {

    }

    /**
     * Inserts node into the proper position in the search tree based on state name
     * @param name name of state to insert into tree
     * @param DR DR of state
     */
    public void insert (String name, double DR) {
        Node current = root;
        Node newNode = new Node();
        newNode.stateName = name;
        newNode.deathRate = DR;

        if (root == null) { // If tree is empty
            root = newNode;
            return;
        }
        while (true) {
            int i = newNode.stateName.compareTo(current.stateName);
            if (i < 0) { // name is less than current node's name
                if (current.lChild == null) {
                    current.lChild = newNode;
                    return;
                }
                else current = current.lChild;
            }
            else { // name is greater than or equal to current node's name
                if (current.rChild == null) {
                    current.rChild = newNode;
                    return;
                }
                else current = current.rChild;
            }
        }// end while loop
    } // end method

    /**
     * <p>
     *     Searches the tree for the sate of a given name and, if found, will return the DR or -1 if not found.
     * </p>
     * <p>
     *     If node is found, method will print the path of the found node, path is defined as the sequence of
     *     nodes from the root to the node.
     * </p>
     * @param name name of State to find
     * @return DR of node if found, -1 otherwise
     */
    public double find(String name) {
        Node current = root;
        String path = "Path: " + root.stateName;
        while ( name.compareTo(current.stateName)!=0 ) { // While name and current.stateName are not equivalent
            if (current != root)
                path = path + " -> " + current.stateName;
            if (name.compareTo(current.stateName) > 0) {
                current = current.rChild;
            }
            else {
                current = current.lChild;
            }
            if(current == null) {
                System.out.println(name + " is not found");
                return -1;
            }
        } // end while
        System.out.println(path + " -> " + current.stateName);
        return current.deathRate;
    } // end method


    /**
     * Deletes State of a given name off the tree.
     * Algorithm taken from Textbook: "Data Structures & Algorithms in Java 2e"
     * @param name name of state to be deleted
     */
    public void delete(String name) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (!current.stateName.equals(name)) {
            parent = current;
            if (name.compareTo(current.stateName) < 0) {
                isLeftChild = true;
                current = current.lChild;
            }
            else {
                isLeftChild = false;
                current = current.rChild;
            }
            if (current == null) {
                System.out.println(name + " not found.");
                return;
            }
        } // end while
        if (current.lChild == null
                && current.rChild == null) {
            if (current == root) {
                root = null;
            }
            else if (isLeftChild) {
                parent.lChild = null;
            }
            else {
                parent.rChild = null;
            }
        }
        else if (current.rChild == null) {
            if (current == root) {
                root = current.lChild;
            }
            else if (isLeftChild) {
                parent.lChild = current.lChild;
            }
            else {
                parent.rChild = current.lChild;
            }
        }
        else if (current.lChild == null) {
            if (current == root) {
                root = current.rChild;
            }
            else if (isLeftChild) {
                parent.lChild = current.rChild;
            }
            else {
                parent.rChild = current.rChild;
            }
        }
        else {
            Node successor = getSuccessor(current);
            if(current == root) {
                root = successor;
            }
            else if(isLeftChild) {
                parent.lChild = successor;
            }
            else {
                parent.rChild = successor;
            }
            successor.lChild = current.lChild;
        }
        System.out.println(name + " was deleted.")
    }

    /**
     * Returns node with second-highest value after delnode is deleted. to be used in delete() method
     * @param delNode
     * @return successor to delnode
     */
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rChild;
        while(current != null) {
            successorParent = successor;
            successor = current;
            current = current.lChild;
        }
        if(successor != delNode.rChild) {
            successorParent.lChild = successor.rChild;
            successor.rChild = delNode.rChild;
        }
        return successor;
    }

    /**
     * Calls inOrder with the root of the tree
     */
    public void printInorder() {
        if (root == null) {
            System.out.println("Tree is empty");
        }
        else {
            inOrder(root);
        }
    }

    /**
     * traverses the tree inOrder (LNR) and prints each node
     * @param localRoot should be set to root by the caller
     */
    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.lChild);
            localRoot.printNode();
            inOrder(localRoot.rChild);
        }
    }

    /**
     * calls preOrder with root of tree
     */
    public void printPreorder() {
        if (root == null) {
            System.out.println("Tree is empty");
        }
        else {
            preOrder(root);
        }
    }
    /**
     * traverses the tree Preorder (NLR) and prints each node
     * @param localRoot should be set to root by the caller
     */
    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            localRoot.printNode();
            preOrder(localRoot.lChild);
            preOrder(localRoot.rChild);
        }
    }
    /**
     * calls postOrder using the root of the tree
     */
    public void printPostorder() {
        if (root == null) {
            System.out.println("Tree is empty");
        }
        else {
            postOrder(root);
        }
    }
    /**
     * traverses the tree Postorder(LRN) and prints each node
     * @param localRoot should be set to root by the caller
     */
    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.lChild);
            postOrder(localRoot.rChild);
            localRoot.printNode();
        }
    }

    /**
     * <p>
     *     Finds and prints the <code>c</code> states with the highest <b>DR</b> in descending order.
     * </p>
     * <p>
     *     Prints all states if there are fewer than <code>c</code> states in the tree.
     * </p>
     * @param c number of states to print
     */
    public void printBottomStates(int c) {
       largest = root;
       smallest = root;
       setLargest(root);
       setSmallest(root);
       for (int i = 0; i < c; i++){
           ceiling = largest;
           ceiling.printNode();
           if (smallest == largest) {
               break;
           }
           largest = smallest;
           findNextLargest(root);
       }
    }

    /**
     * <p>
     *     Finds and prints the <code>c</code> states with the lowest <b>DR</b> in ascending order.
     * </p>
     * <p>
     *     Prints all states if there are fewer than <code>c</code> states in the tree.
     * </p>
     * @param c number of states to print
     */
    public void printTopStates(int c) {
        largest = root;
        smallest = root;
        setLargest(root);
        setSmallest(root);
        for (int i = 0; i < c; i++) {
            floor = smallest;
            floor.printNode();
            if (smallest == largest) {
                break;
            }
            smallest = largest;
            findNextSmallest(root);
        }
    }

    /**
     * Sets the global variable 'smallest' to the node in the tree with the lowest dr
     * @param localRoot the root of a tree should be passed to this method from the caller
     */
    public void setSmallest(Node localRoot) {
        if (localRoot != null) {
            if (localRoot.deathRate < smallest.deathRate) {
                smallest = localRoot;
            }
            setSmallest(localRoot.lChild);
            setSmallest(localRoot.rChild);
        }

    }

    /**
     * Sets global variable 'largest' to the node in the tree with the lowest dr
     * @param localRoot should be set to root by the caller
     */
    public void setLargest(Node localRoot) {
        if (localRoot != null) {
            if (localRoot.deathRate > largest.deathRate) {
                largest = localRoot;
            }
            setLargest(localRoot.lChild);
            setLargest(localRoot.rChild);
        }
    }

    /**
     * finds the node with the lowest deathrate that is still higher than the "floor" node's deathrate.
     * local variable "smallest" will be set to the value found
     * @param localRoot should be set to root by the caller
     */
    public void findNextSmallest(Node localRoot) {
        if (localRoot != null) {
            // if current deathrate is the smallest so far
            if (localRoot.deathRate < smallest.deathRate) {
                // if the current deathrate is higher than the search "floor"
                if (localRoot.deathRate > floor.deathRate) {
                    // in case the float value comparison bugs out
                    if (!localRoot.stateName.equals(floor.stateName)) {
                        smallest = localRoot;
                    }
                }
            }
            findNextSmallest(localRoot.lChild);
            findNextSmallest(localRoot.rChild);
        }
    } // end method
    /**
     * finds the node with the highest deathrate that is still lower than the "ceiling" node's deathrate.
     * local variable "largest" will be set to the value found
     * @param localRoot should be set to root by the caller
     */
    public void findNextLargest(Node localRoot) {
        if (localRoot != null) {
            // if current deathrate is the largest so far
            if (localRoot.deathRate > largest.deathRate) {
                // if the current deathrate is lower than the search "ceiling"
                if (localRoot.deathRate < ceiling.deathRate) {
                    // in case the float value comparison bugs out
                    if (!localRoot.stateName.equals(ceiling.stateName)){
                        largest = localRoot;
                    }
                }
            }
            findNextLargest(localRoot.lChild);
            findNextLargest(localRoot.rChild);
        }
    }
}
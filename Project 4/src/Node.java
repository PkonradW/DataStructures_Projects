/**
 * <p>Data memebers will be:</p>
 * <p><code>stateName</code>: State's name</p>
 * <p><code>deathRate</code>: death rate of the state to two(2) decimal precisions</p>
 * <p><code>lChild</code>: the left child reference</p>
 * <p><code>rChild</code>: the right child reference</p>
 * <p>Includes method to print the node according to some format.</p>
 * @author Konrad Weakley
 * @version 11/19/2021
 */
public class Node {
    public String stateName;
    public double deathRate;
    public Node lChild;
    public Node rChild;

    /**
     * Method to print a node according to some format
     */
    public void printNode() {
        System.out.printf("     State: %s \n" +
                          "Death Rate: %.2f \n", stateName, deathRate);
        System.out.println("-----------------");
    }

}
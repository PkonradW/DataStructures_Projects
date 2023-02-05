/**
 * <p>
 *     Class implements a Stack of state objects and includes common methods associated with stacks, these methods include:
 * </p>
 * <p>  a. Constructor that creates the stack array based on a size provided.</p>
   <p>  b. A push method to push a State on the stack.</p>
   <p>  c. A pop method to pop a State off the stack and return it.</p>
   <p>  d. A printStack method to print the stack from top down without modifying the stack.</p>
   <p>  e. An isEmpty method that returns true if the stack is empty, false otherwise.</p>
   <p>  f. An isFull method that returns true if the stack is full, false otherwise.</p>

 */
public class Stack {
    private int stackSize;
    private State[] stateArray;
    private int top = -1;

    /**
     * constructor that constructs a Stack array constructingly
     * @param size Size of the Stack array
     */
    public Stack(int size) {
        this.stackSize = size;
        this.stateArray = new State[this.stackSize];
    }

    /**
     * Pushes State on top of the Stack
     * @param state State to be pushed onto the stack
     */
    public void push (State state) {
        this.stateArray[++top] = state;
    }

    /**
     * Pops State off of the stack
     * @return State object
     */
    public State pop() {
        return this.stateArray[top--];
    }

    /**
     * @return true if the top of the Stack is -1, false otherwise
     */
    public boolean isEmpty() {
        return (this.top == -1);
    }

    /**
     * Tests the value of "top" against the size of the Stack set by the constructor
     * @return true if Stack is full, false otherwise
     */
    public boolean isFull(){
        return (this.top >= this.stackSize - 1);
    }

    /**
     * Prints the contents of a State stack without deleting anything
     */
    public void printStack() {
        State s;
        System.out.println("Name           | MHI      | VCR      | CFR       | Case Rate | Death Rate");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = top; i >= 0; i--) {
            s = this.stateArray[i];
            System.out.format("%-15s| %-9d| %-9.2f| %-10.6f| %-10.2f| %-10.2f%n",
                    s.getName(),
                    s.getMedianIncome(),
                    s.getViolentRate(),
                    s.getCaseFatalityRate(),
                    s.getCaseRate(),
                    s.getDeathRate());
        }
    }

}

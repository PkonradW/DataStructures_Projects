/**
 * Class that implements a stack of state objects via a double-ended singly-linked list.  Class contains the following
 * methods:
 * <p>a.  Constructor that creates the stack.</p>
 *     <p> b. A push method to push a state on the stack.</p>
 *     <p> c. A pop method to pop a state off the stack and return it.</p>
 *     <p> d. A recursive printStack method to print the stack from top of the stack to bottom.</p>
 *     <p> e. An isEmpty method that returns true if the stack is empty, false otherwise</p>
 * @author Konrad Weakley
 * @version 10/23/2021
 */
public class Stack {
    public SingleLink first;
    public SingleLink last;

    /**
     * Constructor for a Stack that implements a stack of State objects using a double-ended
     * singly-linked list.
     */
    public Stack() {
        first = null;
        last = null;
    }

    /**
     *  Pushes a state onto the stack by creating a new first Link.
     *  <p>
     *  If the stack is empty, <code>last</code>
     *  will be set to <code>newLink</code>. Since the link passed to this method will become the new
     *  <code>first</code> link on the stack, the previous <code>first</code> is set to <code>newLink.next</code>
     *  </p>
     * @param s State to push onto the stack
     */
    public void push(State s) {
        SingleLink newSingleLink = new SingleLink(s);

        if (isEmpty()) {
            last = newSingleLink;
        }
        newSingleLink.next = first;
        first = newSingleLink;
    }

    /**
     * Pops the first link off of the stack and returns its state. Sets the previous second link
     * (what used to be <code>first.next</code>) to <code>first</code>.
     *
     * @return State in the link on top of the stack
     */
    public State pop() {
        State temp = first.state;
        if (first.next == null) {
            last = null;
        }
        first = first.next;
        return temp;
    }

    /**
     * prints a stack of States by recursively calling its self.
     * @param link starting point for the method
     */
    public void printStack(SingleLink link) {
        if (link == first) {
            System.out.println();
            System.out.println("Name           | MHI      | VCR      | CFR       | Case Rate | Death Rate");
            System.out.println("-------------------------------------------------------------------------");
        }
        link.printLink();
        if (link.next != null) {
            printStack(link.next);
        }
    }

    /**
     * Determines if Stack is empty by checking for the existence of a first link
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }


}
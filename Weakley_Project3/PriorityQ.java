/**
 * <p>
 *     Class implements a Priority Queue of state objects using a double-ended, doubly-linked list. The priority is
 *     based on COVID-19 Death Rate (DR) with lower DR states having higher priority. Class contains the following
 *     methods:
 * </p>
 * <p>1) A constructor </p>
 * <p>2) An insert method to insert a state into the priority queue</p>
 * <p>3) A remove method to remove the highest-priority state from the queue</p>
 * <p>4) An intervalDelete method which deletes all states in the queue within two values given by the caller</p>
 * <p>5) A printPriorityQ method that prints all the items in the queue by recursively calling its self</p>
 * <p>6) An isEmpty function that returns True if the queue is empty, false otherwise</p>
 * @author Konrad Weakley n01432685
 * @version 10/23/2021
 */
public class PriorityQ {
    public DoubleLink last;
    public DoubleLink first;

    /**
     * Constructor for a PriorityQ object. Sets first and last to null.
     */
    public PriorityQ() {
        first = null;
        last = null;
    }

    /**
     * Inserts a state into a sorted Priority Queue implemented via a Doubly-Linked Double-Ended list.
     * @param s state to be inserted into the list
     */
    public void insert(State s) {
        DoubleLink newLink = new DoubleLink(s);
        DoubleLink current = first;
        float nLDeathRate = newLink.state.getDeathRate();

        if (isEmpty()) {
            last = newLink;
            first = newLink;
            return;
        }
        float curDeathRate = current.state.getDeathRate();
        while (nLDeathRate > curDeathRate
                && current.next!=null) {
            current = current.next;
            curDeathRate = current.state.getDeathRate();
        }
        if (current == last
                && nLDeathRate > curDeathRate ) { // if priority is lower than the last
            newLink.next = null;
            current.next = newLink;
            newLink.previous = current;
            this.last = newLink;
        }
        else if (current == first) { // if priority is higher than first's
            newLink.previous = null;
            first.previous = newLink;
            newLink.next = first;
            this.first = newLink;
        }
        else { // if newlink is to be inserted anywhere but the beginning or end
            newLink.previous = current.previous;
            current.previous.next = newLink;
            newLink.next = current;
            current.previous = newLink;
        }
    }

    /**
     * Removes a state from the front of the Queue (lowest DR)
     * @return State with the lowest DR in the Queue
     */
    public State remove() {
        DoubleLink temp = first;
        if (first.next == null){
            last = null;
        }
        else {
            first.next.previous = null;
        }
        first = first.next;
        return temp.state;
    }

    /**
     * Deletes State objects from the priority queue with a DR between the two parameters inclusive.
     * @param low lowest state DR to be removed from queue
     * @param high highest state DR to be removed from queue
     * @return true if objects were found and deleted, false otherwise
     */
    public boolean intervalDelete(int low, int high) {
        DoubleLink lo = first;  // walker which follows the link with the lowest proiority to be deleted
        DoubleLink hi = last;   // walker which follows the link with the highest priority to be deleted

        while (lo.state.getDeathRate() < low) { // set low walker to lowest priority state to be deleted
            lo = lo.next;
            if (lo == null) return false;
        }
        while (hi.state.getDeathRate() > high) { // set high walker to highest priority state to be deleted
            hi = hi.previous;
            if (hi == null) return false;
        }
        if (hi == last) { // when deleting last link
            if (lo == first) { // if deleting the whole list
                first = null;
                last = null;
                return true;
            }
            lo.previous.next = null;
            last = lo.previous;
        }
        else {  // only if hi is not last
            hi.next.previous = lo.previous;
        }
        if (lo == first) { // when deleting first link
            if (hi == last) { // if deleting the whole list
                first = null;
                last = null;
                return true;
            }
            hi.next.previous = null;
            first = hi.next;
        } else {  // only if lo is not first
            lo.previous.next = hi.next;
        }
        return true;
    }
    /**
     * prints a Priority Queue of States by recursively calling its self.
     * @param link starting point for the method
     */
    public void printPriorityQueue(DoubleLink link) {
        if (link == first) {
            System.out.println();
            System.out.println("Name           | MHI      | VCR      | CFR       | Case Rate | Death Rate");
            System.out.println("-------------------------------------------------------------------------");
        }
        link.printLink();
        if (link.next != null) {
            printPriorityQueue(link.next);
        }

    }

    /**
     * Checks to see if <code>first</code> is null.
     * @return true if list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return first == null;
    }
} // end class

/**
<p> Class implements a priority queue of State objects sorted by COVID-19 Death Rate (DR) using an array.
 the state with the lowest DR has the highest priority, the one with the second-lowest DR has the second-
 highest priority, and so on<p>
 <p> The class supports the following methods</p>
<p>a. Constructor that creates the priority queue array based on a size provided.</p>
<p>b. An insert method to insert a State into the queue. </p>
<p>c. A remove method to remove a State from the queue and return it. </p>
<p>d. A printQueue method to print the priority queue from beginning to end of it without modifying the
 priority queue.</p>
<p>e. An isEmpty method that returns true if the priority queue is empty, false otherwise.</p>
<p>f. An isFull method that returns true if the priority queue is full, false otherwise.</p>
 */
public class PriorityQ {
    public int qSize;
    public State[] stateArray;
    public int nItems;

    /**
     * Constructor for a Priority Queue of State objects
     * @param size size of the Priority Queue
     */
    public PriorityQ(int size) {
        qSize = size;
        stateArray = new State[qSize];
        nItems = 0;
    }

    /**
     * Inserts a State object into the Queue such as to keep the Queue sorted by
     * COVID Death Rate ascending.
     * @param state Object to insert into the queue
     */
    public void insert (State state) {
        int i;

        if (nItems == 0) {
            stateArray[nItems++] = state;
        }
        else {
            for (i = nItems - 1; i>=0; i--) {
                if ( state.getDeathRate() > stateArray[i].getDeathRate() ) {
                    stateArray[i+1] = stateArray[i];
                }
                else {
                    break;
                }
            }
            stateArray[i+1] = state;
            nItems++;
        }
    }

    /**
     * removes the top item off of the priority queue and returns it to caller
     * @return item on top of priority queue
     */
    public State remove () {
        return stateArray[-- nItems];
    }

    /**
     * Gets the first item in the PriorityQ without deleting it
     * @return copy of State on top of queue
     */
    public State peek() {   // Do I really need this?
        return stateArray[nItems - 1];
    }
    /**
     * Prints the contents of a State stack without deleting anything
     */
    public void printQueue() {
        for (int i = nItems - 1; i >= 0; i--) {
            State s = stateArray[i];
            System.out.format("%-15s| %-9d| %-9.2f| %-10.6f| %-10.2f| %-10.2f%n",
                    s.getName(),
                    s.getMedianIncome(),
                    s.getViolentRate(),
                    s.getCaseFatalityRate(),
                    s.getCaseRate(),
                    s.getDeathRate());
        }
    }

    /**
     * Determines if Queue is empty using the nItems variable
     * @return True if empty
     */
    public boolean isEmpty() {
        return (nItems == 0);
    }

    /**
     * Determines if Queue is full by comparing nItems to qSize
     * @return True if full
     */
    public boolean isFull() {
        return (nItems == qSize);
    }

}

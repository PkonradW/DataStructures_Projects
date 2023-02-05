/**
 * Class defining a SingleLink object. Contains the references for the State contained in this link and the next links.
 * <p>Methods include a constructor and a method for printing the contents of a single link</p>
 * @author Konrad Weakley
 * @version 10/23/2021
 */
public class SingleLink {
    public State state;
    public SingleLink next;

    public SingleLink(State s) {
        state = s;
    }

    /**
     * formats and prints the state within a Link
     */
    public void printLink() {
        System.out.format("%-15s| %-9d| %-9.2f| %-10.6f| %-10.2f| %-10.2f%n",
                state.getName(),
                state.getMedianIncome(),
                state.getViolentRate(),
                state.getCaseFatalityRate(),
                state.getCaseRate(),
                state.getDeathRate());
    }
}

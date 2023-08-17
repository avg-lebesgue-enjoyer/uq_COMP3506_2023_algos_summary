/**
 * <p> This class stores nodes in an AlgoTree which represent classes of algorithms.
 * <p> For example, "Comparison sorts"
 */
public class AlgoTypeNode extends AlgoTreeNode {
    // MEMBER VARIABLES
    /** Type of algorithm described */
    private String typeOfAlgorithm;

    // BOILERPLATE METHODS
    /**
     * Constructs a new AlgoTypeNode
     * @param parent
     * @param typeOfAlgorithm
     */
    public AlgoTypeNode(AlgoTreeNode parent, String typeOfAlgorithm) {
        super(parent);
        this.typeOfAlgorithm = typeOfAlgorithm;
    }

    public String getTypeOfAlgorithm() {
        return typeOfAlgorithm;
    }

    public void setTypeOfAlgorithm(String typeOfAlgorithm) {
        this.typeOfAlgorithm = typeOfAlgorithm;
    }
}
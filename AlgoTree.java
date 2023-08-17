import java.io.Serializable;

/**
 * <p> Tree of algorithms and algorithm types.
 * @see AlgoTreeNode
 * @see AlgoTypeNode
 * @see AlgorithmNode
 */
public class AlgoTree implements Serializable {
    // MEMBER VARIABLES
    /** Root of this AlgoTree */
    private AlgoTreeNode root;

    // BOILERPLATE METHODS
    /**
     * Construct a new AlgoTree with no root.
     */
    public AlgoTree() {
        this.root = null;
    }

    public AlgoTreeNode getRoot() {
        return this.root;
    }

    public void setRoot(AlgoTreeNode root) {
        this.root = root;
    }

    // METHODS OF INTEREST
    /* TODO:
     *  - Method to print this AlgoTree in LaTeX
     *  - Method to get the sub-AlgoTree consisting only of methods called by a given DataStructureNode
     */
}

import java.io.Serializable;

/**
 * <p> Tree of data structures and data structure types.
 * @see DataTreeNode
 * @see DataTypeNode
 * @see DataStructureNode
 */
public class DataTree implements Serializable {
    // MEMBER VARIABLES
    /** Root of this DataTree */
    private DataTreeNode root;

    // BOILERPLATE METHODS
    /**
     * Construct a new DataTree with no root.
     */
    public DataTree() {
        this.root = null;
    }

    public DataTreeNode getRoot() {
        return this.root;
    }

    public void setRoot(DataTreeNode root) {
        this.root = root;
    }

    // METHODS OF INTEREST
    /* TODO:
     *  - Method to print this DataTree in LaTeX
     *  - Method to get the sub-DataTree consisting only of data structures that may call a given AlgorithmNode
     */
}

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents nodes in an AlgoTree.
 * See specific subclasses AlgoTypeNode and AlgorithmNode.
 * @see AlgoTypeNode
 * @see AlgorithmNode
 */
public class AlgoTreeNode {
    // MEMBER VARIABLES
    /** Parent of this AlgoTreeNode */
    private AlgoTreeNode parent;
    /** Children of this AlgoTreeNode */
    private Set<AlgoTreeNode> children;
    
    // BOILERPLATE METHODS
    /**
     * Construct new AlgoTreeNode with empty children and no parent.
     * 
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode() {
        this.parent = null;
        this.children = new LinkedHashSet<>();
    }

    /**
     * Construct new AlgoTreeNode with empty children.
     * @param parent parent of this AlgoTreeNode
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode(AlgoTreeNode parent) {
        this.parent = parent;
        this.children = new LinkedHashSet<>();
    }

    /**
     * Get parent of this AlgoTreeNode.
     * @return parent of this AlgoTreeNode
     */
    public AlgoTreeNode getParent() {
        return parent;
    }

    /**
     * Set parent of this AlgoTreeNode
     * @param parent new parent
     */
    public void setParent(AlgoTreeNode parent) {
        this.parent = parent;
    }

    /**
     * Get set of children of this AlgoTreeNode.
     * @return children of this AlgoTreeNode
     */
    public Set<AlgoTreeNode> getChildren() {
        return children;
    }

    // HELPER METHODS OF INTEREST
    /**
     * Get whether this AlgoTreeNode has any children
     * @return true iff this AlgoTreeNode has any children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
    // METHODS OF INTEREST
}

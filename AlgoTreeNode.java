import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents nodes in an AlgoTree.
 * See specific subclasses AlgoTypeNode and AlgorithmNode.
 * @see AlgoTypeNode
 * @see AlgorithmNode
 */
public class AlgoTreeNode implements Serializable {
    // MEMBER VARIABLES
    /** Parent of this AlgoTreeNode */
    private AlgoTreeNode parent;
    /** Children of this AlgoTreeNode */
    private Set<AlgoTreeNode> children;
    /** Indicates whether this AlgoTreeNode is the root of an AlgoTree */
    private boolean root;
    
    // BOILERPLATE METHODS
    /**
     * Construct new AlgoTreeNode with empty children and no parent.
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode() {
        this.parent = null;
        this.children = new LinkedHashSet<>();
        this.root = false;
    }

    /**
     * Construct new AlgoTreeNode with empty children.
     * @param parent parent of this AlgoTreeNode
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode(AlgoTreeNode parent) {
        this.parent = parent;
        this.children = new LinkedHashSet<>();
        this.root = false;
    }

    /**
     * Construct new root AlgoTreeNode with empty children and no parent.
     * @param root boolean whether this is a root
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode(boolean root) {
        this.parent = null;
        this.children = new LinkedHashSet<>();
        this.root = root;
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
    
    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
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

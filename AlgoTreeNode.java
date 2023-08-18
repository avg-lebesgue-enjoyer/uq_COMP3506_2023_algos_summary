import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
    private List<AlgoTreeNode> children;
    /** Indicates whether this AlgoTreeNode is the root of an AlgoTree */
    private boolean root;
    
    // BOILERPLATE METHODS
    /**
     * Construct new AlgoTreeNode with empty children and no parent.
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode() {
        this.parent = null;
        this.children = new LinkedList<>();
        this.root = false;
    }

    /**
     * Construct new AlgoTreeNode with empty children.
     * @param parent parent of this AlgoTreeNode
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode(AlgoTreeNode parent) {
        this.parent = parent;
        this.children = new LinkedList<>();
        this.root = false;
    }

    /**
     * Construct new root AlgoTreeNode with empty children and no parent.
     * @param root boolean whether this is a root
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode(boolean root) {
        this.parent = null;
        this.children = new LinkedList<>();
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
    public List<AlgoTreeNode> getChildren() {
        return children;
    }
    
    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    @Override
    public String toString() {
        if (this.root) {
            return "AlgoTreeNode ROOT";
        }
        return "AlgoTreeNode";
    }

    // HELPER METHODS
    /**
     * Get whether this AlgoTreeNode has any children
     * @return true iff this AlgoTreeNode has any children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Return the contents of this {@link AlgoTreeNode}, as a String.
     * @return contents of this node
     */
    public String echoContents() {
        return ""
            + "Root: " + root;
    }
}

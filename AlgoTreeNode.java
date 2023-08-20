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
    /** hyperref \ref{} and \label{} parameter */
    private String hyperref;
    
    // BOILERPLATE METHODS
    /**
     * Construct new AlgoTreeNode with empty children and no parent.
     * @return new AlgoTreeNode
     */
    public AlgoTreeNode() {
        this.parent = null;
        this.children = new LinkedList<>();
        this.root = false;
        this.hyperref = null;
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
        this.hyperref = null;
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
        this.hyperref = null;
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

    public String getHyperref() {
        return hyperref;
    }

    public void setHyperref(String hyperref) {
        this.hyperref = hyperref;
    }

    @Override
    public String toString() {
        if (this.root) {
            return "AlgoTreeNode ROOT";
        }
        return "AlgoTreeNode";
    }

    /**
     * @implNote Only checks the {@link AlgoTreeNode#root} parameter
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (root ? 1231 : 1237);
        return result;
    }

    /**
     * @implNote Only checks the {@link AlgoTreeNode#root} parameter
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlgoTreeNode other = (AlgoTreeNode) obj;
        if (root != other.root)
            return false;
        return true;
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
     * Get all descendants of this {@link AlgoTreeNode}.
     * @require This {@link AlgoTreeNode} is <b>not part of a cycle</b>. Otherwise,
     * this method <b>will infinitely loop</b>! This requirement should be met so
     * long as a <em>tree</em> is maintained.
     * @return {@link List} of (proper) descendants
     */
    public List<AlgoTreeNode> getDescendants() {
        List<AlgoTreeNode> descendants = new LinkedList<>();
        descendants.addAll(children); // Children are descendants
        for (AlgoTreeNode child : children) {
            descendants.addAll(child.getDescendants()); // So are their descendants
        }
        return descendants;
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

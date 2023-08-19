import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents nodes in a DataTree.
 * See specific subclasses DataTypeNode and DataStructureNode.
 * @see DataTypeNode
 * @see DataStructureNode
 */
public class DataTreeNode implements Serializable {
    // MEMBER VARIABLES
    /** Parent of this DataTreeNode */
    private DataTreeNode parent;
    /** Children of this DataTreeNode */
    private List<DataTreeNode> children;
    /** Marker for special root node */
    private boolean root;
    /** hyperref \ref{} and \label{} parameter */
    private String hyperref;

    // BOILERPLATE METHODS
    /**
     * Construct new DataTreeNode with no parent and no children.
     */
    public DataTreeNode() {
        this.parent = null;
        this.children = new LinkedList<>();
        this.root = false;
        this.hyperref = null;
    }

    /**
     * Construct new DataTreeNode which is a root.
     * @param root boolean true iff this node is a root
     */
    public DataTreeNode(boolean root) {
        this.parent = null;
        this.children = new LinkedList<>();
        this.root = root;
        this.hyperref = null;
    }
    
    /**
     * Construct new DataTreeNode with no children.
     * @param parent DataTreeNode parent of this DataTreeNode
     */
    public DataTreeNode(DataTreeNode parent) {
        this.parent = parent;
        this.children = new LinkedList<>();
        this.hyperref = null;
    }

    public DataTreeNode getParent() {
        return parent;
    }
    public void setParent(DataTreeNode parent) {
        this.parent = parent;
    }
    public List<DataTreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<DataTreeNode> children) {
        this.children = children;
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
            return "DataTreeNode ROOT";
        }
        return "DataTreeNode";
    }

    /**
     * @implNote Only checks {@link DataTreeNode#root} parameter.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (root ? 1231 : 1237);
        return result;
    }

    /**
     * @implNote Only checks {@link DataTreeNode#root} parameter.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataTreeNode other = (DataTreeNode) obj;
        if (root != other.root)
            return false;
        return true;
    }

    // HELPER METHODS
    /**
     * Get whether this DataTreeNode has any children.
     * @return true iff this DataTreeNode has any children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Return the contents of this {@link DataTreeNode}, as a String.
     * @return contents of this node
     */
    public String echoContents() {
        return ""
            + "Root: " + root;
    }
}

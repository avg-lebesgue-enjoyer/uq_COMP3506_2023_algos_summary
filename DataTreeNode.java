import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<DataTreeNode> children;
    /** Marker for special root node */
    private boolean root;

    // BOILERPLATE METHODS
    /**
     * Construct new DataTreeNode with no parent and no children.
     */
    public DataTreeNode() {
        this.parent = null;
        this.children = new LinkedHashSet<>();
        this.root = false;
    }

    /**
     * Construct new DataTreeNode which is a root.
     * @param root boolean true iff this node is a root
     */
    public DataTreeNode(boolean root) {
        this.parent = null;
        this.children = new LinkedHashSet<>();
        this.root = root;
    }
    
    /**
     * Construct new DataTreeNode with no children.
     * @param parent DataTreeNode parent of this DataTreeNode
     */
    public DataTreeNode(DataTreeNode parent) {
        this.parent = parent;
        this.children = new LinkedHashSet<>();
    }

    public DataTreeNode getParent() {
        return parent;
    }
    public void setParent(DataTreeNode parent) {
        this.parent = parent;
    }
    public Set<DataTreeNode> getChildren() {
        return children;
    }
    public void setChildren(Set<DataTreeNode> children) {
        this.children = children;
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
            return "DataTreeNode ROOT";
        }
        return "DataTreeNode";
            //"["
            //+ "DataTreeNode [parent=" + parent 
            //+ ", children=" + children.toString() 
            //+ ", root=" + root + "]";
    }

    // HELPER METHODS
    /**
     * Get whether this DataTreeNode has any children.
     * @return true iff this DataTreeNode has any children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
}

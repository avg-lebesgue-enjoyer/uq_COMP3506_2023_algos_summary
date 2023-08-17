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

    // BOILERPLATE METHODS
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

    // HELPER METHODS
    /**
     * Get whether this DataTreeNode has any children.
     * @return true iff this DataTreeNode has any children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
}

/**
 * <p> This class stores nodes in a DataTree which represent individual data structure ADTs.
 * <p> For example, "StaticSequence".
 * <p> Direct superclass, if any, should be determined using the parent DataTreeNode.
 */
public class DataStructureNode extends DataTreeNode {
    // MEMBER VARIABLES
    /** Name of this data structure */
    private String name;
    
    // BOILERPLATE METHODS
    /**
     * Construct new DataStructureNode with no super structure.
     * @param parent
     * @param name
     */
    public DataStructureNode(DataTreeNode parent, String name) {
        super(parent);
        this.name = name;
    }
    
}

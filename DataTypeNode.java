/**
 * <p> This class stores nodes in a DataTree which represent classes of data structures.
 * <p> For example, "General linear structure"
 */
public class DataTypeNode extends DataTreeNode {
    // MEMBER VARIABLES
    /** Type of data structure described */
    private String typeOfDataStructure;

    // BOILERPLATE METHODS
    /**
     * Constructs a new DataTypeNode
     * @param parent DataTreeNode parent node
     * @param typeOfDataStructure String description of data structure type
     */
    public DataTypeNode(DataTypeNode parent, String typeOfDataStructure) {
        super(parent);
        this.typeOfDataStructure = typeOfDataStructure;
    }

    public String getTypeOfDataStructure() {
        return typeOfDataStructure;
    }

    public void setTypeOfDataStructure(String typeOfDataStructure) {
        this.typeOfDataStructure = typeOfDataStructure;
    }
}

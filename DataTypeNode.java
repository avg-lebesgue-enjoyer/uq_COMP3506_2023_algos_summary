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
     * @param typeOfDataStructure String description of data structure type
     * @param hyperref String hyperref \ref{} and \label{} parameter
     */
    public DataTypeNode(String typeOfDataStructure, String hyperref) {
        super();
        this.typeOfDataStructure = typeOfDataStructure;
        super.setHyperref(hyperref);
    }
    /**
     * Constructs a new DataTypeNode.
     * @param parent DataTreeNode parent node
     * @param typeOfDataStructure String description of data structure type
     * @param hyperref String hyperref \ref{} and \label{} parameter
     */
    public DataTypeNode(DataTreeNode parent, String typeOfDataStructure, String hyperref) {
        super(parent);
        this.typeOfDataStructure = typeOfDataStructure;
        super.setHyperref(hyperref);;
    }

    public String getTypeOfDataStructure() {
        return typeOfDataStructure;
    }

    public void setTypeOfDataStructure(String typeOfDataStructure) {
        this.typeOfDataStructure = typeOfDataStructure;
    }

    @Override
    public String toString() {
        return "DataTypeNode [" + typeOfDataStructure + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((typeOfDataStructure == null) ? 0 : typeOfDataStructure.hashCode());
        result = prime * result + ((super.getHyperref() == null) ? 0 : super.getHyperref().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataTypeNode other = (DataTypeNode) obj;
        if (typeOfDataStructure == null) {
            if (other.typeOfDataStructure != null)
                return false;
        } else if (!typeOfDataStructure.equals(other.typeOfDataStructure))
            return false;
        if (super.getHyperref() == null) {
            if (other.getHyperref() != null)
                return false;
        } else if (!super.getHyperref().equals(other.getHyperref()))
            return false;
        return true;
    }
    
    /**
     * Return the contents of this {@link DataTypeNode}, as a String.
     * @return contents of this node
     */
    @Override
    public String echoContents() {
        return ""
            + "Class: " + typeOfDataStructure
            + "\nhyperref: " + super.getHyperref();
    }
}

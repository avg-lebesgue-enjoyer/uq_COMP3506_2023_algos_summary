/**
 * <p> This class stores nodes in a DataTree which represent individual data structure ADTs.
 * <p> For example, "StaticSequence".
 * <p> Direct superclass, if any, should be determined using the parent DataTreeNode.
 */
public class DataStructureNode extends DataTreeNode {
    // MEMBER VARIABLES
    /** Name of this data structure */
    private String name;
    /** hyperref \ref{} and \label{} parameter to data structure */
    private String hyperref;
    
    // BOILERPLATE METHODS
    /**
     * Construct new DataStructureNode.
     * @param parent {@link DataTreeNode} parent of this data structure
     * @param name {@link String} name of this data str
     * @param hyperref {@link String} hyperref \ref{} and \label{} parameter
     */
    public DataStructureNode(DataTreeNode parent, String name, String hyperref) {
        super(parent);
        this.name = name;
        this.hyperref = hyperref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHyperref() {
        return hyperref;
    }

    public void setHyperref(String hyperref) {
        this.hyperref = hyperref;
    }

    @Override
    public String toString() {
        return "DataStructureNode [" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((hyperref == null) ? 0 : hyperref.hashCode());
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
        DataStructureNode other = (DataStructureNode) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (hyperref == null) {
            if (other.hyperref != null)
                return false;
        } else if (!hyperref.equals(other.hyperref))
            return false;
        return true;
    }

    /**
     * Return the contents of this {@link DataStructureNode}, as a String.
     * @return contents of this node
     */
    @Override 
    public String echoContents() {
        return ""
            + "Name: " + name
            + "\nhyperref: " + hyperref
        ;
    }
}

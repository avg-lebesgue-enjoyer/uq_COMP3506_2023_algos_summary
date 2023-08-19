/**
 * <p> This class stores nodes in an AlgoTree which represent classes of algorithms.
 * <p> For example, "Comparison sorts"
 */
public class AlgoTypeNode extends AlgoTreeNode {
    // MEMBER VARIABLES
    /** Type of algorithm described */
    private String typeOfAlgorithm;

    // BOILERPLATE METHODS
    /**
     * Constructs a new AlgoTypeNode
     * @param parent
     * @param typeOfAlgorithm
     */
    public AlgoTypeNode(AlgoTreeNode parent, String typeOfAlgorithm) {
        super(parent);
        this.typeOfAlgorithm = typeOfAlgorithm;
    }

    public String getTypeOfAlgorithm() {
        return typeOfAlgorithm;
    }

    public void setTypeOfAlgorithm(String typeOfAlgorithm) {
        this.typeOfAlgorithm = typeOfAlgorithm;
    }
    
    @Override
    public String toString() {
        return "AlgoTypeNode [" + typeOfAlgorithm + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlgoTypeNode other = (AlgoTypeNode) obj;
        if (typeOfAlgorithm == null) {
            if (other.typeOfAlgorithm != null)
                return false;
        } else if (!typeOfAlgorithm.equals(other.typeOfAlgorithm))
            return false;
        return true;
    }

    /**
     * Return the contents of this {@link AlgoTypeNode}, as a String.
     * @return contents of this node
     */
    @Override
    public String echoContents() {
        return ""
            + "Class: " + typeOfAlgorithm;
    }
}

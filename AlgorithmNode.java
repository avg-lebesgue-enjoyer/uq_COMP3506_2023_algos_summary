/**
 * <p> This class stores nodes in an AlgoTree which represent individual algorithms.
 * <p> For example, "radix sort"
 */
public class AlgorithmNode extends AlgoTreeNode {
    // MEMBER VARIABLES
    /** Name of this algorithm */
    private String name;
    
    // BOILERPLATE METHODS
    /**
     * <p> Construct a new AlgorithmNode.
     * <p> Specify null for any "...Complexity" member variable if you do not wish to record it.
     * @param parent AlgoTreeNode
     * @param name String name of this algorithm
     * @param hyperref String \ref{} and \label{} parameter
     */
    public AlgorithmNode(AlgoTreeNode parent, String name, String hyperref) {
        super(parent);
        this.name = name;
        super.setHyperref(hyperref);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlgorithmNode [" + name + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((super.getHyperref() == null) ? 0 : super.getHyperref().hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        AlgorithmNode other = (AlgorithmNode) obj;
        if (super.getHyperref() == null) {
            if (other.getHyperref() != null)
                return false;
        } else if (!super.getHyperref().equals(other.getHyperref()))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Return the contents of this {@link AlgorithmNode}, as a String.
     * @return contents of this node
     */
    @Override 
    public String echoContents() {
        return ""
            + "Name: " + name
            + "\nhyperref: " + super.getHyperref()
        ;
    }
    
    // METHODS OF INTEREST
    /* TODO:
     *  - Method to get list of all data structures which call this method
     *  - Method to print ^^ in LaTeX
     *   ^^^ maybe those two methods go in DataTree?
     *   ^^^ Nope, they'll go in AlgoDataPrinter
     */
}

import java.io.Serializable;

/**
 * Represents a pairing between an algorithm and a data structure.
 */
public class AlgoDataPair implements Serializable {
    // MEMBER VARIABLES
    /** Paired algorithm */
    private AlgoTreeNode algorithm;
    /** Paired data structure */
    private DataTreeNode dataStructure;
    
    // BOILERPLATE METHODS
    /**
     * Construct new AlgoDataPair.
     * @param algorithm AlgoTreeNode to pair
     * @param dataStructure DataTreeNode to pair
     */
    public AlgoDataPair(AlgoTreeNode algorithm, DataTreeNode dataStructure) {
        this.algorithm = algorithm;
        this.dataStructure = dataStructure;
    }
    public AlgoTreeNode getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(AlgoTreeNode algorithm) {
        this.algorithm = algorithm;
    }
    public DataTreeNode getDataStructure() {
        return dataStructure;
    }
    public void setDataStructure(DataTreeNode dataStructure) {
        this.dataStructure = dataStructure;
    }

    @Override
    public String toString() {
        return "(" + algorithm.toString() + ", " 
            + dataStructure.toString() + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        System.out.println("<!> CHECKING EQUALITY");
        if (this == obj)
            return true;
        if (obj == null) {
            System.out.println("<!> null???");
            return false;
        }
        if (getClass() != obj.getClass()) {
            System.out.println("<!> classes diff??");
            return false;
        }
        AlgoDataPair other = (AlgoDataPair) obj;
        if (algorithm == null) {
            if (other.algorithm != null) {
                System.out.println("<!> null algo and non-null other algo");
                return false;
            }
        } else if (!algorithm.equals(other.algorithm)) {
            System.out.println("<!> non-null algo not equal to other algo");
            return false;
        }
        if (dataStructure == null) {
            if (other.dataStructure != null) {
                System.out.println("<!> null data and non-null other data");
                return false;
            }
        } else if (!dataStructure.equals(other.dataStructure)) {
            System.out.println("<!> non-null data not equal to other data");
            return false;
        }
        return true;
    }

    /*
    @Override
    public boolean equals(Object anObject) {
        if (! (anObject instanceof AlgoDataPair)) {
            return false;
        }
        AlgoDataPair aPair = (AlgoDataPair) anObject;
        return this.algorithm.equals(aPair.getAlgorithm())
                && this.dataStructure.equals(aPair.getDataStructure());
    }
    */

    
}

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
        result = prime * result + ((dataStructure == null) ? 0 : dataStructure.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlgoDataPair other = (AlgoDataPair) obj;
        if (algorithm == null) {
            if (other.algorithm != null)
                return false;
        } else if (!algorithm.equals(other.algorithm))
            return false;
        if (dataStructure == null) {
            if (other.dataStructure != null)
                return false;
        } else if (!dataStructure.equals(other.dataStructure))
            return false;
        return true;
    }
    
    
}

/**
 * Represents a pairing between an algorithm and a data structure.
 */
public class AlgoDataPair {
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
}

/**
 * <p> This class stores nodes in an AlgoTree which represent individual algorithms.
 * <p> For example, "radix sort"
 */
public class AlgorithmNode extends AlgoTreeNode {
    // MEMBER VARIABLES
    /** hyperref \ref{} and \label{} parameter to algorithm */
    private String hyperref;
    /** Name of this algorithm */
    private String name;
    /** Description of this algorithm (brief) */
    private String description;
    /** Best-case time complexity, in mathmode */
    private String bestTimeComplexity;
    /** Average-case time complexity, in mathmode */
    private String averageTimeComplexity;
    /** Worst-case time complexity, in mathmode */
    private String worstTimeComplexity;
    /** Best-case space complexity, in mathmode */
    private String bestSpaceComplexity;
    /** Average-case space complexity, in mathmode */
    private String averageSpaceComplexity;
    /** Worst-case space complexity, in mathmode */
    private String worstSpaceComplexity;
    
    // BOILERPLATE METHODS
    /**
     * <p> Construct a new AlgorithmNode.
     * <p> Specify null for any "...Complexity" member variable if you do not wish to record it.
     * @param parent AlgoTreeNode
     * @param hyperref String \ref{} and \label{} parameter
     * @param name String name of this algorithm
     * @param bestTimeComplexity String best-case time complexity, in mathmode
     * @param averageTimeComplexity String average-case time complexity, in mathmode
     * @param worstTimeComplexity String worst-case time complexity, in mathmode
     * @param bestSpaceComplexity String best-case space complexity, in mathmode
     * @param averageSpaceComplexity String average-case space complexity, in mathmode
     * @param worstSpaceComplexity String worst-case space complexity, in mathmode
     */
    public AlgorithmNode(AlgoTreeNode parent, String hyperref, String name, String description,
            String bestTimeComplexity, String averageTimeComplexity, String worstTimeComplexity, 
            String bestSpaceComplexity, String averageSpaceComplexity, String worstSpaceComplexity) {
        super(parent);
        this.hyperref = hyperref;
        this.name = name;
        this.description = description;
        this.bestTimeComplexity = bestTimeComplexity;
        this.averageTimeComplexity = averageTimeComplexity;
        this.worstTimeComplexity = worstTimeComplexity;
        this.bestSpaceComplexity = bestSpaceComplexity;
        this.averageSpaceComplexity = averageSpaceComplexity;
        this.worstSpaceComplexity = worstSpaceComplexity;
    }

    public String getHyperref() {
        return hyperref;
    }

    public void setHyperref(String hyperref) {
        this.hyperref = hyperref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBestTimeComplexity() {
        return bestTimeComplexity;
    }

    public void setBestTimeComplexity(String bestTimeComplexity) {
        this.bestTimeComplexity = bestTimeComplexity;
    }

    public String getAverageTimeComplexity() {
        return averageTimeComplexity;
    }

    public void setAverageTimeComplexity(String averageTimeComplexity) {
        this.averageTimeComplexity = averageTimeComplexity;
    }

    public String getWorstTimeComplexity() {
        return worstTimeComplexity;
    }

    public void setWorstTimeComplexity(String worstTimeComplexity) {
        this.worstTimeComplexity = worstTimeComplexity;
    }

    public String getBestSpaceComplexity() {
        return bestSpaceComplexity;
    }

    public void setBestSpaceComplexity(String bestSpaceComplexity) {
        this.bestSpaceComplexity = bestSpaceComplexity;
    }

    public String getAverageSpaceComplexity() {
        return averageSpaceComplexity;
    }

    public void setAverageSpaceComplexity(String averageSpaceComplexity) {
        this.averageSpaceComplexity = averageSpaceComplexity;
    }

    public String getWorstSpaceComplexity() {
        return worstSpaceComplexity;
    }

    public void setWorstSpaceComplexity(String worstSpaceComplexity) {
        this.worstSpaceComplexity = worstSpaceComplexity;
    }
    
    // METHODS OF INTEREST
    /* TODO:
     *  - Method to get list of all data structures which call this method
     *  - Method to print ^^ in LaTeX
     *   ^^^ maybe those two methods go in DataTree?
     */
}

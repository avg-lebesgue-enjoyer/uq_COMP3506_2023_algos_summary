import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents the pairing between algorithms and data structures.
 */
public class AlgoDataPairing implements Serializable {
    // MEMBER VARIABLES
    private Set<AlgoDataPair> pairs;

    // BOILERPLATE METHODS
    /*
     * Construct empty AlgoDataPairing.
     */
    public AlgoDataPairing() {
        this.pairs = new LinkedHashSet<>();
    }

    /**
     * Construct AlgoDataPairing from set of pairs.
     * @param pairs Set<AlgoDataPair> pairs to store in this structure.
     */
    public AlgoDataPairing(Set<AlgoDataPair> pairs) {
        this.pairs = pairs;
    }

    public Set<AlgoDataPair> getPairs() {
        return pairs;
    }

    public void setPairs(Set<AlgoDataPair> pairs) {
        this.pairs = pairs;
    }

    /**
     * Return a shallow copy. Does not copy internal AlgoDataPair options.
     * @return shallow copy of this AlgoDataPairing.
     */
    public AlgoDataPairing shallowCopy() {
        return new AlgoDataPairing(
            new LinkedHashSet<AlgoDataPair>(this.pairs)
        );
    }

    // METHODS OF INTEREST
    /**
     * <p> Return a new AlgoDataPairing in which for each pair (D, A) of a
     * data structure D and an algorithm A contained in this AlgoDataPairing,
     * <ul>
     *  <li>All subclasses of D are paired with A,</li>
     *  <li>All subalgorithms of A are paired with D,</li>
     *  <li>The same is true of the children.</li>
     * </ul>
     * <p> I kinda know what I mean by writing this.
     * <p> Useful for printing maximal data from only minimal data stored.
     * @param dataTree DataTree reference data tree
     * @param algoTree AlgoTree reference algo tree
     * @return new AlgoDataPairing with pairs filled in, as described above.
     * @throws UnsupportedOperationException because I haven't implemented this yet
     */
    public AlgoDataPairing fill(DataTree dataTree, AlgoTree algoTree) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Haven't implemented this yet!!");
    }

    /**
     * <p> Put (D, A) pairs into this AlgoDataPairing, for a fixed DataTreeNode D
     * and AlgoTreeNodes A.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void appendDataAlgo(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find data structure to associate from
        DataTreeNode source = getSourceData(dataTree.getRoot(), keyboard);
        if (source != null) {
            System.out.println("Selected node was " + source.toString());
        } else {
            System.out.println("No node was selected.");
        }
    }

    /**
     * Helper method to find
     * @param cursor {@link DataTreeNode} node to ask about
     * @param keyboard {@link java.util.Scanner} input scanner
     * @return selected {@link DataTreeNode}, or null if none was selected
     */
    public DataTreeNode getSourceData(DataTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Select " + cursor.toString() + "? (\"y\"/else)");
        String decision = keyboard.nextLine();
        // Select this one?
        if (decision.strip().equals("y")) {
            return cursor;
        }
        // Select one of its children?
        for (DataTreeNode child : cursor.getChildren()) {
            DataTreeNode trial = getSourceData(child, keyboard);
            // If this child, or one of its descendants, was selected, return it
            if (trial != null) {
                return trial;
            }
        }
        // Neither this node nor any of its descendants were selected.
        return null;
    }
}
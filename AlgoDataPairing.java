import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;
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

    // COMMON HELPER METHODS
    /**
     * Helper method to find a source DataTreeNode.
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

    /**
     * Helper method to find a source AlgoTreeNode.
     * @param cursor {@link AlgoTreeNode} node to ask about
     * @param keyboard {@link java.util.Scanner} input scanner
     * @return selected {@link AlgoTreeNode}, or null if none was selected
     */
    public AlgoTreeNode getSourceAlgo(AlgoTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Select " + cursor.toString() + "? (\"y\"/else)");
        String decision = keyboard.nextLine();
        // Select this one?
        if (decision.strip().equals("y")) {
            return cursor;
        }
        // Select one of its children?
        for (AlgoTreeNode child : cursor.getChildren()) {
            AlgoTreeNode trial = getSourceAlgo(child, keyboard);
            // If this child, or one of its descendants, was selected, return it
            if (trial != null) {
                return trial;
            }
        }
        // Neither this node nor any of its descendants were selected.
        return null;
    }

    // METHODS for appending pairs
    /**
     * <p> Put (D, A) pairs into this AlgoDataPairing, for a fixed DataTreeNode D
     * and AlgoTreeNodes A.
     * <p> Write only.
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
        if (source == null) {
            System.out.println("No data structure was selected.");
            return;
        }

        // Pair with algos
        appendAlgo(source, algoTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to append algos to internal set.
     * @param source {@link DataTreeNode} to pair with
     * @param cursor {@link AlgoTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void appendAlgo(DataTreeNode source, AlgoTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Append " + cursor.toString() + "? (\"y\"/else)");
        String decision = keyboard.nextLine();
        // Pair with this one?
        if (decision.strip().equals("y")) {
            pairs.add(new AlgoDataPair(cursor, source));
        }
        // Pair with one of its children?
        for (AlgoTreeNode child : cursor.getChildren()) {
            appendAlgo(source, child, keyboard);
        }
    }

    /**
     * <p> Put (D, A) pairs into this AlgoDataPairing, for a fixed AlgoTreeNode A
     * and DataTreeNodes D.
     * <p> Write only.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void appendAlgoData(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find algorithm to associate from
        AlgoTreeNode source = getSourceAlgo(algoTree.getRoot(), keyboard);
        if (source == null) {
            System.out.println("No algorithm was selected.");
            return;
        }

        // Pair with datas
        appendData(source, dataTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to append datas to internal set.
     * @param source {@link AlgoTreeNode} to pair with
     * @param cursor {@link DataTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void appendData(AlgoTreeNode source, DataTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Append " + cursor.toString() + "? (\"y\"/else)");
        String decision = keyboard.nextLine();
        // Pair with this one?
        if (decision.strip().equals("y")) {
            pairs.add(new AlgoDataPair(source, cursor));
        }
        // Pair with one of its children?
        for (DataTreeNode child : cursor.getChildren()) {
            appendData(source, child, keyboard);
        }
    }

    // METHODS for overriding pairs
    /**
     * <p> Override (D, A) pairs in this AlgoDataPairing, for a fixed DataTreeNode D
     * and AlgoTreeNodes A.
     * <p> Simultaneous write and delete.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void overrideDataAlgo(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find data structure to associate from
        DataTreeNode source = getSourceData(dataTree.getRoot(), keyboard);
        if (source == null) {
            System.out.println("No data structure was selected.");
            return;
        }

        // Override with algos
        overrideAlgo(source, algoTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to override algos in internal set.
     * @param source {@link DataTreeNode} to pair with
     * @param cursor {@link AlgoTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void overrideAlgo(DataTreeNode source, AlgoTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Include " + cursor.toString() + "? (\"y\"/\"n\")");
        boolean retry;
        do {
            retry = false;
            String decision = keyboard.nextLine();
            // Override with this one?
            if (decision.strip().equals("y")) {
                pairs.add(new AlgoDataPair(cursor, source));
            } else if (decision.strip().equals("n")) {
                pairs.remove(new AlgoDataPair(cursor, source));
            } else {
                retry = true;
            }
        } while (retry);
        // Override with one of its children?
        for (AlgoTreeNode child : cursor.getChildren()) {
            overrideAlgo(source, child, keyboard);
        }
    }

    /**
     * <p> Override (D, A) pairs in this AlgoDataPairing, for a fixed AlgoTreeNode A
     * and DataTreeNodes D.
     * <p> Simultaneous write and delete.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void overrideAlgoData(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find algorithm to associate from
        AlgoTreeNode source = getSourceAlgo(algoTree.getRoot(), keyboard);
        if (source == null) {
            System.out.println("No algorithm was selected.");
            return;
        }

        // Override with datas
        overrideData(source, dataTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to override datas in internal set.
     * @param source {@link AlgoTreeNode} to pair with
     * @param cursor {@link DataTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void overrideData(AlgoTreeNode source, DataTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("Include " + cursor.toString() + "? (\"y\"/\"n\")");
        boolean retry;
        do {
            retry = false;
            String decision = keyboard.nextLine();
            // Override with this one?
            if (decision.strip().equals("y")) {
                pairs.add(new AlgoDataPair(source, cursor));
            } else if (decision.strip().equals("n")) {
                pairs.remove(new AlgoDataPair(source, cursor));
            } else {
                retry = true;
            }
        } while (retry);
        // Override with one of its children?
        for (DataTreeNode child : cursor.getChildren()) {
            overrideData(source, child, keyboard);
        }
    }

    // METHODS for deleting (D, A) pairs for fixed D
    /**
     * <p> Delete (D, A) pairs from this AlgoDataPairing, for a fixed DataTreeNode D
     * and AlgoTreeNodes A.
     * <p> Delete only.
     * <p> BUG: Doesn't work at all.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void deleteDataAlgo(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find data structure to associate from
        DataTreeNode source = getSourceData(dataTree.getRoot(), keyboard);
        if (source == null) {
            System.out.println("No data structure was selected.");
            return;
        }

        // Delete algos
        deleteAlgo(source, algoTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to delete algos from internal set.
     * @param source {@link DataTreeNode} to pair with
     * @param cursor {@link AlgoTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void deleteAlgo(DataTreeNode source, AlgoTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        System.out.println("<!> " + (new AlgoDataPair(cursor, source)).toString());
        System.out.println("<!> " + this.pairs.toString());
        if (this.pairs.contains(new AlgoDataPair(cursor, source))) {
            System.out.println("Delete " + cursor.toString() + "? (\"y\"/\"n\")");
            String decision = keyboard.nextLine();
            // Delete this one?
            if (decision.strip().equals("n")) {
                pairs.remove(new AlgoDataPair(cursor, source));
            }
        } else {
            System.err.println("<!> why????");
            LinkedList<AlgoDataPair> asList = new LinkedList<>(this.pairs);
            System.out.println("<!> " + asList.toString());
            System.out.println("<!> " + Objects.equals(asList.getFirst(), new AlgoDataPair(cursor, source)));
        }
        // Delete one of its children?
        for (AlgoTreeNode child : cursor.getChildren()) {
            deleteAlgo(source, child, keyboard);
        }
    }

    /**
     * <p> Delete (D, A) pairs from this AlgoDataPairing, for a fixed AlgoTreeNode A
     * and DataTreeNodes D.
     * <p> Delete only.
     * <p> BUG: Doesn't work at all. {@see AlgoDataPairing#deleteAlgo(DataTreeNode, AlgoTreeNode, Scanner)}
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require {@link Main#dataTree} and {@link Main#algoTree} are non-null and rooted
     * @require this AlgoDataPairing is the one maintained by Main (i.e. is {@link Main#pairing}).
     */
    public void deleteAlgoData(Scanner keyboard) {
        // Grab dataTree and algoTree from Main
        DataTree dataTree = Main.getDataTree();
        AlgoTree algoTree = Main.getAlgoTree();

        // Find data structure to associate from
        AlgoTreeNode source = getSourceAlgo(algoTree.getRoot(), keyboard);
        if (source == null) {
            System.out.println("No algorithm was selected.");
            return;
        }

        // Delete datas
        deleteData(source, dataTree.getRoot(), keyboard); // recurse from head
    }

    /**
     * Helper method to delete algos from internal set.
     * @param source {@link AlgoTreeNode} to pair with
     * @param cursor {@link DataTreeNode} to potentially pair with
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private void deleteData(AlgoTreeNode source, DataTreeNode cursor, Scanner keyboard) {
        /* Preorder traversal. */
        if (this.pairs.contains(new AlgoDataPair(source, cursor))) {
            System.out.println("Delete " + cursor.toString() + "? (\"y\"/\"n\")");
            String decision = keyboard.nextLine();
            // Delete this one?
            if (decision.strip().equals("n")) {
                pairs.remove(new AlgoDataPair(source, cursor));
            }
        }
        // Delete one of its children?
        for (DataTreeNode child : cursor.getChildren()) {
            deleteData(source, child, keyboard);
        }
    }
}
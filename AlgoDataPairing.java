import java.io.Serializable;
import java.util.LinkedHashSet;
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
     * @param pairs {@link Set} of pairs to store in this structure.
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

    /**
     * Returns true iff this AlgoDataPairing contains an element {@code e}
     * such that {@code Objects.equals(o, e)}.
     * @param o Object to test
     * @return true iff this AlgoDataPairing contains an element 
     * {@code e} such that {@code Objects.equals(o, e)}
     */
    public boolean contains(Object o) {
        return this.pairs.contains(o);
    }

    // METHODS OF INTEREST
    /**
     * <p> Return a new {@link AlgoDataPairing} P in which for each pair (D, A) 
     * of a data structure D and an algorithm A contained in this AlgoDataPairing,
     * we have that for all D' sub-structure of D and for all A' sub-algorithm
     * of A, the pair (A', D') is in P, and nothing else is. D' = D and A' = A
     * are permissible.
     * <p> i.e. return a new {@link AlgoDataPairing} in which the set of pairs is
     * \set*{
     *      (A', D')
     *      \given
     *      \exists (A, D) \in (\text{this AlgoDataPairing}) : \,
     *      A' \text{ sub-algorithm } A
     *      \text{ and }
     *      D' \text{ sub-structure } D
     * }. In the product poset, this is the downwards closure.
     * <p> <em>Sub-</em>algos and <em>sub-</em>structures are determined according
     * to the given {@link AlgoTree} and the given {@link DataTree}.
     * <p> Useful for printing maximal data from only minimal data entered.
     * @implNote This implementation is <em>very</em> time-inefficient because I kinda 
     * don't want to bother optimising it. Ironic, given that this is code being written 
     * for COMP3506, I know.
     * @param algoTree {@link AlgoTree} reference algo tree
     * @param dataTree {@link DataTree} reference data tree
     * @return new {@link AlgoDataPairing} with pairs filled in, as described above.
     * @throws UnsupportedOperationException because I haven't implemented this yet
     * @see AlgoDataPairing#isCorrupt(AlgoTree, DataTree)
     */
    public AlgoDataPairing fill(AlgoTree algoTree, DataTree dataTree) throws UnsupportedOperationException {
        Set<AlgoDataPair> fullPairs = new LinkedHashSet<>();
        for (AlgoDataPair pair : this.pairs) {
            for (AlgoTreeNode algoDescendant : pair.getAlgorithm().getDescendants()) {
                for (DataTreeNode dataDescendant : pair.getDataStructure().getDescendants()) {
                    fullPairs.add(new AlgoDataPair(algoDescendant, dataDescendant));
                }
            }
        }
        fullPairs.addAll(this.pairs);
        return new AlgoDataPairing(fullPairs);
    }

    /**
     * <p> Tests whether this pairing is corrupt, relative to a provided
     * {@link AlgoTree} and a provided {@link DataTree}.
     * <p> A pairing P is corrupt relative to an {@link AlgoTree} A and a
     * {@link DataTree} D iff there is some pair (a, d) in P such that there
     * is some sub-algo a' of a (possibly a' = a) and some sub-data d' of d
     * (possibly d' = d) such that (a', d') is not in P.
     * <p> In essence, a pairing P is <em>not</em> corrupt iff. it is transitively
     * closed (under sub-structures).
     * <p> <em>Sub-</em>structures are determined relative to A and D.
     * <p> A corrupt pairing will not be well-represented by an AlgoDataPrinter.
     * <p> Corrupt pairings can be made non-corrupt using {@link AlgoDataPairing#fill(AlgoTree, 
     * DataTree)}.
     * @param algoTree {@link AlgoTree} to test against ("A")
     * @param dataTree {@link DataTree} to test against ("D")
     * @return true iff this pairing is corrupt relative to {@code algoTree}
     * and {@code dataTree}
     * @see AlgoDataPairing#fill(AlgoTree, DataTree)
     */
    public boolean isCorrupt(AlgoTree algoTree, DataTree dataTree) {
        throw new UnsupportedOperationException("Not yet implemented!");
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

    /**
     * Return a {@link java.util.Set} of {@link AlgoTreeNode} objects which
     * are related to a given {@link DataTreeNode}.
     * @param data {@link DataTreeNode} to get algos associated to
     * @return {@link java.util.Set} of {@link} AlgoTreeNode objects which
     * are paired with {@code data}
     */
    public Set<AlgoTreeNode> getAlgos(DataTreeNode data) {
        return new LinkedHashSet<>(
            this.getPairs().stream()
                .filter((pair) -> Objects.equals(pair.getDataStructure(), data))
                .map((pair) -> pair.getAlgorithm())
                .toList()
        );
    }

    /**
     * Return a {@link java.util.Set} of {@link DataTreeNode} objects which
     * are related to a given {@link AlgoTreeNode}.
     * @param algo {@link AlgoTreeNode} to get algos associated to
     * @return {@link java.util.Set} of {@link} DataTreeNode objects which
     * are paired with {@code algo}
     */
    public Set<DataTreeNode> getDatas(AlgoTreeNode algo) {
        return new LinkedHashSet<>(
            this.getPairs().stream()
                .filter((pair) -> Objects.equals(pair.getAlgorithm(), algo))
                .map((pair) -> pair.getDataStructure())
                .toList()
        );
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
        AlgoDataPair candidate = new AlgoDataPair(cursor, source);
        if (! this.pairs.contains(candidate)) {
            System.out.println("Append " + cursor.toString() + "? (\"y\"/else)");
            String decision = keyboard.nextLine();
            // Pair with this one?
            if (decision.strip().equals("y")) {
                pairs.add(candidate);
            }
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
        AlgoDataPair candidate = new AlgoDataPair(source, cursor);
        if (! this.pairs.contains(candidate)) {
            System.out.println("Append " + cursor.toString() + "? (\"y\"/else)");
            String decision = keyboard.nextLine();
            // Pair with this one?
            if (decision.strip().equals("y")) {
                pairs.add(candidate);
            }
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
        AlgoDataPair candidate = new AlgoDataPair(cursor, source);
        if (this.pairs.contains(candidate)) {
            System.out.println("Delete " + cursor.toString() + "? (\"y\"/\"n\")");
            String decision = keyboard.nextLine();
            // Delete this one?
            if (decision.strip().equals("y")) {
                pairs.remove(candidate);
            }
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
        AlgoDataPair candidate = new AlgoDataPair(source, cursor);
        if (this.pairs.contains(candidate)) {
            System.out.println("Delete " + cursor.toString() + "? (\"y\"/\"n\")");
            String decision = keyboard.nextLine();
            // Delete this one?
            if (decision.strip().equals("y")) {
                pairs.remove(candidate);
            }
        }
        // Delete one of its children?
        for (DataTreeNode child : cursor.getChildren()) {
            deleteData(source, child, keyboard);
        }
    }
}
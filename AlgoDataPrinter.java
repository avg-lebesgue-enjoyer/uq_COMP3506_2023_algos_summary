/**
 * Static class for printing lists of algos/datas associated with a data/algo.
 */
public class AlgoDataPrinter {
    /**
     * Print algorithms associated to a given data structure.
     * @param data DataTreeNode to print algos for
     * @param pairing AlgoDataPairing which associates algos and data structures
     * @return String human-readable prototype string.
     */
    public static String prototypePrintAlgos(DataTreeNode data, AlgoDataPairing pairing) {
        throw new UnsupportedOperationException("haven't implemented yet!");
    }

    /* TODO:
     *  - Print algorithms associated to a given data structure, in LaTeX.
     *  - Print ^^, in *preamble* LaTeX.
     *  - Print ^^, for all data structures, in *preamble* LaTeX.
     *  - sim. Algos -> Datas
     */

    /**
     * <p> Write the big funny command string that will go in the preamble.
     * @param algoTree {@link AlgoTree} to print based on
     * @param dataTree {@link DataTree} to print based on
     * @param pairing {@link AlgoDataPairing} to print based on
     * @return big funny command
     */
    public static String writeDataAlgos(AlgoTree algoTree, DataTree dataTree, AlgoDataPairing pairing) {
        StringBuilder out = new StringBuilder();
        out.append(
            "\\newcommand{\\dataprintalgos}[1]{"
            + "\n\t\\par \\textbf{\\color{magenta}{\\underline{Algorithms}}} this data structure(s) may utilise:"
        );
        // Traverse data tree
        traverseDataPrintAlgos(algoTree, dataTree.getRoot(), pairing, "\n\t", out);
        out.append("\n}");
        return out.toString();
    }

    /**
     * Helper method for {@link AlgoDataPrinter#writeDataAlgos(AlgoDataPairing)}
     * to traverse down dataTree.
     * @param algoTree {@link AlgoTree} to print based on
     * @param cursor {@link DataTreeNode} to print based on
     * @param pairing {@link AlgoDataPairing} to print based on
     * @param padding String to pad with
     * @param out {@link java.lang.StringBuilder} to append to
     */
    public static void traverseDataPrintAlgos(AlgoTree algoTree, DataTreeNode cursor, AlgoDataPairing pairing, 
            String padding, StringBuilder out) {
        /* Preorder traversal */
        // Work at this node
        out.append(
            padding + "\\ifthenelse{"
            + padding + "\t\\equal{"
            + padding + "\t\t#1"
            + padding + "\t}{"
            + padding + "\t\t\\ref{" + cursor.getHyperref() + "}"
        );

        // Recurse on children
    }
}

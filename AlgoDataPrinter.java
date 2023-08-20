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


    // TODO: Change the writing algorithm to look only at leaves!!
    /**
     * <p> Write the big funny command string that will go in the preamble.
     * @param algoTree {@link AlgoTree} to print based on
     * @param dataTree {@link DataTree} to print based on
     * @param pairing {@link AlgoDataPairing} to print based on
     * @return big funny command
     * @require pairing is <b>full</b>
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
     * @require pairing pairs cursor with at least one non-root AlgoTreeNode
     */
    public static void traverseDataPrintAlgos(AlgoTree algoTree, DataTreeNode cursor, AlgoDataPairing pairing, 
            String padding, StringBuilder out) {
        /* Preorder traversal. Could be any traversal. */
        /* Work at this node */
        if (cursor instanceof DataTypeNode || cursor instanceof DataStructureNode) {
            // \ifthenelse call
            out.append(
                padding + "\\ifthenelse{"
                + padding + "\t\\equal{"
                + padding + "\t\t#1"
                + padding + "\t}{"
                + padding + "\t\t\\ref{" + cursor.getHyperref() + "}"
                + padding + "\t}"
                + padding + "}{"
            );
            // List of algos
            String nextPadding = padding + "\t";
            out.append(
                nextPadding + "\\begin{itemize}[nosep]"
            );
            traverseAlgosPrint(
                algoTree.getRoot(), cursor, pairing, nextPadding + "\t", out
            );
            out.append(
                nextPadding + "\\end{itemize}"
            );
            // close \ifthenelse
            out.append(
                padding + "}{} % empty ELSE block"
            );
        }
        /* Recurse on children */
        for (DataTreeNode child : cursor.getChildren()) {
            traverseDataPrintAlgos(algoTree, child, pairing, padding, out);
        }
    }

    /**
     * <p> Print list of algos associated to {@link DataTreeNode} data.
     * <p> Call with {@code cursor} set to {@code algoTree.getHead()}.
     * @param cursor {@link AlgoTreeNode} position in algoTree
     * @param data {@link DataTreeNode} to associate with
     * @param pairing {@link AlgoDataPairing} to print based on
     * @param padding String to pad with
     * @param out {@link java.lang.StringBuilder} to append to
     */
    private static void traverseAlgosPrint(AlgoTreeNode cursor, DataTreeNode data, AlgoDataPairing pairing, String padding, StringBuilder out) {
        /* Preorder traversal */
        /* Work at this node */
        if (pairing.contains(new AlgoDataPair(cursor, data))) {
            if (cursor instanceof AlgoTypeNode) {
                AlgoTypeNode cursorReal = (AlgoTypeNode) cursor;
                out.append(
                    padding + "\\item "
                    + cursorReal.getTypeOfAlgorithm()
                    + ":"
                );
            } else if (cursor instanceof AlgorithmNode) {
                AlgorithmNode cursorReal = (AlgorithmNode) cursor;
                out.append(
                    padding + "\\item " 
                    + cursorReal.getName()
                    + " (algo \\ref{" + cursorReal.getHyperref() + "})"
                );
            }
        }
        /* Recurse on children */
        if (cursor.hasChildren()) {
            if (! cursor.isRoot()){
                out.append(
                    padding + "\\begin{itemize}[nosep]"
                );
            }
            for (AlgoTreeNode child : cursor.getChildren()) {
                traverseAlgosPrint(child, data, pairing, padding + "\t", out);
            }
            if (! cursor.isRoot()) {
                out.append(
                    padding + "\\end{itemize}"
                );
            }
        }
    }
}

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

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

    // METHODS FOR WRITING lists_preamble.tex: \dataprintalgos
    /**
     * <p> Return a string to go in the preamble, for {@code \dataprintalgos}.
     * @param algoTree {@link AlgoTree} to print relative to
     * @param dataTree {@link DataTree} to print relative to
     * @param pairing {@link AlgoDataPairing} to print relative to
     * @return String with LaTeX implementation of {@code \dataprintalgos}
     */
    public static String writeDA(AlgoTree algoTree, DataTree dataTree, AlgoDataPairing pairing) {
        StringBuilder out = new StringBuilder();
        String padding = "\n\t";
        out.append(
            "\\newcommand{\\dataprintalgos}[1]{"
            + padding + "\\par \\textbf{\\color{magenta}{\\underline{Algorithms}}} this data structure(s) may utilise:"
        );
        // NOTE: Yes, this is inefficent. No, I don't care.
        for (DataTreeNode data : dataTree.toSet()) { // DataTree.toSet() doesn't return the ROOT for this reason.
            writeGivenData(algoTree, data, pairing, out, padding);
        }
        out.append("\n}");
        return out.toString();
    }

    /**
     * <p> Write piece of preamble for given {@link DataTreeNode} data.
     * @param algoTree {@link AlgoTree} tree
     * @param data {@link DataTreeNode} given data structure (type)
     * @param pairing {@link ALgoDataPairing} pairing
     * @param out {@link java.lang.StringBuilder} to write to
     * @param padding String to pad with
     */
    private static void writeGivenData(AlgoTree algoTree, DataTreeNode data,
            AlgoDataPairing pairing, StringBuilder out, String padding) {
        out.append(
            padding + "\\ifthenelse{"
            + padding + "\t\\equal{"
            + padding + "\t\t#1"
            + padding + "\t}{"
            + padding + "\t\t\\ref{" + data.getHyperref() + "}"
            + padding + "\t}"
            + padding + "}{ % IF"
        );
        
        /* COLLECT ALGORITHMS TO PRINT */
        Set<AlgoTreeNode> algos = pairing.getAlgos(data);
        // Close under data node ancestors
        DataTreeNode ancestor = data.getParent();
        while (ancestor != null) {
            algos.addAll(pairing.getAlgos(ancestor));
            ancestor = ancestor.getParent();
        }

        /* PRINT ALGORITHMS */
        if (! algos.isEmpty()) {
            out.append(
                padding + "\t\\begin{itemize}[nosep]"
            );
            writeAlgosForGivenData(algoTree, data, algos, out, padding + "\t\t");
            out.append(
                padding + "\t\\end{itemize}"
            );
            
        } else {
            out.append(
                padding + "\t(none yet)"
            );
        }
        out.append(
            padding + "}{} % empty ELSE block"
        );
    }

    /**
     * <p> Write list of algos for a given data structure.
     * <p> Doesn't write surrounding \begin{itemize}[nosep] or the surrounding
     * \end{itemize}
     * @param algoTree {@link AlgoTree} to print relative to
     * @param data {@link DataTreeNode} to print relative to
     * @param algos {@link java.util.Set} of {@link AlgoTreeNode}s to print relative to
     * @param out {@link java.util.StringBuilder} to write to
     * @param padding String to pad with
     * @require {@code algos} is closed under data node ancestors
     */
    private static void writeAlgosForGivenData(AlgoTree algoTree, DataTreeNode data, 
            Set<AlgoTreeNode> algos, StringBuilder out, String padding) {
        // Close under ancestors of algorithms
        final Set<AlgoTreeNode> algosReal = AlgoTree.ancestorClosure(algos);
        
        // Write, now that things are closed
        algoTree.getRoot().getChildren().stream()
                .forEach( (child) -> 
                    writeAlgosForGivenDataHelper( // FIXME: finish implementing!
                        child, algosReal, out, padding
                    )
                );
    }

    /**
     * <p> Prints out {@code cursor} in the {@code itemize} environment.
     * <p> Helper method for {@link AlgoDataPrinter#writeAlgosForGivenDataHelper}
     * and {@link AlgoDataPrinter#writeAlgosForGivenDataHelperHelper}.
     * @param cursor {@link AlgoTreeNode} to print
     * @param out {@link StringBuilder} to write to
     * @param padding String to pad with
     */
    private static void writeCursorAlgo(AlgoTreeNode cursor, StringBuilder out, String padding) {
        if (cursor instanceof AlgoTypeNode) {
            AlgoTypeNode cursorReal = (AlgoTypeNode) cursor;
            out.append(
                padding + "\\item " + cursorReal.getTypeOfAlgorithm()
                + ":"
            );
        } else if (cursor instanceof AlgorithmNode) {
            AlgorithmNode cursorReal = (AlgorithmNode) cursor;
            out.append(
                padding + "\\item " + cursorReal.getName()
                + " (algo \\ref{" + cursorReal.getHyperref() + "})"
            );
        } else {
            System.err.println("Corrupt data!");
        }
    }

    /**
     * <p> Helper method for {@link AlgoDataPrinter#writeAlgosForGivenData(AlgoTree, 
     * DataTreeNode, AlgoDataPairing, StringBuilder, String)}.
     * @implNote Preorder traversal.
     * @require {@code algos} is closed under ancestors.
     * @param cursor {@link AlgoTreeNode} to print relative to
     * @param algos {@link java.util.Set} of {@link AlgoTreeNode} objects to include in print,
     * along with descendants of nodes in this set
     * @param out {@link java.util.StringBuilder} to write to
     * @param padding String to pad with
     */
    private static void writeAlgosForGivenDataHelper(AlgoTreeNode cursor, Set<AlgoTreeNode> algos, StringBuilder out, String padding) {
        /* Preorder traversal */
        /* Work at this node */
        if (! algos.contains(cursor)) {
            return;
        }
        // If we should be printing this node,
        writeCursorAlgo(cursor, out, padding);
        
        /* Recurse on children */
        if (cursor.hasChildren()) {
            out.append(
                padding + "\\begin{itemize}[nosep]"
            );
            if (AlgoTree.isLowest(cursor, algos)) {
                cursor.getChildren().stream()
                    .forEach( (child) ->
                        writeAlgosForGivenDataHelperHelper(
                            child, out, padding + "\t"
                        )
                    );
            } else {
                for (AlgoTreeNode child : cursor.getChildren()) {
                    writeAlgosForGivenDataHelper(child, algos, out, padding + "\t");
                }
            }
            out.append(
                padding + "\\end{itemize}"
            );
        }
    }

    /**
     * <p> Prints all descendants of {@code cursor} to {@code out} with {@code padding}, along with {@code cursor}
     * itself.
     * <p> Method is named terribly because I thought it was funny.
     * @param cursor {@link AlgoTreeNode} to print descendants of
     * @param out {@link StringBuilder} to write to
     * @param padding String to pad with
     */
    private static void writeAlgosForGivenDataHelperHelper(AlgoTreeNode cursor, StringBuilder out, String padding) {
        /* Preorder traversal */
        /* Work at this node */
        writeCursorAlgo(cursor, out, padding);
        /* Recurse on children */
        if (cursor.hasChildren()) {
            out.append(
                padding + "\\begin{itemize}[nosep]"
            );
            for (AlgoTreeNode child : cursor.getChildren()) {
                writeAlgosForGivenDataHelperHelper(child, out, padding + "\t");
            }
            out.append(
                padding + "\\end{itemize}"
            );
        }
    }
    
    // OLDER METHODS
    // TODO: Change the writing algorithm to look only at leaves!!
    public static String writeDatasAlgos(AlgoTree algoTree, DataTree dataTree, AlgoDataPairing pairing) {
        // Write the stuff
        StringBuilder out = new StringBuilder();
        out.append(
            "\\newcommand{\\dataprintalgos}[1]{"
            + "\n\t\\par \\textbf{\\color{magenta}{\\underline{Algorithms}}} this data structure(s) may utilise:"
        );
        traverseDataTreeWriteAlgosList(algoTree, dataTree.getRoot(), pairing, out, "\n\t");
        out.append("\n}");
        return out.toString();
    }

    public static void traverseDataTreeWriteAlgosList(AlgoTree algoTree, DataTreeNode cursor, 
            AlgoDataPairing pairing, StringBuilder out, String padding) {
        /* Preorder traversal, though any traversal is fine. */
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
            // FIXME: print algos list
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

    public static void writeDataAlgos(AlgoTree algoTree, DataTreeNode data, AlgoDataPairing pairing, StringBuilder out, String padding) {
        /* Get set (list) of algos associated by pairing */
        Collection<AlgoTreeNode> algos = pairing.getPairs().stream()
                .filter(
                    (pair) -> Objects.equals(pair.getDataStructure(), data)
                ).map(
                    (pair) -> pair.getAlgorithm()
                ).toList();
        algos = upwardsClosureAlgos(algoTree, algos);
        traverseAlgosWrite(algoTree.getRoot(), algos, out, padding);
    }

    public static Collection<AlgoTreeNode> upwardsClosureAlgos(AlgoTree algoTree, Collection<AlgoTreeNode> algos) {
        Set<AlgoTreeNode> closed = new LinkedHashSet<>();
        LinkedList<AlgoTreeNode> path = new LinkedList<>();
        traverseCloseAlgos(algoTree.getRoot(), algos, closed, path);
        return closed;
    }

    public static void traverseCloseAlgos(AlgoTreeNode cursor, Collection<AlgoTreeNode> algos, Collection<AlgoTreeNode> closed, LinkedList<AlgoTreeNode> path) {
        /* Preorder traversal with path */
        path.addFirst(cursor);
        /* Work at this node */
        if (algos.contains(cursor)) {
            // close upwards from cursor. Exit early if we start hitting things we've already closed.
            int index = 0;
            while (index < path.size() && (! closed.contains(path.get(index)))) {
                closed.add(path.get(index));
                index++;
            }
        }
        /* Recurse on children */
        for (AlgoTreeNode child : cursor.getChildren()) {
            traverseCloseAlgos(child, algos, closed, path);
        }
        path.remove(cursor);
    }

    public static void traverseAlgosWrite(AlgoTreeNode cursor, Collection<AlgoTreeNode> algos, StringBuilder out, String padding) {
        /* Preorder traversal */
        /* Work at this node */
        if (! algos.contains(cursor)) {
            return;
        } 
        // FIXME: I gave up writing this
        /*else if (isMinimal(cursor, algos)) {
            writeDescendants(cursor, padding);
        } else {
            if (cursor instanceof AlgoTypeNode) {
                AlgoTypeNode cursorReal = (AlgoTypeNode) cursor;
                out.append(
                    padding + cursorReal.getTypeOfAlgorithm() + ":"
                );
            }
        }
        */
    }
    


    // MUCH OLDER METHODS
    /**
     * <p> Write the big funny command string that will go in the preamble.
     * BUG: Doesn't work unless things are closed off properly.
     * @param algoTree {@link AlgoTree} to print based on
     * @param dataTree {@link DataTree} to print based on
     * @param pairing {@link AlgoDataPairing} to print based on
     * @return big funny command
     * @require pairing is <b>full</b>
     */
    public static String oldwriteDataAlgos(AlgoTree algoTree, DataTree dataTree, AlgoDataPairing pairing) {
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

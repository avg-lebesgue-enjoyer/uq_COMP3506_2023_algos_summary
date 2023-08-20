import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * <p> Tree of algorithms and algorithm types.
 * @see AlgoTreeNode
 * @see AlgoTypeNode
 * @see AlgorithmNode
 */
public class AlgoTree implements Serializable {
    // STATIC VARIABLES
    private static String[] defaultBrowseCommandsArray = {
        "die", // exit browse
        "echo_node_data", // print node data to console
        "print_subtree", // print subtree from this node
        "add_child" // add child node
    };
    private static String[] typeBrowseCommandsArray = {};
    private static String[] algorithmBrowseCommandsArray = {};
    private static String[] internalBrowseCommandsArray = {
        "step_down"
    };
    private static String[] rootBrowseCommandsArray = {
        "REROOT"
    };
    private static String[] nonRootBrowseCommandsArray = {
        "step_up"
    };
    /** Browse commands for any node */
    private static List<String> defaultBrowseCommands = Arrays.asList(defaultBrowseCommandsArray);
    /** Additional browse commands for any AlgoTypeNode */
    private static List<String> typeBrowseCommands = Arrays.asList(typeBrowseCommandsArray);
    /** Additional browse commands for any AlgorithmNode */
    private static List<String> algorithmBrowseCommands = Arrays.asList(algorithmBrowseCommandsArray);
    /** Additional browse commands for any internal node */
    private static List<String> internalBrowseCommands = Arrays.asList(internalBrowseCommandsArray);
    /** Additional browse commands for the root */
    private static List<String> rootBrowseCommands = Arrays.asList(rootBrowseCommandsArray);
    /** Additional browse commands for non-root elements */
    private static List<String> nonRootBrowseCommands = Arrays.asList(nonRootBrowseCommandsArray);

    // MEMBER VARIABLES
    /** Root of this AlgoTree */
    private AlgoTreeNode root;
    /** Cursor when browsing this AlgoTree */
    private AlgoTreeNode cursor;

    // BOILERPLATE METHODS
    /**
     * Construct a new AlgoTree with no root.
     */
    public AlgoTree() {
        this.root = null;
    }

    public AlgoTreeNode getRoot() {
        return this.root;
    }

    public void setRoot(AlgoTreeNode root) {
        this.root = root;
    }

    // METHODS OF INTEREST
    /* TODO:
     *  - Method to print this AlgoTree in LaTeX
     *  - Method to get the sub-AlgoTree consisting only of methods called by a given DataStructureNode
     */

    /**
     * <p> <b>REROOT THIS TREE.</b>
     * <p> Do not use unless confident!
     * @return true iff rerooting was successful
     */
    public boolean reRoot() {
        this.setRoot(new AlgoTreeNode(true));
        return true;
    }

    /**
     * <p> Browse this AlgoTree.
     * <p> I hate myself for not making a common AlgoDataTree, instead of splitting into
     * an AlgoTree and a DataTree like I have. It was a <em>terrible</em> design decision
     * which has lead to approx. <em>350 lines of duplicated code</em>.
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    public void browse(Scanner keyboard) {
        System.out.println("==== Browsing AlgoTree ====");
        this.cursor = this.getRoot();
        if (this.cursor == null) {
            System.err.println("Cannot browse a tree with no root!");
            return;
        }
        List<String> commands;
        String command;
        while (true) {
            commands = this.getBrowseCommands();
            printBrowseCommands(commands);
            command = keyboard.nextLine();
            if (! commands.contains(command)) {
                System.out.println("Invalid command.");
            } else {
                // Can now assume that command is a valid command on this node
                switch (command) {
                    case "die":
                        System.out.println("~~~~ finished browsing AlgoTree");
                        return;
                    case "echo_node_data":
                        System.out.println(cursor.echoContents());
                        break;
                    case "print_subtree":
                        System.out.println(">> Subtree from here is:");
                        preorderTraversal(
                                cursor, 
                                (node, padding) -> System.out.println(padding + node.toString()), 
                                ""
                        );
                        System.out.println("<< end subtree");
                        break;
                    case "add_child":
                        addChild(keyboard);
                        break;
                    case "step_down":
                        stepDown(keyboard);
                        break;
                    case "REROOT":
                        System.out.println(
                            "Are you sure you would like to reroot?"
                            + "\n\tEnter \"y\" to confirm"
                            + "\n\tEnter any other string to cancel"
                        );
                        if (keyboard.nextLine() == "y") {
                            this.reRoot();
                            System.out.println("Tree re-rooted (rip lol).");
                        } else {
                            System.out.println("Operation aborted.");
                        }
                        break;
                    case "step_up":
                        cursor = cursor.getParent();
                        break;
                    default:
                        System.err.println("I've got a bug");
                }
            }
        }
    }

    /**
     * <p> Helper method for getting browsing commands.
     * <p> I hate myself for not making a generic 'TreeOfThings' class.
     * @return List<String> of commands
     * @see AlgoTree#browse()
     * @see AlgoTree#printBrowseCommands()
     */
    private List<String> getBrowseCommands() {
        // Default commands
        List<String> browseCommands = new LinkedList<>(defaultBrowseCommands);
        // Node subclasses
        if (cursor instanceof AlgoTypeNode) {
            browseCommands.addAll(typeBrowseCommands);
        } else if (cursor instanceof AlgorithmNode) {
            browseCommands.addAll(algorithmBrowseCommands);
        }
        // Internal node
        if (cursor.hasChildren()) {
            browseCommands.addAll(internalBrowseCommands);
        }
        // The root of this tree (not just any "root"!)
        if (cursor == this.root) {
            browseCommands.addAll(rootBrowseCommands);
        } else { // Not the root of this tree
            browseCommands.addAll(nonRootBrowseCommands);
        }
        return browseCommands;
    }

    /**
     * Helper method for printing browsing commands.
     * @param browseCommands List<String> commands to print
     * @see AlgoTree#browse()
     * @see AlgoTree#getBrowseCommands()
     */
    private void printBrowseCommands(List<String> browseCommands) {
        String printMe = "Please enter one of the following commands:";
        for (String command : browseCommands) {
            printMe += "\n\t\"" + command + "\"";
        }
        System.out.println(printMe);
    }

    /**
     * Preorder traversal from a node.
     * @param node AlgoTreeNode for traversal
     * @param traversalFunction TraversalFunction to apply at each node
     * @param padding String for printing subtree
     */
    private void preorderTraversal(AlgoTreeNode node, AlgoTraversalFunction traversalFunction, String padding) {
        traversalFunction.apply(node, padding);
        for (AlgoTreeNode child : node.getChildren()) {
            preorderTraversal(child, traversalFunction, padding + "|\t");
        }
    }

    /**
     * Helper method for adding a child under the cursor.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @see AlgoTree#browse(Scanner)
     */
    private void addChild(Scanner keyboard) {
        String theClass;
        boolean retry;
        // CLASS
        do {
            retry = false;
            // Enter class
            System.out.println(
                "Please enter one of the following kinds of nodes:"
                + "\n\t\"AlgoTypeNode\" (classes of algorithms; e.g. Sorting Algorithm)"
                + "\n\t\"AlgorithmNode\" (individual algorithm; e.g. Bubble Sort)"
            );
            theClass = keyboard.nextLine();
            switch (theClass) {
                // Admissible inputs
                case "AlgoTypeNode":
                case "AlgorithmNode":
                    break;
                default:
                    System.out.println("Invalid input");
                    retry = true;
            }
        } while (retry);
        String hyperref;
        switch (theClass) {
            case "AlgoTypeNode":
                // Request details
                System.out.println(
                    "Enter the following details, separated by newline characters:"
                    + "\n\tName of type of algorithm, as a string (e.g. Sort)"
                    + "\n\tHyperref \\ref{} and \\label{} parameter, as a string (e.g. sec:sort)"
                );
                String typeOfAlgorithm = keyboard.nextLine();
                hyperref = keyboard.nextLine();
                // Spawn child
                cursor.getChildren().add(new AlgoTypeNode(
                    cursor, typeOfAlgorithm, hyperref
                ));
                break;
            case "AlgorithmNode":
                // Request details
                System.out.println(
                    "Enter the following details, separated by newline characters:"
                    + "\n\tName of algorithm, as a string (e.g. Bubble Sort)"
                    + "\n\tHyperref \\ref{} and \\label{} parameter, as a string (e.g. alg:bubble_sort)"
                );
                String name = keyboard.nextLine();
                hyperref = keyboard.nextLine();
                // Spawn child
                cursor.getChildren().add(new AlgorithmNode(
                    cursor, name, hyperref
                ));
                break;
            default:
                System.err.println("I've got a bug.");
        }
    }

    /**
     * Helper method for stepping down to a child.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @require <ul>
     * <li>this.cursor is pointing to an internal node</li>
     * </ul>
     * @see AlgoTree#browse(Scanner)
     */
    private void stepDown(Scanner keyboard) {
        // Request child index
        String printMe = "Select one of the following children:";
        int index = 0;
        for (AlgoTreeNode child : cursor.getChildren()) {
            printMe += "\n\t" + index + " : " + child.toString();
            index++;
        }
        printMe += "\nEnter one of the integer indices listed above:";
        System.out.println(printMe);
        // Read requested index
        boolean retry;
        do {
            retry = false;
            try {
                index = Integer.parseInt(keyboard.nextLine());
                if (index < 0 || index >= cursor.getChildren().size()) {
                    retry = true;
                }
            } catch (NumberFormatException nfe) {
                retry = true;
            }
        } while (retry); // index successfully read
        cursor = cursor.getChildren().get(index);
    }
}

/**
 * Function to apply during tree traversal.
 */
abstract interface AlgoTraversalFunction {
    public void apply(AlgoTreeNode dataTreeNode, String padding);
}

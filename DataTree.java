import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * <p> Tree of data structures and data structure types.
 * @see DataTreeNode
 * @see DataTypeNode
 * @see DataStructureNode
 */
public class DataTree implements Serializable {
    // STATIC VARIABLES
    private static String[] defaultBrowseCommandsArray = {
        "die", // exit browse
        "echo_node_data", // print node data to console
        "print_subtree", // print subtree from this node
        "add_child" // add child node
    };
    private static String[] typeBrowseCommandsArray = {};
    private static String[] structureBrowseCommandsArray = {};
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
    /** Additional browse commands for any DataTypeNode */
    private static List<String> typeBrowseCommands = Arrays.asList(typeBrowseCommandsArray);
    /** Additional browse commands for any DataStructureNode */
    private static List<String> structureBrowseCommands = Arrays.asList(structureBrowseCommandsArray);
    /** Additional browse commands for any internal node */
    private static List<String> internalBrowseCommands = Arrays.asList(internalBrowseCommandsArray);
    /** Additional browse commands for the root */
    private static List<String> rootBrowseCommands = Arrays.asList(rootBrowseCommandsArray);
    /** Additional browse commands for non-root elements */
    private static List<String> nonRootBrowseCommands = Arrays.asList(nonRootBrowseCommandsArray);

    // MEMBER VARIABLES
    /** Root of this DataTree */
    private DataTreeNode root;
    /** Cursor when browsing this DataTree */
    private DataTreeNode cursor;

    // BOILERPLATE METHODS
    /**
     * Construct a new DataTree with no root.
     */
    public DataTree() {
        this.root = null;
        this.cursor = null;
    }

    public DataTreeNode getRoot() {
        return this.root;
    }

    public void setRoot(DataTreeNode root) {
        this.root = root;
    }

    /**
     * <p> <b>REROOT THIS TREE.</b>
     * <p> Do not use unless confident!
     * @return true iff rerooting was successful
     */
    public boolean reRoot() {
        this.setRoot(new DataTreeNode(true));
        return true;
    }

    // METHODS OF INTEREST
    /* TODO:
     *  - Method to print this DataTree in LaTeX
     *  - Method to get the sub-DataTree consisting only of data structures that may call a given AlgorithmNode
     */

    /**
     * Browse this DataTree.
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    public void browse(Scanner keyboard) {
        System.out.println("==== Browsing DataTree ====");
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
                        System.out.println("~~~~ finished browsing DataTree");
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
     * Helper method for getting browsing commands.
     * @return List<String> of commands
     * @see DataTree#browse()
     * @see DataTree#printBrowseCommands()
     */
    private List<String> getBrowseCommands() {
        // Default commands
        List<String> browseCommands = new LinkedList<>(defaultBrowseCommands);
        // Node subclasses
        if (cursor instanceof DataTypeNode) {
            browseCommands.addAll(typeBrowseCommands);
        } else if (cursor instanceof DataStructureNode) {
            browseCommands.addAll(structureBrowseCommands);
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
     * @see DataTree#browse()
     * @see DataTree#getBrowseCommands()
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
     * @param node DataTreeNode for traversal
     * @param traversalFunction TraversalFunction to apply at each node
     * @param padding String for printing subtree
     */
    private void preorderTraversal(DataTreeNode node, DataTraversalFunction traversalFunction, String padding) {
        traversalFunction.apply(node, padding);
        for (DataTreeNode child : node.getChildren()) {
            preorderTraversal(child, traversalFunction, padding + "|\t");
        }
    }

    /**
     * Helper method for adding a child under the cursor.
     * @param keyboard {@link java.util.Scanner} input scanner
     * @see DataTree#browse(Scanner)
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
                + "\n\t\"DataTypeNode\" (classes of data structures; e.g. General Linear Structure)"
                + "\n\t\"DataStructureNode\" (individual data structure ADTs; e.g. StaticSequence)"
            );
            theClass = keyboard.nextLine();
            switch (theClass) {
                // Admissible inputs
                case "DataTypeNode":
                case "DataStructureNode":
                    break;
                default:
                    System.out.println("Invalid input");
                    retry = true;
            }
        } while (retry);
        String hyperref;
        switch (theClass) {
            case "DataTypeNode":
                // Request details
                System.out.println(
                    "Enter the following details, separated by newline characters:"
                    + "\n\tName of type of data structure, as a string (e.g. General Linear Structure)"
                    + "\n\tHyperref \\ref{} and \\label{} parameter, as a string (e.g. adt:general_linear_structure)"
                );
                String typeOfStructure = keyboard.nextLine();
                hyperref = keyboard.nextLine();
                // Spawn child
                cursor.getChildren().add(new DataTypeNode(
                    cursor, typeOfStructure, hyperref
                ));
                break;
            case "DataStructureNode":
                // Request details
                System.out.println(
                    "Enter the following details, separated by newline characters:"
                    + "\n\tName of data structure, as a string (e.g. StaticSequence)"
                    + "\n\tHyperref \\ref{} and \\label{} parameter, as a string (e.g. adt:static_sequence)"
                );
                String name = keyboard.nextLine();
                hyperref = keyboard.nextLine();
                // Spawn child
                cursor.getChildren().add(new DataStructureNode(
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
     * @see DataTree#browse(Scanner)
     */
    private void stepDown(Scanner keyboard) {
        // Request child index
        String printMe = "Select one of the following children:";
        int index = 0;
        for (DataTreeNode child : cursor.getChildren()) {
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

    /**
     * <p> Return a Set<DataTreeNode> of all nodes stored in this DataTree
     * <b>except</b> the root
     * @return {@link java.util.Set} of all nodes except the root
     */
    public Set<DataTreeNode> toSet() {
        Set<DataTreeNode> nodes = new LinkedHashSet<>();
        this.getRoot().getChildren().stream()
            .forEach((child) -> toSetHelper(child, nodes));
        return nodes;
    }

    /**
     * Prorder traversal to chuck stuff in the set
     * @param cursor
     * @param nodes
     * @see DataTree#toSet()
     */
    private void toSetHelper(DataTreeNode cursor, Set<DataTreeNode> nodes) {
        nodes.add(cursor);
        cursor.getChildren().stream()
            .forEach((child) -> toSetHelper(child, nodes));
    }
}

/**
 * Function to apply during tree traversal.
 */
abstract interface DataTraversalFunction {
    public void apply(DataTreeNode dataTreeNode, String padding);
}

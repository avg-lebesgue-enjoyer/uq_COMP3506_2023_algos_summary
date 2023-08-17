import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
    private static String[] internalBrowseCommandsArray = {};
    /** Browse commands for any node */
    private static List<String> defaultBrowseCommands = Arrays.asList(defaultBrowseCommandsArray);
    /** Additional browse commands for any DataTypeNode */
    private static List<String> typeBrowseCommands = Arrays.asList(typeBrowseCommandsArray);
    /** Additional browse commands for any DataStructureNode */
    private static List<String> structureBrowseCommands = Arrays.asList(structureBrowseCommandsArray);
    /** Additional browse commands for any internal node */
    private static List<String> internalBrowseCommands = Arrays.asList(internalBrowseCommandsArray);

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

    // METHODS OF INTEREST
    /* TODO:
     *  - Method to *browse* this tree.
     *  - Method to print this DataTree in LaTeX
     *  - Method to get the sub-DataTree consisting only of data structures that may call a given AlgorithmNode
     */

    /**
     * Browse this DataTree.
     */
    public void browse() {
        this.cursor = this.getRoot();
        if (this.cursor == null) {
            System.err.println("Cannot browse a tree with no root!");
            return;
        }
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            printBrowseCommands();
            String command = keyboard.nextLine();
            switch (command) {
                case "die":
                    keyboard.close();
                    return;
                case "echo_node_data":
                    System.out.println("\t" + cursor.toString());
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
                    throw new UnsupportedOperationException("haven't implemented yet!");
                    // break;
                default:
                    System.out.println("Invalid command.");
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
        return browseCommands;
    }

    /**
     * Helper method for printing browsing commands.
     * @see DataTree#browse()
     * @see DataTree#getBrowseCommands()
     */
    private void printBrowseCommands() {
        String printMe = "Please enter one of the following commands:";
        List<String> browseCommands = this.getBrowseCommands();
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
    private void preorderTraversal(DataTreeNode node, TraversalFunction traversalFunction, String padding) {
        traversalFunction.apply(node, padding);
        for (DataTreeNode child : node.getChildren()) {
            preorderTraversal(child, traversalFunction, padding + "\t");
        }
    }
}

/**
 * Function to apply during tree traversal.
 */
abstract interface TraversalFunction {
    public void apply(DataTreeNode dataTreeNode, String padding);
}

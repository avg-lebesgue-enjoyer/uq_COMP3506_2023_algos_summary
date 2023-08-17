import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p> Launch platform.
 */
public class Main {
    // MEMBER VARIABLES
    /** DataTree to maintain */
    private static DataTree dataTree;
    /** filename to dataTree.ser, from AlgosSummary */
    private static final String dataTreeFilename = "uq_COMP3506_2023_algos_summary/saved_data/dataTree.ser";
    /** AlgoTree to maintain */
    private static AlgoTree algoTree;
    /** filename to algoTree.ser, from AlgosSummary */
    private static String algoTreeFilename;

    /**
     * Launch method.
     * @param args String[] command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Amogus");
    }
    
    // TEST METHODS
    public static void testSerializationMain(String[] args) {
        dataTree = new DataTree();
        DataTypeNode node = new DataTypeNode("Sugoma");
        dataTree.setRoot(node);

        algoTree = null;
        
        System.out.println("All set up");

        // Serialise
        try {
            // Streams
            FileOutputStream file = new FileOutputStream(dataTreeFilename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Write object
            out.writeObject(dataTree);
            // Close
            out.close();
            file.close();
            System.out.println("Successfully serialised :)");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Working directory is " + System.getProperty("user.dir"));
        }

        // Destroy data
        dataTree = null;
        algoTree = null;

        // Deserialise
        try
        {  
            // Reading the object from a file
            FileInputStream file = new FileInputStream(dataTreeFilename);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            dataTree = (DataTree) in.readObject();
             
            in.close();
            file.close();
             
            System.out.println("Successfully deserialised :)");
        } catch(IOException e)
        {
            System.err.println(e);
            System.out.println("Working directory is " + System.getProperty("user.dir"));
        } catch(ClassNotFoundException e)
        {
            System.err.println(e);
        }
        
        // Check thing is okay
        try {
            DataTreeNode root = dataTree.getRoot();
            if (root instanceof DataTypeNode) {
                DataTypeNode rootTypeNode = (DataTypeNode) root;
                System.out.println("root stored field " + rootTypeNode.getTypeOfDataStructure());
            } else {
                System.err.println("That's bad.");
            }
        } catch (NullPointerException e) {
            System.err.println(e);
        }
        
        System.out.println("End of method");
    }
}

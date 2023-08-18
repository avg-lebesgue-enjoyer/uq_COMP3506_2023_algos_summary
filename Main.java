import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * <p> Launch platform.
 */
public class Main {
    // MEMBER VARIABLES
    /** DataTree to maintain */
    private static DataTree dataTree;
    /** filename to dataTree.ser, from AlgosSummary */
    private static String dataTreeFilename = "uq_COMP3506_2023_algos_summary/saved_data/dataTree.ser";
    /** AlgoTree to maintain */
    private static AlgoTree algoTree;
    /** filename to algoTree.ser, from AlgosSummary */
    private static String algoTreeFilename = "uq_COMP3506_2023_algos_summary/saved_data/algoTree.ser";
    /** AlgoDataPairing to maintain */
    private static AlgoDataPairing pairing;
    /** filename to pairing.ser, from AlgosSummary */
    private static String pairingFilename = "uq_COMP3506_2023_algos_summary/saved_data/pairing.ser";
    
    /**
     * Launch method.
     * @param args String[] command line arguments
     */
    public static void main(String[] args) {
        // HARD RESET
        /*
        emptyItems();
        serialise();
        */
        try (Scanner keyboard = new Scanner(System.in)) {
            // Reset filenames?
            System.out.println("Would you like to reset your filenames? \"y\"/else");
            System.out.println(
                "Note: only dataTree is actually implemented at the "
                + "moment, but algoTree is necessary for deserialisation. "
                + "pairing is not even specified in the code, so you can leave it blank."
            );
            if (keyboard.nextLine().equals("y")) {
                resetFilenames(keyboard);
            }

            // Deserialise
            deserialise();
            
            // Browse dataTree
            dataTree.browse(keyboard);
            
            // Serialise result
            boolean retry;
            do {
                retry = false;
                System.out.println("Would you like to serialise? \"y\"/\"n\"");
                String doSerialise = keyboard.nextLine();
                if (doSerialise.strip().equals("y")) {
                    serialise();
                } else if (! doSerialise.strip().equals("n")) {
                    retry = true;
                }
            } while (retry);
        }
        System.out.println("End of main()");
    }

    // IMPORTANT METHODS
    // none

    // HELPER METHODS
    private static void resetFilenames(Scanner keyboard) {
        System.out.println("==== Resetting filenames ====");
        System.out.println("Input relative filepath to dataTree.ser, from the location of execution:");
        dataTreeFilename = keyboard.nextLine();
        System.out.println("Input relative filepath to algoTree.ser, from the location of execution:");
        algoTreeFilename = keyboard.nextLine();
        System.out.println("Input relative filepath to pairing.ser, from the location of execution:");
        pairingFilename = keyboard.nextLine();
        System.out.println("~~~~ filenames reset");
    }

    private static void emptyItems() {
        System.out.println("==== EMPTYING ITEMS ====");
        dataTree = new DataTree();
        dataTree.reRoot();

        algoTree = new AlgoTree();
        algoTree.reRoot();

        pairing = new AlgoDataPairing();
        System.out.println("~~~~ items emptied");
    }
    
    /**
     * <p> Serialise {@link Main#dataTree} and {@link Main#algoTree}
     * <p> TODO: serialise and deserialise AlgoDataPairing.
     */
    private static void serialise() {
        System.out.println("==== SERIALISING... ====");
        // Serialise dataTree
        System.out.println("== Serialising dataTree ==");
        String suffix = "unsuccessfully";
        try {
            // Streams
            FileOutputStream file = new FileOutputStream(dataTreeFilename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Write object
            out.writeObject(dataTree);
            // Close
            out.close();
            file.close();
            suffix = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        }
        System.out.println("~~ dataTree serialisation " + suffix);
        // Serialise algoTree
        System.out.println("== Serialising algoTree ==");
        suffix = "unsuccessfully";
        try {
            // Streams
            FileOutputStream file = new FileOutputStream(algoTreeFilename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Write object
            out.writeObject(algoTree);
            // Close
            out.close();
            file.close();
            suffix = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        }
        System.out.println("~~ algoTree serialisation " + suffix);
        System.out.println("~~~~ serialisaiton done.");
    }

    /**
     * <p> Deserialise {@link Main#dataTree} and {@link Main#algoTree}
     * <p> TODO: serialise and deserialise AlgoDataPairing.
     */
    private static void deserialise() {
        System.out.println("==== DESERIALISING... ====");
        // Deserialise dataTree
        System.out.println("==  Deserialising dataTree ==");
        String suffix = "unsuccessfully";
        try {  
            // Streams
            FileInputStream file = new FileInputStream(dataTreeFilename);
            ObjectInputStream in = new ObjectInputStream(file);
            // Read object
            dataTree = (DataTree) in.readObject();
            // Close
            in.close();
            file.close();
            suffix = "successfully";
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        } catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("~~ dataTree deserialised " + suffix);
        // Deserialise algoTree
        System.out.println("==  Deserialising algoTree ==");
        suffix = "unsuccessfully";
        try {  
            // Streams
            FileInputStream file = new FileInputStream(algoTreeFilename);
            ObjectInputStream in = new ObjectInputStream(file);
            // Read object
            algoTree = (AlgoTree) in.readObject();
            // Close
            in.close();
            file.close();
            suffix = "successfully";
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        } catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("~~ algoTree deserialised " + suffix);
        System.out.println("~~~~ deserialisation done.");
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
        
        // Add funny test data
        DataStructureNode newNode = new DataStructureNode(dataTree.getRoot(), "StaticSequence", "dat:static_sequence");
        dataTree.getRoot().getChildren().add(newNode);

        // Check thing is okay
        try {
            dataTree.browse(null); // BUG: This breaks immediately lol
        } catch (NullPointerException e) {
            System.err.println(e);
        }
        
        System.out.println("End of method");
    }
}

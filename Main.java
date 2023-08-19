import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
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
    
    /** Array of commands that can be executed from {@link Main#main(String[])} */
    private static final String[] commandsArray = {
        "die",
        "browse_data",
        "browse_algos",
        "pair_data_to_algos",
        "pair_algo_to_datas",
        "list_pairing",
        "serialise"
    };
    /** List of commands that can be executed from {@link Main#main(String[])} */
    private static final List<String> commands = Arrays.asList(commandsArray);
    /** Array of commands that can be executed from {@link Main#pairDataToAlgos()} or {@link Main#pairAlgoToDatas()} */
    private static final String[] pairCommandsArray = {
        "append",
        "override",
        "delete"
    };
    /** List of commands that can be executed from {@link Main#pairDataToAlgos()} or {@link Main#pairAlgoToDatas()} */
    private static final List<String> pairCommands = Arrays.asList(pairCommandsArray);

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
            boolean deserialised = deserialise();
            if (! deserialised) {
                System.out.println(
                    "Some data did not serialise. "
                    + "\nWould you like to kill it all? (\"kill\"/else)"
                );
                if (keyboard.nextLine().strip().equals("kill")) {
                    emptyItems();
                }
            }
            
            // Request user input
            boolean dead = false;
            String command;
            while (! dead) {
                printCommands();
                command = keyboard.nextLine();
                if (! commands.contains(command)) {
                    System.out.println("Invalid command.");
                } else {
                    switch (command) {
                        case "die":
                            dead = true;
                            break;
                        case "browse_data":
                            dataTree.browse(keyboard);
                            break;
                        case "browse_algos":
                            algoTree.browse(keyboard);
                            break;
                        case "serialise":
                            serialise();
                            break;
                        case "pair_data_to_algos":
                            pairDataToAlgos(keyboard);
                            break;
                        case "pair_algo_to_datas":
                            pairAlgoToDatas(keyboard);
                            break;
                        case "list_pairing":
                            System.out.println("This view isn't very useful.");
                            for (AlgoDataPair pair : pairing.getPairs()) {
                                System.out.println(pair);
                            }
                            break;
                        default:
                            System.err.println("I have a bug :(((");
                    }
                }
            }
            
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
    /**
     * Request methods about pairing data to algos.
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private static void pairDataToAlgos(Scanner keyboard) {
        printPairCommands("data -> algos");
        String command = keyboard.nextLine();
        if (! pairCommands.contains(command)) {
            System.out.println("Invalid command.");
        } else {
            switch (command) {
                case "append":
                    pairing.appendDataAlgo(keyboard);
                    break;
                case "override":
                    pairing.overrideDataAlgo(keyboard);
                    break;
                case "delete":
                    pairing.deleteDataAlgo(keyboard);
                    break;
                default:
                    System.err.println("I have a bug :(((");
            }
        }
    }

    /**
     * Request methods about pairing algo to datas.
     * @param keyboard {@link java.util.Scanner} input scanner
     */
    private static void pairAlgoToDatas(Scanner keyboard) {
        printPairCommands("algos -> datas");
        String command = keyboard.nextLine();
        if (! pairCommands.contains(command)) {
            System.out.println("Invalid command.");
        } else {
            switch (command) {
                case "append":
                    pairing.appendAlgoData(keyboard);
                    break;
                case "override":
                    pairing.overrideAlgoData(keyboard);
                    break;
                case "delete":
                    pairing.deleteAlgoData(keyboard);
                    break;
                default:
                    System.err.println("I have a bug :(((");
            }
        }
    }

    // TODO: the other way around

    // HELPER METHODS
    /**
     * Prints commands that can be executed in Main().
     */
    private static void printCommands() {
        String printMe = "Please enter one of the following commands:";
        for (String command : commands) {
            printMe += "\n\t\"" + command + "\"";
        }
        System.out.println(printMe);
    }

    /**
     * Prints commands that can be executed in {@link Main#pairDataToAlgos()}
     * or {@link Main#pairAlgoToDatas()}.
     * @param whichWay String indicating whether data structure is fixed or whether
     * algorithm is fixed
     */
    private static void printPairCommands(String whichWay) {
        String printMe = "(" + whichWay + ") "
                + "Please enter one of the following commands:";
        for (String command : pairCommands) {
            printMe += "\n\t\"" + command + "\"";
        }
        System.out.println(printMe);
    }

    /**
     * Resets filenames for the current execution of Main().
     * @param keyboard Scanner with user input
     */
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

    /**
     * Empty dataTree, algoTree and pairing.
     */
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
        System.out.println("== Serialising pairing ==");
        String suffix = "unsuccessfully";
        try (ObjectOutputStream out = 
                new ObjectOutputStream(new FileOutputStream(dataTreeFilename))){
            out.writeObject(dataTree);
            suffix = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        }
        System.out.println("~~ pairing serialised " + suffix);
        
        // Serialise algoTree
        System.out.println("== Serialising algoTree ==");
        suffix = "unsuccessfully";
        try (ObjectOutputStream out = 
                new ObjectOutputStream(new FileOutputStream(algoTreeFilename))){
            out.writeObject(algoTree);
            suffix = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        }
        System.out.println("~~ pairing serialised " + suffix);
        
        // Serialise pairing
        System.out.println("== Serialising pairing ==");
        suffix = "unsuccessfully";
        try (ObjectOutputStream out = 
                new ObjectOutputStream(new FileOutputStream(pairingFilename))){
            out.writeObject(pairing);
            suffix = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        }
        System.out.println("~~ pairing serialised " + suffix);
        
        System.out.println("~~~~ serialisaiton done.");
    }

    /**
     * <p> Deserialise {@link Main#dataTree} and {@link Main#algoTree}
     * <p> TODO: serialise and deserialise AlgoDataPairing.
     * @return true iff
     */
    private static boolean deserialise() {
        System.out.println("==== DESERIALISING... ====");
        
        // Deserialise dataTree
        System.out.println("==  Deserialising dataTree ==");
        String suffixData = "unsuccessfully";
        try (ObjectInputStream in = 
                new ObjectInputStream(new FileInputStream(dataTreeFilename))) {  
            dataTree = (DataTree) in.readObject();
            suffixData = "successfully";
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        } catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("~~ dataTree deserialised " + suffixData);
        
        // Deserialise algoTree
        System.out.println("==  Deserialising algoTree ==");
        String suffixAlgos = "unsuccessfully";
        try (ObjectInputStream in = 
                new ObjectInputStream(new FileInputStream(algoTreeFilename))) {  
            algoTree = (AlgoTree) in.readObject();
            suffixAlgos = "successfully";
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        } catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("~~ algoTree deserialised " + suffixAlgos);
        
        // Deserialise pairing
        System.out.println("== Deserialising pairing ==");
        String suffixPairing = "unsuccessfully";
        try (ObjectInputStream in = 
                new ObjectInputStream(new FileInputStream(pairingFilename))) {
            pairing = (AlgoDataPairing) in.readObject();
            suffixPairing = "successfully";
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Working directory is " + System.getProperty("user.dir"));
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("~~ pairing deserialised " + suffixPairing);
        
        System.out.println("~~~~ deserialisation done.");
        return suffixData.equals("successfully") && suffixAlgos.equals("successfully")
                && suffixPairing.equals("successfully");
    }
    
    // BOILERPLATE METHODS
    public static DataTree getDataTree() {
        return dataTree;
    }
    public static AlgoTree getAlgoTree() {
        return algoTree;
    }
    public static AlgoDataPairing getPairing() {
        return pairing;
    }

    // TEST METHODS
    /**
     * Ignore
     * @param args ignore
     */
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

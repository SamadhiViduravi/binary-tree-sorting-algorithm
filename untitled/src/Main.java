import java.io.IOException;

/**
 * Main class for the Tree Sorting application
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            // Example 1: Small test case from the coursework description
            System.out.println("=== EXAMPLE 1: Small Test Case ===");
            String testInput = "1 3 2 7 5 6 4";
            TreeState initialState = InputParser.parseFromString(testInput);
            
            System.out.println("Initial tree: " + initialState);
            System.out.println("Is BST: " + initialState.isBST());
            
            TreeSorter.SearchResult result = TreeSorter.findMinimumSwaps(initialState);
            TreeSorter.printSolution(result);
            
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            // Example 2: Another test case
            System.out.println("=== EXAMPLE 2: Another Test Case ===");
            String testInput2 = "4 2 6 1 3 5 7";
            TreeState initialState2 = InputParser.parseFromString(testInput2);
            
            System.out.println("Initial tree: " + initialState2);
            System.out.println("Is BST: " + initialState2.isBST());
            
            TreeSorter.SearchResult result2 = TreeSorter.findMinimumSwaps(initialState2);
            TreeSorter.printSolution(result2);
            
            // Example 3: Reading from file (if file exists)
            if (args.length > 0) {
                System.out.println("\n" + "=".repeat(50) + "\n");
                System.out.println("=== READING FROM FILE: " + args[0] + " ===");
                
                TreeState fileState = InputParser.parseFromFile(args[0]);
                System.out.println("Tree from file: " + fileState);
                System.out.println("Is BST: " + fileState.isBST());
                
                TreeSorter.SearchResult fileResult = TreeSorter.findMinimumSwaps(fileState);
                TreeSorter.printSolution(fileResult);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

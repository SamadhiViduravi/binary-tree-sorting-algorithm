import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the input file name (e.g., input.txt): ");
            String filename = scanner.nextLine().trim();

            // Parse input
            int[] values = TreeParser.parseInputFile(filename);

            if (!TreeParser.isValidInput(values)) {
                System.out.println("Invalid input. Exiting.");
                return;
            }

            System.out.println("Input tree: " + java.util.Arrays.toString(values));

            // Create initial and target trees
            BinaryTree initial = new BinaryTree(values);
            BinaryTree target = BinaryTree.generateTargetBST(values);

            System.out.println("Target BST: " + target);

            // Check if already a BST
            if (initial.isBST()) {
                System.out.println("Input tree is already a valid BST!");
                return;
            }

            // Find shortest path using BFS
            System.out.println("\nSearching for shortest path...");
            long startTime = System.currentTimeMillis();

            List<BinaryTree> solution = TreeSorter.findShortestPath(initial, target);

            long endTime = System.currentTimeMillis();
            System.out.println("Search completed in " + (endTime - startTime) + " ms");

            // Print solution
            TreeSorter.printSolution(solution);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

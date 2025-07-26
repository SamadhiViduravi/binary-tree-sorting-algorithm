import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the input file name (e.g., input.txt): ");
        String filename = scanner.nextLine().trim();

        try {
            // Parse the input file
            BinaryTree initialTree = TreeParser.parseFromFile(filename);

            // Find the shortest path using A* search
            List<String> solution = TreeSorter.findShortestPath(initialTree);

            // Print the solution
            TreeSorter.printSolution(initialTree, solution);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}

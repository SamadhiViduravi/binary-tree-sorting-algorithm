// File: Main.java
import java.util.List;
import java.util.Scanner;
import java.io.*;
/**
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file name (e.g., input.txt): ");
        String filename = scanner.nextLine().trim();

        try {
            BinaryTree tree = TreeParser.parse(filename);
            List<String> swaps = TreeSorter.findShortestPath(tree);

            System.out.println("Solution found with " + swaps.size() + " swaps:");
            if (swaps.isEmpty()) {
                System.out.println("No swaps needed or no solution found.");
            } else {
                for (String swap : swaps) {
                    System.out.println(swap);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
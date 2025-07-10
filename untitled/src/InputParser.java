import java.io.*;
import java.util.*;

/**
 * Parser for reading tree input files
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class InputParser {
    
    /**
     * Reads a tree from an input file
     * Expected format: numbers separated by spaces, possibly on multiple lines
     */
    public static TreeState parseFromFile(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        try {
                            numbers.add(Integer.parseInt(token));
                        } catch (NumberFormatException e) {
                            throw new IOException("Invalid number format: " + token);
                        }
                    }
                }
            }
        }
        
        if (numbers.isEmpty()) {
            throw new IOException("No numbers found in file");
        }
        
        int[] treeArray = numbers.stream().mapToInt(Integer::intValue).toArray();
        
        // Validate the input
        validateInput(treeArray);
        
        return new TreeState(treeArray);
    }
    
    /**
     * Parses tree from a string (useful for testing)
     */
    public static TreeState parseFromString(String input) {
        String[] tokens = input.trim().split("\\s+");
        int[] treeArray = new int[tokens.length];
        
        for (int i = 0; i < tokens.length; i++) {
            treeArray[i] = Integer.parseInt(tokens[i]);
        }
        
        validateInput(treeArray);
        return new TreeState(treeArray);
    }
    
    /**
     * Validates that the input represents a valid complete binary tree with numbers 1...N
     */
    private static void validateInput(int[] tree) {
        int n = tree.length;
        
        // Check if tree size represents a complete binary tree
        if (!isCompleteBinaryTreeSize(n)) {
            throw new IllegalArgumentException("Tree size " + n + " does not represent a complete binary tree");
        }
        
        // Check if all numbers from 1 to N are present exactly once
        boolean[] present = new boolean[n + 1];
        for (int value : tree) {
            if (value < 1 || value > n) {
                throw new IllegalArgumentException("Invalid value: " + value + ". Expected values between 1 and " + n);
            }
            if (present[value]) {
                throw new IllegalArgumentException("Duplicate value: " + value);
            }
            present[value] = true;
        }
        
        // Check if any number is missing
        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                throw new IllegalArgumentException("Missing value: " + i);
            }
        }
    }
    
    /**
     * Checks if the given size represents a complete binary tree
     */
    private static boolean isCompleteBinaryTreeSize(int size) {
        if (size <= 0) return false;
        
        // For a complete binary tree, size should be 2^h - 1 for some height h
        int powerOfTwo = 1;
        while (powerOfTwo - 1 < size) {
            if (powerOfTwo - 1 == size) {
                return true;
            }
            powerOfTwo *= 2;
        }
        return false;
    }
}

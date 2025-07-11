import java.io.*;
import java.util.*;

public class TreeParser {
    public static BinaryTree parse(String filename) throws IOException {
        List<Integer> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    values.add(Integer.parseInt(token));
                }
            }
        }
        // Validate input
        if (!isValidInput(values)) {
            throw new IllegalArgumentException("Invalid input: not a complete binary tree or contains duplicates");
        }
        return new BinaryTree(values.stream().mapToInt(i -> i).toArray());
    }

    private static boolean isValidInput(List<Integer> values) {
        // Check for duplicates
        Set<Integer> set = new HashSet<>(values);
        if (set.size() != values.size()) return false;
        // Check if size forms a complete binary tree
        int n = values.size();
        return (n & (n + 1)) == 0; // Checks if n is one less than a power of 2
    }
}
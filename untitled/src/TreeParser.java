import java.io.*;
import java.util.*;

public class TreeParser {

    public static BinaryTree parseFromFile(String filename) throws IOException {
        System.out.println("Reading input from: " + filename);

        List<Integer> values = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] tokens = line.split("\\s+");
                    for (String token : tokens) {
                        if (!token.isEmpty()) {
                            try {
                                values.add(Integer.parseInt(token));
                            } catch (NumberFormatException e) {
                                throw new IOException("Invalid number format: " + token);
                            }
                        }
                    }
                }
            }
        }

        if (values.isEmpty()) {
            throw new IOException("No valid numbers found in file");
        }

        // Validate tree completeness
        int size = values.size();
        int expectedSize = (int) Math.pow(2, (int) Math.ceil(Math.log(size + 1) / Math.log(2))) - 1;

        if (size != expectedSize) {
            System.out.println("Warning: Tree may not be complete. Size: " + size);
        }

        // Validate no duplicates and contains 1...N
        Set<Integer> uniqueValues = new HashSet<>(values);
        if (uniqueValues.size() != values.size()) {
            throw new IOException("Duplicate values found in input");
        }

        // Check if values are 1...N
        for (int i = 1; i <= size; i++) {
            if (!uniqueValues.contains(i)) {
                throw new IOException("Missing value: " + i + ". Expected values 1 to " + size);
            }
        }

        System.out.println("Input validation passed.");

        int[] treeArray = values.stream().mapToInt(Integer::intValue).toArray();
        return new BinaryTree(treeArray);
    }
}

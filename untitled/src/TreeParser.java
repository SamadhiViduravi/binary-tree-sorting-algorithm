import java.io.*;
import java.util.*;

public class TreeParser {

    public static int[] parseInputFile(String filename) throws IOException {
        List<Integer> values = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        values.add(Integer.parseInt(token));
                    }
                }
            }
        }

        return values.stream().mapToInt(i -> i).toArray();
    }

    public static boolean isValidInput(int[] values) {
        if (values.length == 0) return false;

        // Check if it's a complete binary tree (2^k - 1 nodes for some k)
        int n = values.length;
        boolean isComplete = (n & (n + 1)) == 0; // Check if n+1 is power of 2

        if (!isComplete) {
            System.out.println("Warning: Tree is not complete, but proceeding anyway.");
        }

        // Check for duplicates and valid range
        Set<Integer> seen = new HashSet<>();
        for (int value : values) {
            if (value < 1 || value > values.length) {
                System.out.println("Error: Values must be in range 1 to " + values.length);
                return false;
            }
            if (seen.contains(value)) {
                System.out.println("Error: Duplicate value found: " + value);
                return false;
            }
            seen.add(value);
        }

        return true;
    }
}

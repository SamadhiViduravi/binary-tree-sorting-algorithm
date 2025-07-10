import java.util.*;

/**
 * Performance analysis utilities for the Tree Sorting algorithm
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class PerformanceAnalyzer {
    
    public static class PerformanceResult {
        private int treeSize;
        private int minSwaps;
        private long executionTimeMs;
        private int statesExplored;
        
        public PerformanceResult(int treeSize, int minSwaps, long executionTimeMs, int statesExplored) {
            this.treeSize = treeSize;
            this.minSwaps = minSwaps;
            this.executionTimeMs = executionTimeMs;
            this.statesExplored = statesExplored;
        }
        
        public int getTreeSize() { return treeSize; }
        public int getMinSwaps() { return minSwaps; }
        public long getExecutionTimeMs() { return executionTimeMs; }
        public int getStatesExplored() { return statesExplored; }
    }
    
    /**
     * Generates a random permutation of numbers 1 to n for testing
     */
    public static TreeState generateRandomTree(int size) {
        // Ensure size is valid for complete binary tree
        if (!isValidTreeSize(size)) {
            throw new IllegalArgumentException("Invalid tree size: " + size);
        }
        
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        
        int[] tree = numbers.stream().mapToInt(Integer::intValue).toArray();
        return new TreeState(tree);
    }
    
    private static boolean isValidTreeSize(int size) {
        int powerOfTwo = 1;
        while (powerOfTwo - 1 < size) {
            if (powerOfTwo - 1 == size) {
                return true;
            }
            powerOfTwo *= 2;
        }
        return false;
    }
    
    /**
     * Runs performance analysis on different tree sizes
     */
    public static void runPerformanceAnalysis() {
        System.out.println("=== PERFORMANCE ANALYSIS ===");
        System.out.println("Tree Size | Min Swaps | Time (ms) | States Explored");
        System.out.println("-".repeat(55));
        
        int[] testSizes = {3, 7, 15}; // Complete binary tree sizes
        
        for (int size : testSizes) {
            try {
                // Run multiple tests and average the results
                int numTests = 5;
                long totalTime = 0;
                int totalSwaps = 0;
                
                for (int i = 0; i < numTests; i++) {
                    TreeState randomTree = generateRandomTree(size);
                    
                    long startTime = System.currentTimeMillis();
                    TreeSorter.SearchResult result = TreeSorter.findMinimumSwaps(randomTree);
                    long endTime = System.currentTimeMillis();
                    
                    totalTime += (endTime - startTime);
                    totalSwaps += result.getTotalSwaps();
                }
                
                double avgTime = totalTime / (double) numTests;
                double avgSwaps = totalSwaps / (double) numTests;
                
                System.out.printf("%9d | %9.1f | %9.1f | %s\n", 
                                size, avgSwaps, avgTime, "N/A");
                
            } catch (Exception e) {
                System.out.printf("%9d | %9s | %9s | %s\n", 
                                size, "ERROR", "ERROR", e.getMessage());
            }
        }
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- State space size: O(n!) where n is the number of nodes");
        System.out.println("- BFS explores states level by level");
        System.out.println("- Each state has at most (n-1) neighbors");
        System.out.println("- Time complexity: O(n! * n) in worst case");
        System.out.println("- Space complexity: O(n!) for storing visited states");
    }
    
    public static void main(String[] args) {
        runPerformanceAnalysis();
    }
}

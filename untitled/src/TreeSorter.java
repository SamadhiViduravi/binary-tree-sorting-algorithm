import java.util.*;

/**
 * Main algorithm implementation for finding minimum swaps to convert tree to BST
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class TreeSorter {
    
    public static class SearchResult {
        private List<SwapOperation> swaps;
        private int totalSwaps;
        private List<TreeState> path;
        
        public SearchResult(List<SwapOperation> swaps, List<TreeState> path) {
            this.swaps = swaps;
            this.totalSwaps = swaps.size();
            this.path = path;
        }
        
        public List<SwapOperation> getSwaps() { return swaps; }
        public int getTotalSwaps() { return totalSwaps; }
        public List<TreeState> getPath() { return path; }
    }
    
    public static class SwapOperation {
        private int childIndex;
        private int parentIndex;
        private int childValue;
        private int parentValue;
        
        public SwapOperation(int childIndex, int childValue, int parentValue) {
            this.childIndex = childIndex;
            this.parentIndex = (childIndex - 1) / 2;
            this.childValue = childValue;
            this.parentValue = parentValue;
        }
        
        public int getChildIndex() { return childIndex; }
        public int getParentIndex() { return parentIndex; }
        public int getChildValue() { return childValue; }
        public int getParentValue() { return parentValue; }
        
        @Override
        public String toString() {
            return String.format("Swap node at index %d (value %d) with parent at index %d (value %d)", 
                               childIndex, childValue, parentIndex, parentValue);
        }
    }
    
    /**
     * Finds the minimum number of swaps needed to convert the tree to a BST using BFS
     */
    public static SearchResult findMinimumSwaps(TreeState initialState) {
        if (initialState.isBST()) {
            return new SearchResult(new ArrayList<>(), Arrays.asList(initialState));
        }
        
        Queue<StateNode> queue = new LinkedList<>();
        Set<TreeState> visited = new HashSet<>();
        
        StateNode initialNode = new StateNode(initialState, null, null);
        queue.offer(initialNode);
        visited.add(initialState);
        
        while (!queue.isEmpty()) {
            StateNode currentNode = queue.poll();
            TreeState currentState = currentNode.state;
            
            // Generate all possible next states
            List<TreeState> nextStates = currentState.generateNextStates();
            
            for (int i = 0; i < nextStates.size(); i++) {
                TreeState nextState = nextStates.get(i);
                
                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    
                    // Create swap operation (i+1 because we start from index 1 for non-root nodes)
                    int childIndex = i + 1;
                    SwapOperation swap = new SwapOperation(childIndex, 
                                                         currentState.getValue(childIndex),
                                                         currentState.getValue((childIndex - 1) / 2));
                    
                    StateNode nextNode = new StateNode(nextState, currentNode, swap);
                    
                    if (nextState.isBST()) {
                        // Found solution, reconstruct path
                        return reconstructPath(nextNode);
                    }
                    
                    queue.offer(nextNode);
                }
            }
        }
        
        // No solution found (should not happen for valid inputs)
        throw new RuntimeException("No solution found");
    }
    
    private static class StateNode {
        TreeState state;
        StateNode parent;
        SwapOperation swapFromParent;
        
        StateNode(TreeState state, StateNode parent, SwapOperation swapFromParent) {
            this.state = state;
            this.parent = parent;
            this.swapFromParent = swapFromParent;
        }
    }
    
    private static SearchResult reconstructPath(StateNode goalNode) {
        List<SwapOperation> swaps = new ArrayList<>();
        List<TreeState> path = new ArrayList<>();
        
        StateNode current = goalNode;
        while (current != null) {
            path.add(0, current.state);
            if (current.swapFromParent != null) {
                swaps.add(0, current.swapFromParent);
            }
            current = current.parent;
        }
        
        return new SearchResult(swaps, path);
    }
    
    /**
     * Formats the solution output
     */
    public static void printSolution(SearchResult result) {
        System.out.println("=== TREE SORTING SOLUTION ===");
        System.out.println("Minimum swaps needed: " + result.getTotalSwaps());
        System.out.println();
        
        List<TreeState> path = result.getPath();
        List<SwapOperation> swaps = result.getSwaps();
        
        System.out.println("Initial state:");
        System.out.println(path.get(0).toTreeString());
        
        for (int i = 0; i < swaps.size(); i++) {
            System.out.println("Step " + (i + 1) + ": " + swaps.get(i));
            System.out.println("Resulting state:");
            System.out.println(path.get(i + 1).toTreeString());
        }
        
        System.out.println("Final BST achieved!");
        System.out.println("Verification: " + path.get(path.size() - 1).isBST());
    }
}

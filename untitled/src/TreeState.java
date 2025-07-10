import java.util.*;

/**
 * Represents a state of the binary tree during the sorting process
 * Student ID: [Your Student ID]
 * Name: [Your Name]
 */
public class TreeState {
    private int[] tree;
    private int size;
    
    public TreeState(int[] tree) {
        this.tree = tree.clone();
        this.size = tree.length;
    }
    
    public TreeState(TreeState other) {
        this.tree = other.tree.clone();
        this.size = other.size;
    }
    
    public int[] getTree() {
        return tree.clone();
    }
    
    public int getSize() {
        return size;
    }
    
    public int getValue(int index) {
        return tree[index];
    }
    
    public void setValue(int index, int value) {
        tree[index] = value;
    }
    
    /**
     * Swaps values between parent and child nodes
     */
    public void swap(int childIndex) {
        if (childIndex <= 0 || childIndex >= size) {
            throw new IllegalArgumentException("Invalid child index");
        }
        
        int parentIndex = (childIndex - 1) / 2;
        int temp = tree[parentIndex];
        tree[parentIndex] = tree[childIndex];
        tree[childIndex] = temp;
    }
    
    /**
     * Generates all possible next states by swapping each non-root node with its parent
     */
    public List<TreeState> generateNextStates() {
        List<TreeState> nextStates = new ArrayList<>();
        
        // Try swapping each non-root node with its parent
        for (int i = 1; i < size; i++) {
            TreeState newState = new TreeState(this);
            newState.swap(i);
            nextStates.add(newState);
        }
        
        return nextStates;
    }
    
    /**
     * Checks if current tree is a valid Binary Search Tree
     */
    public boolean isBST() {
        return isBSTHelper(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isBSTHelper(int index, int min, int max) {
        if (index >= size) {
            return true;
        }
        
        if (tree[index] <= min || tree[index] >= max) {
            return false;
        }
        
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        
        return isBSTHelper(leftChild, min, tree[index]) && 
               isBSTHelper(rightChild, tree[index], max);
    }
    
    /**
     * Creates the target BST state for the given numbers
     */
    public static TreeState createTargetBST(int[] numbers) {
        int[] sorted = numbers.clone();
        Arrays.sort(sorted);
        
        int[] bstArray = new int[numbers.length];
        fillBSTArray(bstArray, sorted, 0, 0, sorted.length - 1);
        
        return new TreeState(bstArray);
    }
    
    private static void fillBSTArray(int[] bstArray, int[] sorted, int index, int start, int end) {
        if (start > end || index >= bstArray.length) {
            return;
        }
        
        int mid = start + (end - start) / 2;
        bstArray[index] = sorted[mid];
        
        fillBSTArray(bstArray, sorted, 2 * index + 1, start, mid - 1);
        fillBSTArray(bstArray, sorted, 2 * index + 2, mid + 1, end);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TreeState treeState = (TreeState) obj;
        return Arrays.equals(tree, treeState.tree);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(tree);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(tree);
    }
    
    /**
     * Returns a formatted string representation of the tree structure
     */
    public String toTreeString() {
        StringBuilder sb = new StringBuilder();
        int level = 0;
        int nodesInLevel = 1;
        int nodeCount = 0;
        
        while (nodeCount < size) {
            sb.append("Level ").append(level).append(": ");
            for (int i = 0; i < nodesInLevel && nodeCount < size; i++) {
                sb.append(tree[nodeCount]).append(" ");
                nodeCount++;
            }
            sb.append("\n");
            level++;
            nodesInLevel *= 2;
        }
        
        return sb.toString();
    }
}

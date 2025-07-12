import java.util.*;

public class BinaryTree {
    private int[] tree;
    private int size;

    public BinaryTree(int[] values) {
        this.tree = values.clone();
        this.size = values.length;
    }

    public BinaryTree(BinaryTree other) {
        this.tree = other.tree.clone();
        this.size = other.size;
    }

    public int[] getTree() {
        return tree;
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

    // Swap a node with its parent
    public BinaryTree swap(int childIndex) {
        if (childIndex == 0) return null; // Can't swap root

        BinaryTree newTree = new BinaryTree(this);
        int parentIndex = (childIndex - 1) / 2;

        int temp = newTree.tree[childIndex];
        newTree.tree[childIndex] = newTree.tree[parentIndex];
        newTree.tree[parentIndex] = temp;

        return newTree;
    }

    // Get all possible next states (all valid swaps)
    public List<BinaryTree> getNextStates() {
        List<BinaryTree> nextStates = new ArrayList<>();

        // Try swapping each non-root node with its parent
        for (int i = 1; i < size; i++) {
            BinaryTree swapped = swap(i);
            if (swapped != null) {
                nextStates.add(swapped);
            }
        }

        return nextStates;
    }

    // Check if this tree is a valid BST
    public boolean isBST() {
        return isBSTHelper(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTHelper(int index, int min, int max) {
        if (index >= size) return true;

        int value = tree[index];
        if (value <= min || value >= max) return false;

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        return isBSTHelper(leftChild, min, value) &&
                isBSTHelper(rightChild, value, max);
    }

    // Generate target BST configuration
    public static BinaryTree generateTargetBST(int[] values) {
        int[] sorted = values.clone();
        Arrays.sort(sorted);

        int[] bstArray = new int[values.length];
        fillBSTArray(bstArray, sorted, 0, 0, sorted.length - 1);

        return new BinaryTree(bstArray);
    }

    private static void fillBSTArray(int[] bstArray, int[] sorted, int index, int start, int end) {
        if (start > end || index >= bstArray.length) return;

        int mid = start + (end - start) / 2;
        bstArray[index] = sorted[mid];

        fillBSTArray(bstArray, sorted, 2 * index + 1, start, mid - 1);
        fillBSTArray(bstArray, sorted, 2 * index + 2, mid + 1, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BinaryTree other = (BinaryTree) obj;
        return Arrays.equals(tree, other.tree);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tree);
    }

    @Override
    public String toString() {
        return Arrays.toString(tree);
    }

    public String getSwapDescription(BinaryTree parent) {
        if (parent == null) return "Initial state";

        for (int i = 1; i < size; i++) {
            int parentIndex = (i - 1) / 2;
            if (this.tree[i] == parent.tree[parentIndex] &&
                    this.tree[parentIndex] == parent.tree[i]) {
                return "Swap positions " + parentIndex + " and " + i +
                        " (values " + this.tree[parentIndex] + " and " + this.tree[i] + ")";
            }
        }
        return "Unknown swap";
    }
}

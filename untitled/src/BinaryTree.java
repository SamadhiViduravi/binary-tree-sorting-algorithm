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

    public int[] getArray() {
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

    public void swap(int index1, int index2) {
        int temp = tree[index1];
        tree[index1] = tree[index2];
        tree[index2] = temp;
    }

    public int getParentIndex(int index) {
        if (index == 0) return -1;
        return (index - 1) / 2;
    }

    public int getLeftChildIndex(int index) {
        int leftIndex = 2 * index + 1;
        return leftIndex < size ? leftIndex : -1;
    }

    public int getRightChildIndex(int index) {
        int rightIndex = 2 * index + 2;
        return rightIndex < size ? rightIndex : -1;
    }

    public boolean isBST() {
        return isBSTHelper(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTHelper(int index, int min, int max) {
        if (index >= size) return true;

        int value = tree[index];
        if (value <= min || value >= max) return false;

        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);

        boolean leftValid = leftChild == -1 || isBSTHelper(leftChild, min, value);
        boolean rightValid = rightChild == -1 || isBSTHelper(rightChild, value, max);

        return leftValid && rightValid;
    }

    public List<Integer> getPossibleSwaps() {
        List<Integer> swaps = new ArrayList<>();
        for (int i = 1; i < size; i++) { // Start from 1 (skip root)
            swaps.add(i);
        }
        return swaps;
    }

    public int calculateHeuristic() {
        int violations = 0;
        for (int i = 0; i < size; i++) {
            int leftChild = getLeftChildIndex(i);
            int rightChild = getRightChildIndex(i);

            if (leftChild != -1 && tree[leftChild] >= tree[i]) {
                violations++;
            }
            if (rightChild != -1 && tree[rightChild] <= tree[i]) {
                violations++;
            }
        }
        return violations;
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

    public void printTreeStructure() {
        if (size == 0) return;

        int levels = (int) Math.ceil(Math.log(size + 1) / Math.log(2));
        int maxWidth = (int) Math.pow(2, levels - 1) * 4;

        for (int level = 0; level < levels; level++) {
            int startIndex = (int) Math.pow(2, level) - 1;
            int endIndex = Math.min(startIndex + (int) Math.pow(2, level), size);

            int spacing = maxWidth / (int) Math.pow(2, level + 1);

            for (int i = 0; i < spacing; i++) System.out.print(" ");

            for (int i = startIndex; i < endIndex; i++) {
                if (i < size) {
                    System.out.print(tree[i]);
                    if (i < endIndex - 1) {
                        for (int j = 0; j < spacing * 2; j++) System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }
}


import java.util.*;

public class BinaryTree {
    private TreeNode[] nodes;
    private int size;

    public BinaryTree(int[] values) {
        size = values.length;
        nodes = new TreeNode[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = new TreeNode(values[i]);
        }
        // Set up parent and child relationships
        for (int i = 0; i < size; i++) {
            if (2 * i + 1 < size) {
                nodes[i].left = nodes[2 * i + 1];
                nodes[2 * i + 1].parent = nodes[i];
            }
            if (2 * i + 2 < size) {
                nodes[i].right = nodes[2 * i + 2];
                nodes[2 * i + 2].parent = nodes[i];
            }
        }
    }

    public TreeNode[] getNodes() {
        return nodes;
    }

    public BinaryTree copy() {
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = nodes[i].value;
        }
        return new BinaryTree(values);
    }

    public void swapWithParent(int index) {
        if (index == 0 || nodes[index].parent == null) return;
        int parentIndex = (index - 1) / 2;
        int temp = nodes[index].value;
        nodes[index].value = nodes[parentIndex].value;
        nodes[parentIndex].value = temp;
    }

    public boolean isBST() {
        return isBST(nodes[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(TreeNode node, int min, int max) {
        if (node == null) return true;
        if (node.value <= min || node.value >= max) return false;
        return isBST(node.left, min, node.value) && isBST(node.right, node.value, max);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BinaryTree)) return false;
        BinaryTree other = (BinaryTree) obj;
        if (size != other.size) return false;
        for (int i = 0; i < size; i++) {
            if (nodes[i].value != other.nodes[i].value) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodes);
    }
}
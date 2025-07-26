public class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;
    public int index; // Position in array representation

    public TreeNode(int value, int index) {
        this.value = value;
        this.index = index;
        this.left = null;
        this.right = null;
    }

    public TreeNode(int value) {
        this(value, -1);
    }
}

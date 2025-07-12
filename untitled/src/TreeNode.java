public class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;
    public int index; // For level-order indexing

    public TreeNode(int value, int index) {
        this.value = value;
        this.index = index;
        this.left = null;
        this.right = null;
    }
}

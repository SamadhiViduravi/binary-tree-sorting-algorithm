import java.util.*;

public class TreeSorter {
    private static class State {
        BinaryTree tree;
        List<String> swaps;
        State parent;

        State(BinaryTree tree, List<String> swaps, State parent) {
            this.tree = tree;
            this.swaps = swaps;
            this.parent = parent;
        }
    }

    public static List<String> findShortestPath(BinaryTree initial) {
        Queue<State> queue = new LinkedList<>();
        Set<BinaryTree> visited = new HashSet<>();
        List<String> initialSwaps = new ArrayList<>();
        queue.offer(new State(initial, initialSwaps, null));
        visited.add(initial);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.tree.isBST()) {
                return current.swaps;
            }

            TreeNode[] nodes = current.tree.getNodes();
            for (int i = 1; i < nodes.length; i++) { // Skip root
                BinaryTree nextTree = current.tree.copy();
                nextTree.swapWithParent(i);
                if (!visited.contains(nextTree)) {
                    visited.add(nextTree);
                    List<String> newSwaps = new ArrayList<>(current.swaps);
                    newSwaps.add("Swap node " + nodes[i].value + " with parent " + nodes[(i-1)/2].value);
                    queue.offer(new State(nextTree, newSwaps, current));
                }
            }
        }
        return new ArrayList<>(); // No solution found
    }
}
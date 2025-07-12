import java.util.*;

public class TreeSorter {

    public static List<BinaryTree> findShortestPath(BinaryTree initial, BinaryTree target) {
        if (initial.equals(target)) {
            return Arrays.asList(initial);
        }

        // BFS with memory optimization
        Queue<BinaryTree> queue = new LinkedList<>();
        Map<String, BinaryTree> visited = new HashMap<>(); // Use string representation to save memory
        Map<String, String> parent = new HashMap<>();

        queue.offer(initial);
        visited.put(initial.toString(), initial);
        parent.put(initial.toString(), null);

        int maxQueueSize = 0;
        int statesExplored = 0;

        while (!queue.isEmpty()) {
            maxQueueSize = Math.max(maxQueueSize, queue.size());
            BinaryTree current = queue.poll();
            statesExplored++;

            // Memory management: clear old states periodically
            if (statesExplored % 1000 == 0) {
                System.gc(); // Suggest garbage collection
                System.out.println("States explored: " + statesExplored +
                        ", Queue size: " + queue.size() +
                        ", Max queue size: " + maxQueueSize);
            }

            // Limit search depth to prevent memory overflow
            if (statesExplored > 50000) {
                System.out.println("Search limit reached. No solution found within reasonable bounds.");
                return null;
            }

            List<BinaryTree> nextStates = current.getNextStates();

            for (BinaryTree next : nextStates) {
                String nextStr = next.toString();

                if (next.equals(target)) {
                    // Reconstruct path
                    List<BinaryTree> path = new ArrayList<>();
                    BinaryTree step = next;
                    String stepStr = nextStr;

                    while (step != null) {
                        path.add(0, step);
                        stepStr = parent.get(stepStr);
                        step = stepStr != null ? visited.get(stepStr) : null;
                    }

                    System.out.println("Solution found! States explored: " + statesExplored);
                    return path;
                }

                if (!visited.containsKey(nextStr)) {
                    visited.put(nextStr, next);
                    parent.put(nextStr, current.toString());
                    queue.offer(next);
                }
            }
        }

        System.out.println("No solution found. States explored: " + statesExplored);
        return null;
    }

    public static void printSolution(List<BinaryTree> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("No solution to print.");
            return;
        }

        System.out.println("\nSolution found in " + (path.size() - 1) + " steps:");
        System.out.println("==========================================");

        for (int i = 0; i < path.size(); i++) {
            BinaryTree current = path.get(i);
            BinaryTree previous = i > 0 ? path.get(i - 1) : null;

            System.out.println("Step " + i + ": " + current.getSwapDescription(previous));
            System.out.println("State: " + current);

            if (i == 0) {
                System.out.println("(Initial state)");
            } else if (i == path.size() - 1) {
                System.out.println("(Target BST achieved!)");
            }
            System.out.println();
        }
    }
}

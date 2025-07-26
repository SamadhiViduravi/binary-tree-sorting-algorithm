import java.util.*;

public class TreeSorter {

    private static class State {
        BinaryTree tree;
        int steps; // Number of swaps taken
        List<String> path; // Path taken to reach this state

        State(BinaryTree tree, int steps, List<String> path) {
            this.tree = tree;
            this.steps = steps;
            this.path = new ArrayList<>(path);
        }
    }

    public static List<String> findShortestPath(BinaryTree initialTree) {
        if (initialTree.isBST()) {
            System.out.println("Tree is already a BST!");
            return new ArrayList<>();
        }

        System.out.println("Tree size: " + initialTree.getSize() + " nodes");
        System.out.println("Searching for shortest path using BFS...");

        // Use LinkedList as a FIFO queue for BFS
        Queue<State> openSet = new LinkedList<>();
        Set<String> closedSet = new HashSet<>();
        int maxClosedSetSize = 100000; // Limit closed set to prevent memory overflow

        State initialState = new State(initialTree, 0, new ArrayList<>());
        openSet.add(initialState);

        int statesExplored = 0;
        int maxOpenSetSize = 0;
        long startTime = System.currentTimeMillis();

        while (!openSet.isEmpty()) {
            State current = openSet.poll();
            statesExplored++;

            // Memory management: limit exploration
            if (statesExplored > 200000) {
                System.out.println("Search space too large, stopping at " + statesExplored + " states");
                break;
            }

            // Progress reporting
            if (statesExplored % 1000 == 0) {
                System.out.println("States explored: " + statesExplored +
                        ", Open set size: " + openSet.size() +
                        ", Current steps: " + current.steps);
            }

            maxOpenSetSize = Math.max(maxOpenSetSize, openSet.size());

            // Check if state has been visited
            String currentStateKey = current.tree.toString();
            if (closedSet.contains(currentStateKey)) {
                continue;
            }

            // Add to closed set, with size management
            if (closedSet.size() >= maxClosedSetSize) {
                // Remove oldest entries (approximated by random removal)
                Iterator<String> iterator = closedSet.iterator();
                for (int i = 0; i < 1000 && iterator.hasNext(); i++) {
                    iterator.next();
                    iterator.remove();
                }
                System.out.println("Pruned closed set to prevent memory overflow");
            }
            closedSet.add(currentStateKey);

            // Check if goal reached
            if (current.tree.isBST()) {
                long endTime = System.currentTimeMillis();
                System.out.println("Solution found!");
                System.out.println("Total states explored: " + statesExplored);
                System.out.println("Maximum open set size: " + maxOpenSetSize);
                System.out.println("Solution length: " + current.steps);
                System.out.println("Search completed in " + (endTime - startTime) + " ms");
                return current.path;
            }

            // Generate successor states
            List<Integer> possibleSwaps = current.tree.getPossibleSwaps();
            for (int childIndex : possibleSwaps) {
                int parentIndex = current.tree.getParentIndex(childIndex);
                if (parentIndex == -1) continue;

                BinaryTree newTree = new BinaryTree(current.tree);
                newTree.swap(childIndex, parentIndex);

                String newStateKey = newTree.toString();
                if (closedSet.contains(newStateKey)) {
                    continue;
                }

                List<String> newPath = new ArrayList<>(current.path);
                newPath.add("Swap node at index " + childIndex + " (value " +
                        current.tree.getValue(childIndex) + ") with its parent at index " +
                        parentIndex + " (value " + current.tree.getValue(parentIndex) + ")");

                State newState = new State(newTree, current.steps + 1, newPath);
                openSet.add(newState);
            }
        }

        System.out.println("No solution found within search limits");
        System.out.println("Total states explored: " + statesExplored);
        System.out.println("Maximum open set size: " + maxOpenSetSize);
        return null;
    }

    public static void printSolution(BinaryTree initialTree, List<String> solution) {
        if (solution == null) {
            System.out.println("No solution found");
            return;
        }

        if (solution.isEmpty()) {
            System.out.println("Tree is already a BST");
            return;
        }

        System.out.println("\n=== SOLUTION ===");
        System.out.println("Initial tree:");
        System.out.println(initialTree.toString());
        System.out.print("Tree structure:\n");
        initialTree.printTreeStructure();

        BinaryTree currentTree = new BinaryTree(initialTree);

        for (int i = 0; i < solution.size(); i++) {
            String step = solution.get(i);
            System.out.println("\nStep " + (i + 1) + ": " + step);

            // Extract indices from step description and perform swap
            String[] parts = step.split(" ");
            int childIndex = -1, parentIndex = -1;

            for (int j = 0; j < parts.length; j++) {
                if (parts[j].equals("index") && j + 1 < parts.length) {
                    try {
                        int index = Integer.parseInt(parts[j + 1]);
                        if (childIndex == -1) {
                            childIndex = index;
                        } else {
                            parentIndex = index;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        // Continue searching
                    }
                }
            }

            if (childIndex != -1 && parentIndex != -1) {
                currentTree.swap(childIndex, parentIndex);
                System.out.println("Result: " + currentTree.toString());
            }
        }

        System.out.println("\nFinal BST:");
        System.out.print("Tree structure:\n");
        currentTree.printTreeStructure();
        System.out.println("Is BST: " + currentTree.isBST());
        System.out.println("Total swaps: " + solution.size());
    }
}
package amazon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumDepthOfBinaryTree {
    Node root;

    static class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
            left = right = null;
        }
    }

    static class qItem {
        Node node;
        int depth;

        public qItem(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }


// DFS if scan iteratively use a Queue
    public int minDepth_while(Node root) {
        if (root == null) return 0;
        Queue<qItem> q = new LinkedList<>();

        qItem qi = new qItem(root, 1);
        q.add(qi);

        while (!q.isEmpty()) {
            qi = q.poll();
            Node node = qi.node;
            int depth = qi.depth;
            if (node.left == null && node.right == null) {
                return depth;
            }

            if (node.left != null) {
                qi.node = node.left;
                qi.depth = depth + 1;
                // the value is copied to a new node for the linkedList, so we can reuse the qi, to save some memory
                q.add(qi);
            }
            if (node.right != null) {
                qi.node = node.right;
                qi.depth = depth + 1;
                // the value is copied to a new node for the linkedList, so we can reuse the qi, to save some memory
                q.add(qi);
            }
        }
        return 0;
    }

    // time complexity: O(2N)
    int minDepth_recursive(Node root, int level) {
        if (root == null)
            return level;
        level++;

        return Math.min(minDepth_recursive(root.left, level),
                minDepth_recursive(root.right, level));
    }

    /**
     * Time complexity of above solution is O(2n) as it traverses the tree only once.
     */
    int minDepth_recursive_2(Node root) {
        // Corner case. Should never be hit unless the code is
        // called on root = NULL
        if (root == null)
            return 0;

        // Base case : Leaf Node. This accounts for height = 1.
        if (root.left == null && root.right == null)
            return 1;

        // If left subtree is NULL, recur for right subtree
        if (root.left == null)
            return minDepth_recursive_2(root.right) + 1;

        // If right subtree is NULL, recur for left subtree
        if (root.right == null)
            return minDepth_recursive_2(root.left) + 1;

        return Math.min(minDepth_recursive_2(root.left),
                minDepth_recursive_2(root.right)) + 1;
    }

    // in a nutshell, depth first search ( DFS) use recursive way
    // time: O(2N)
    int maxDepth(Node node) {
        if (node == null) return 0;
        else {
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);
            return Math.max(lDepth, rDepth) + 1;
        }
    }

    /*
     * Time Complexity – O(n^2) Space Complexity – O(1)
     * */
    void printLevelsRecursively(Node root) {
        int height = maxDepth(root);

        // height maybe number of node (n)
        for (int i = 1; i <= height; i++) {
            System.out.print("Level " + i + " : ");
            // recursive call number is 2n
            printSingleLevelRecursively(root, i);
            System.out.println();
        }
        // so totally loop number is 2N + n * 2N = O(n^2)
    }

    void printSingleLevelRecursively(Node root, int level) {
        if (root == null)
            return;

        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printSingleLevelRecursively(root.left, level - 1);
            printSingleLevelRecursively(root.right, level - 1);
        }
    }

    /*Time Complexity – O(n) Space Complexity – O(n)*/
    // Breadth first search : use a queue
    // each loop the number of the queue is at the same level
    // remove the head when access it from the queue, add it's left and right to the queue
    void printLevelsIteratively(Node root) {
        Queue<Node> queue = new LinkedList<>();

        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.print(level + ": ");
            for (int i = 0; i < size; ++i) {
                Node node = queue.peek();
                if (node != null) {
                    System.out.print(node.data + " ");
                    queue.remove();

                    if (node.left != null)
                        queue.add(node.left);

                    if (node.right != null)
                        queue.add(node.right);
                }
            }
            level++;
            System.out.println();
        }
    }

    /*depth first search use stack, here use recursively call */
    void dfs_preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.data + "->");
        dfs_preOrder(node.left);
        dfs_preOrder(node.right);
    }

    void dfs_inOrder(Node node) {
        if (node == null) return;
        dfs_preOrder(node.left);
        System.out.print(node.data + "->");
        dfs_preOrder(node.right);
    }

    void dfs_postOrder(Node node) {
        if (node == null) return;
        dfs_preOrder(node.left);
        dfs_preOrder(node.right);
        System.out.print(node.data + "->");
    }

    @BeforeEach
    public void init() {
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.left.left.left = new Node(6);
        root.left.left.left.left = new Node(7);
    }

    @Test
    public void test_minDepth() {
        assertEquals(minDepth_while(root), 2);
        assertEquals(minDepth_recursive(root, 0), 2);
        assertEquals(minDepth_recursive_2(root), 2);
    }

    @Test
    public void test_maxDepth() {
        assertEquals(maxDepth(root), 5);
    }

    @Test
    public void test_printBFSRecursively() {
        printLevelsRecursively(root);
    }

    @Test
    public void test_printBFSIteratively() {
        printLevelsIteratively(root);
    }

    @Test
    public void test_dfs() {
        System.out.print("preOrder: ");
        dfs_preOrder(root);
        System.out.print("\ninOrder: ");
        dfs_inOrder(root);
        System.out.print("\npostOrder: ");
        dfs_postOrder(root);
    }
}
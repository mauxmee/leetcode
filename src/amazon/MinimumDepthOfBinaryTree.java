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

    int minDepth_recursive(Node root, int level) {
        if (root == null)
            return level;
        level++;

        return Math.min(minDepth_recursive(root.left, level),
                minDepth_recursive(root.right, level));
    }

    /**
     * Time complexity of above solution is O(n) as it traverses the tree only once.
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

    int maxDepth(Node node) {
        if (node == null) return 0;
        else {
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);
            return Math.max(lDepth, rDepth) + 1;
        }
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
}
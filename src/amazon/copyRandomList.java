package amazon;

import java.util.*;

/**
 * 138. Copy List with Random Pointer
 * A linked list of length n is given such that each node contains an additional random pointer,
 * which could point to any node in the list, or null.
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
 * where each new node has its value set to the value of its corresponding original node.
 * Both the next and random pointer of the new nodes should point to new nodes in the copied
 * list such that the pointers in the original list and copied list represent the same list state.
 * None of the pointers in the new list should point to nodes in the original list.
 * For example, if there are two nodes X and Y in the original list, where X.random --> Y,
 * then for the corresponding two nodes x and y in the copied list, x.random --> y.
 * Return the head of the copied linked list.
 * The linked list is represented in the input/output as a list of n nodes. Each node is represented
 * as a pair of [val, random_index] where:
 * •	val: an integer representing Node.val
 * •	random_index: the index of the node (range from 0 to n-1) that the random pointer points to,
 * or null if it does not point to any node.
 * Your code will only be given the head of the original linked list.
 * <p>
 * Example 1:
 * <p>
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Example 2:
 * <p>
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * Example 3:
 * <p>
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * Example 4:
 * Input: head = []
 * Output: []
 * Explanation: The given linked list is empty (null pointer), so return null.
 * <p>
 * Constraints:
 * •	0 <= n <= 1000
 * •	-10000 <= Node.val <= 10000
 * •	Node.random is null or is pointing to some node in the linked list.
 */

class copyRandomList {
    // Definition for a Node.
    public static class Node {
        int val;
        Node next = null;
        Node random = null;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     Runtime: 0 ms, faster than 100.00% of Java online submissions for Copy List with Random Pointer.
     Memory Usage: 38.9 MB, less than 30.51% of Java online submissions for Copy List with Random Pointer.
     */
    public static Node solution(Node head) {
        if (head == null) return null;
        Node copyHead = new Node(0), curr = copyHead;
        int i = 0;
        Map<Node, Integer> oldNodeToIndexMap = new HashMap<>();
        List<Node> oldRandomList = new ArrayList<>();
        List<Node> newNodeList = new ArrayList<>();
        // first round only create the new deep copy of next
        while (head != null) {
            curr.next = new Node(head.val);
            oldNodeToIndexMap.put(head, i);
            oldRandomList.add(head.random);
            curr = curr.next;
            newNodeList.add(curr);
            head = head.next;
            ++i;
        }
        for (i = 0; i < oldRandomList.size(); ++i) {
            Node randomNode = oldRandomList.get(i);
            if (randomNode == null) continue;
            Integer index = oldNodeToIndexMap.get(randomNode);
            if (index != null) {
                newNodeList.get(i).random = newNodeList.get(index);
            }
        }
        return copyHead.next;
    }

    public static void main(String[] args) {
        List<Node> rawNodes = new ArrayList<>(10);
        for (int i = 0; i < 10; ++i) {
            rawNodes.add(new Node(i));
        }
        Random rn = new Random();
        for (int i = 1; i < 10; ++i) {
            rawNodes.get(i - 1).next = rawNodes.get(i);
            rawNodes.get(i - 1).random = rawNodes.get(rn.nextInt(10));
        }
        StringBuffer sb = new StringBuffer();

        Node orig = rawNodes.get(0);
        while (orig != null) {
            sb.append("[ ").append(orig.val).append(",").append((orig.random == null ? "null" : orig.random.val)).append(" ],");
            orig = orig.next;
        }
        sb.append("\n");
        System.out.println(sb);
        sb = new StringBuffer();
        Node copy = solution(rawNodes.get(0));
        while (copy != null) {
            sb.append("[ ").append(copy.val).append(",").append((copy.random == null ? "null" : copy.random.val)).append(" ],");
            copy = copy.next;
        }
        System.out.println(sb);
    }
}

package amazon;

/*23. Merge k Sorted Lists
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []

Constraints:

k == lists.length
0 <= k <= 10^4
0 <= lists[i].length <= 500
-10^4 <= lists[i][j] <= 10^4
lists[i] is sorted in ascending order.
The sum of lists[i].length won't exceed 10^4.
通过次数286,766提交次数516,009*/

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MergeKSortedList {
    public static final int INVALID = Integer.MAX_VALUE;

    public class ListNode {
        int val = INVALID;
        ListNode next = null;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /*
    Runtime: 675 ms, faster than 5.02% of Java online submissions for Merge k Sorted Lists.
    Memory Usage: 40.4 MB, less than 76.74% of Java online submissions for Merge k Sorted Lists.
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) return null;
        ListNode head = new ListNode(), nextNode = head, node = null;
        int n = lists.length;
        List<ListNode> currNodes = new ArrayList<>(n);
        // assign head of each nodes to the list
        for (int i = 0; i < n; i++) {
            currNodes.add(lists[i]);
        }
        do {
            node = getMinNode(currNodes);
            nextNode.next = node;
            nextNode = nextNode.next;
        } while (node != null);
        return head.next;
    }

    private ListNode getMinNode(List<ListNode> currNodes) {
        int index = -1, value = INVALID;
        ListNode n;
        for (int i = 0; i < currNodes.size(); ++i) {
            n = currNodes.get(i);
            if (n != null && n.val < value) {
                index = i;
                value = n.val;
            }
        }
        if (index == -1) return null;
        n = currNodes.get(index);
        currNodes.set(index, n.next);
        return n;
    }

    private String printList(ListNode n) {
        StringBuffer sb = new StringBuffer();
        while (n != null) {
            sb.append(n.val).append(",");
            n = n.next;
        }
        return sb.toString();
    }

    @Test
    public void test_mergeKLists() {
        ListNode n1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode n2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode n3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = {n1, n2, n3};
        System.out.println(printList(mergeKLists(lists)));
    }

    @Test
    public void test_mergeKList_empty() {
        ListNode[] lists = {};
        System.out.println(printList(mergeKLists(lists)));
        ListNode dummy = new ListNode();
        ListNode[] lists2 = {dummy};
        System.out.println(printList(mergeKLists(lists2)));
    }
}
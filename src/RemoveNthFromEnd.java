/**
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * Follow up: Could you do this in one pass?
 * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * Example 2:
 * Input: head = [1], n = 1
 * Output: []
 * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 * Constraints:
 * <p>
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 */

class RemoveNthFromEnd {
    public static void main(String[] args) {
        int[] raw = {1, 2, 3, 4, 5};
        ListNode l = makeList(raw);
        printList(l);
        printList(solution2(l, 2));

        int[] raw2 = {1};
        l = makeList(raw2);
        printList(l);
        printList(solution2(l, 1));

        int[] raw3 = {1, 2};
        l = makeList(raw3);
        printList(l);
        printList(solution2(l, 2));
    }

    public static ListNode solution(ListNode head, int n) {
        if (head == null) return null;
        ListNode cur = head;
        int count = 0;
        while (cur != null) {
            cur = cur.next;
            ++count;
        }
        int remain = count - n;

        if (remain < 0) return null;
        else if (remain == 0) return head.next;
        cur = head;
        int c2 = 0;
        while (cur != null && c2 < remain - 1) {
            cur = cur.next;
            ++c2;
        }
        if (cur != null) {
            ListNode target = cur.next;
            if (target != null) {
                target = target.next;
            }
            cur.next = target;
        }
        return head;
    }

    public static ListNode solution2(ListNode head, int n) {
        if (head == null) return null;
        ListNode p1 = head, p2 = head;
        int count = 0;
        // skip the first n element.
        while (p1 != null & count < n) {
            p1 = p1.next;
            ++count;
        }
        // reach end before N elements
        if (count < n) return null;
        else if (p1 == null) return head.next;
        p1 = p1.next;
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        // now remove the p2
        ListNode target = p2.next;
        if (target != null) {
            target = target.next;
        }
        p2.next = target;
        return head;
    }

    public static class ListNode {
        int val = 0;
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

    public static ListNode makeList(int[] v) {
        ListNode head = new ListNode(), cur = head;
        for (final int j : v) {
            cur.next = new ListNode(j);
            cur = cur.next;
        }
        return head.next;
    }

    public static void printList(ListNode l) {
        StringBuffer sb = new StringBuffer();
        while (l != null) {
            sb.append(l.val).append(",");
            l = l.next;
        }
        System.out.println(sb);
    }
}
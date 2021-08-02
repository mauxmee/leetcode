package normal;/*
* You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse
*  order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.
*  example: 2 -> 4 -> 3 ; 5 -> 6 -> 4, result is 7 -> 0 -> 8
* Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
*
* Constraints:

The number of nodes in each linked list is in the range [1, 100].
0 <= Node.val <= 9
It is guaranteed that the list represents a number that does not have leading zeros.
*
*  * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
* */

import java.util.Scanner;

public class addTwoNumbers {
    // input the link list one, and two, they may not be the same length
    // check the first element, make sure it's not leading zero, otherwise warning and quit
    // check each element, make sure it's within [0,9], add them and put to result link list
    // if result is over 10, create next link list node and put the 1 there first, go to next one,
    // if one list finishes, go through the other one until all adds up.

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

    public static ListNode getUserInput(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        ListNode head = new ListNode(), current = head, prev = null;
        do {
            try {
                int n = scanner.nextInt();
                if (n < 0 || n > 9) {
                    System.out.println("number must be within 0 and 9, try again");
                    continue;
                }
                if (current == null) {
                    current = new ListNode(n);
                    prev.next = current;
                } else {
                    current.val = n;
                }
                prev = current;
                current = current.next;
            } catch (Exception e) {
                System.out.println("finished input.");
                printList(head);
                break;
            }
        } while (true);
        return head;
    }

    public static boolean valid(ListNode n) {
        return n != null && n.val != -1;
    }

    public static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        while (valid(current)) {
            sb.append(current.val).append(",");
            current = current.next;
        }
        System.out.println(sb);
    }

    private static ListNode addOne(ListNode currentNode, ListNode prevNode, int sum) {
        int carry = 0;
        if (currentNode != null) {
            sum += currentNode.val;
        }
        if (sum > 9) {
            carry = sum / 10;
            sum = sum % 10;
        }
        if (currentNode == null) {
            currentNode = new ListNode(sum);
            if (prevNode != null) {
                prevNode.next = currentNode;
            }
        } else {
            currentNode.val = sum;
        }
        if (carry > 0) {
            currentNode.next = new ListNode(carry);
        }
        return currentNode.next;
    }

    public static void add(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            System.out.println("Invalid input. exit");
            return;
        }
        ListNode n1 = l1, n2 = l2;
        ListNode result = new ListNode(), currentNode = result, prevNode = null;
        while (valid(n1) && valid(n2)) {
            ListNode nextNode = addOne(currentNode, prevNode, n1.val + n2.val);
            if (currentNode == null && prevNode != null) {
                currentNode = prevNode.next;
            }
            prevNode = currentNode;
            currentNode = nextNode;
            n1 = n1.next;
            n2 = n2.next;
           // printList(result);
        }
        ListNode n3 = valid(n1) ? n1 : valid(n2) ? n2 : null;
        while (valid(n3)) {
            ListNode nextNode = addOne(currentNode, prevNode, n3.val);
            if (currentNode == null && prevNode != null) {
                currentNode = prevNode.next;
            }
            prevNode = currentNode;
            currentNode = nextNode;
            n3 = n3.next;
            //printList(result);
        }
        printList(result);
    }

    public static void add2(ListNode l1, ListNode l2) {

        ListNode currL1 = l1;
        ListNode currL2 = l2;

        //create dummy node, it just a placeholder
        //to hold a head of linkedlist
        ListNode dummyNode = new ListNode();
        ListNode curr = dummyNode;

        int carry = 0;


        while(currL1 != null || currL2 != null){
            int sum = carry;

            if(currL1 != null){
                sum += currL1.val;
                currL1 = currL1.next;
            }


            if(currL2 != null){
                sum += currL2.val;
                currL2 = currL2.next;
            }

            int curentDigit = sum % 10;
            carry = sum / 10;

            //craete new node, with current digit
            curr.next = new ListNode(curentDigit);

            //move to next node
            curr = curr.next;
        }

        //if carry left,
        //add new ncarrry node
        if(carry == 1){
            curr.next = new ListNode(1);
        }

        printList(dummyNode.next);
    }
    public static void main(String[] args) {
        ListNode list1 = getUserInput("input list 1");
        ListNode list2 = getUserInput("input list 2");
        //add(list1, list2);
        add2(list1, list2);
    }

}
package CheatSheet.BinaryTree;

import org.junit.jupiter.api.Test;

import java.util.*;


public class BinaryTreeOps {
    //Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public BinaryTreeOps() {
    }
    /*
	105. Construct Binary Tree from Preorder and Inorder Traversal
	Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree
	and inorder is the inorder traversal of the same tree, construct and return the binary tree.

	Example 1:
	Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
	Output: [3,9,20,null,null,15,7]

	Example 2:
	Input: preorder = [-1], inorder = [-1]
	Output: [-1]


	Constraints:
	1 <= preorder.length <= 3000
	inorder.length == preorder.length
	-3000 <= preorder[i], inorder[i] <= 3000
	preorder and inorder consist of unique values.
	Each value of inorder also appears in preorder.
	preorder is guaranteed to be the preorder traversal of the tree.
	inorder is guaranteed to be the inorder traversal of the tree.
	通过次数223,497提交次数318,447
	 */

    /*
    二叉树前序遍历的顺序为： PreOrder
    - 先遍历根节点；root
    - 随后递归地遍历左子树； left tree
    - 最后递归地遍历右子树。 right tree

    二叉树中序遍历的顺序为： InOrder
    - 先递归地遍历左子树； left tree
    - 随后遍历根节点； Root
    - 最后递归地遍历右子树。Right tree

    在「递归」地遍历某个子树的过程中，我们也是将这颗子树看成一颗全新的树，按照上述的顺序进行遍历。
    挖掘「前序遍历」和「中序遍历」的性质，我们就可以得出本题的做法。

    方法一：递归 Recursive
    对于任意一颗树而言，前序遍历的形式总是
    [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
    即根节点总是前序遍历中的第一个节点。而中序遍历的形式总是
    [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
    只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目。
    由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，因此我们就可以对应到前序遍历的结果中，
    对上述形式中的所有左右括号进行定位。

    这样以来，我们就知道了左子树的前序遍历和中序遍历结果，以及右子树的前序遍历和中序遍历结果，
    我们就可以递归地对构造出左子树和右子树，再将这两颗子树接到根节点的左右位置。

    细节 Implementation details
    在中序遍历中对根节点进行定位时，一种简单的方法是直接扫描整个中序遍历的结果并找出根节点，
    但这样做的时间复杂度较高。我们可以考虑使用哈希表来帮助我们快速地定位根节点。
    use HashMap to allocate root node quickly. key: node : value: location in InOrder search
    对于哈希映射中的每个键值对，键表示一个元素（节点的值），值表示其在中序遍历中的出现位置。
    在构造二叉树的过程之前，我们可以对中序遍历的列表进行一遍扫描，就可以构造出这个哈希映射。
    在此后构造二叉树的过程中，我们就只需要 O(1)O(1) 的时间对根节点进行定位了。

    下面的代码给出了详细的注释。

    时间复杂度：O(n)，其中 n 是树中的节点个数。
    空间复杂度：O(n)，除去返回的答案需要的 O(n) 空间之外，我们还需要使用O(n) 的空间存储哈希映射，
    以及 O(h)（其中 h 是树的高度）的空间表示递归时栈空间。这里 h<n，所以总空间复杂度为 O(n)。

         */

    private Map<Integer, Integer> indexMap;

    public TreeNode ConstructBinaryTreeFromPreorderAndInorderTraversal_recursive
            (int[] preorder, int[] inorder) {
        int n = preorder.length;
        // construct hashmap to quickly allocate root node
        indexMap = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            indexMap.put(inorder[i], i);
        }
        return buildTree1(preorder, inorder,
                0, n - 1, 0, n - 1);
    }

    private TreeNode buildTree1(int[] preorder, int[] inorder,
                                int preorder_left, int preorder_right,
                                int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) return null;
        // the first node for preorder result is the root node
        int preorder_root = preorder_left;
        // allocate the root node in inorder result, this is O(1)
        int rootValue = preorder[preorder_root];
        int inorder_root = indexMap.get(rootValue);
        // build the root node first
        TreeNode root = new TreeNode(rootValue);
        // get the node number of left sub tree
        int size_left_subtree = inorder_root - inorder_left;
        // recursively construct the left sub tree, and connect to the root node
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了
        // 中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = buildTree1(preorder, inorder,
                preorder_left + 1, preorder_left + size_left_subtree,
                inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素
        // 就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = buildTree1(preorder, inorder,
                preorder_left + size_left_subtree + 1, preorder_right,
                inorder_root + 1, inorder_right);
        return root;
    }

    /*
    方法二：迭代
    kxu: This is too hard to understand, so maybe just as reference here.
    思路

    迭代法是一种非常巧妙的实现方法。
    对于前序遍历中的任意两个连续节点 u 和 v，根据前序遍历的流程，我们可以知道 u 和 v 只有两种可能的关系：
    v 是 u 的左儿子。这是因为在遍历到 u 之后，下一个遍历的节点就是 u 的左儿子，即 v；
    u 没有左儿子，并且 v 是 u 的某个祖先节点（或者 u 本身）的右儿子。如果 u 没有左儿子，那么下一个遍历的节点就是 u 的右儿子。
    如果 u 没有右儿子，我们就会向上回溯，直到遇到第一个有右儿子（且 u 不在它的右儿子的子树中）的节点 u_a，那么 v 就是 u_a的右儿子。

    第二种关系看上去有些复杂。我们举一个例子来说明其正确性，并在例子中给出我们的迭代算法。
    例子
    我们以树

            3
           / \
          9  20
         /  /  \
        8  15   7
       / \
      5  10
     /
    4
    为例，它的前序遍历和中序遍历分别为
    preorder = [3, 9, 8, 5, 4, 10, 20, 15, 7]
    inorder = [4, 5, 8, 10, 9, 3, 15, 20, 7]
    我们用一个栈 stack 来维护「当前节点的所有还没有考虑过右儿子的祖先节点」，
    栈顶就是当前节点。也就是说，只有在栈中的节点才可能连接一个新的右儿子。
    同时，我们用一个指针 index 指向中序遍历的某个位置，初始值为 0。
    index 对应的节点是「当前节点不断往左走达到的最终节点」，这也是符合中序遍历的，
    它的作用在下面的过程中会有所体现。

    首先我们将根节点 3 入栈，再初始化 index 所指向的节点为 4
    ，随后对于前序遍历中的每个节点，我们依次判断它是栈顶节点的左儿子，还是栈中某个节点的右儿子。

    我们遍历 9。9 一定是栈顶节点 3 的左儿子。我们使用反证法，假设 9 是 3 的右儿子，那么 3 没有左儿子，index 应该恰好指向 3，但实际上为 4
    ，因此产生了矛盾。所以我们将 9 作为 3 的左儿子，并将 9 入栈。

    stack = [3, 9]
    index -> inorder[0] = 4
    我们遍历 8，5 和 4。同理可得它们都是上一个节点（栈顶节点）的左儿子，所以它们会依次入栈。

    stack = [3, 9, 8, 5, 4]
    index -> inorder[0] = 4
    我们遍历 10，这时情况就不一样了。我们发现 index 恰好指向当前的栈顶节点 4，也就是说 4 没有左儿子
    ，那么 10 必须为栈中某个节点的右儿子。那么如何找到这个节点呢？栈中的节点的顺序和它们在前序遍历中出现的顺序是一致的，
    而且每一个节点的右儿子都还没有被遍历过，那么这些节点的顺序和它们在中序遍历中出现的顺序一定是相反的。

    这是因为栈中的任意两个相邻的节点，前者都是后者的某个祖先。并且我们知道，栈中的任意一个节点的右儿子还没有被遍历过，
    说明后者一定是前者左儿子的子树中的节点，那么后者就先于前者出现在中序遍历中。

    因此我们可以把 index 不断向右移动，并与栈顶节点进行比较。如果 index 对应的元素恰好等于栈顶节点，那么说明我们在中序遍历中找到了栈顶节点，
    所以将 index 增加 1 并弹出栈顶节点，直到 index 对应的元素不等于栈顶节点。按照这样的过程，我们弹出的最后一个节点 x 就是 10 的双亲节点，
    这是因为 10 出现在了 x 与 x 在栈中的下一个节点的中序遍历之间，因此 10 就是 x 的右儿子。

    回到我们的例子，我们会依次从栈顶弹出 4，5 和 8，并且将 index 向右移动了三次。我们将 10 作为最后弹出的节点 8 的右儿子，并将 10 入栈。

    stack = [3, 9, 10]
    index -> inorder[3] = 10
    我们遍历 20。同理，index 恰好指向当前栈顶节点 10，那么我们会依次从栈顶弹出 10，9 和 3，
    并且将 index 向右移动了三次。我们将 20 作为最后弹出的节点 3 的右儿子，并将 20 入栈。

    stack = [20]
    index -> inorder[6] = 15
    我们遍历 15，将 15 作为栈顶节点 20 的左儿子，并将 15 入栈。

    stack = [20, 15]
    index -> inorder[6] = 15
    我们遍历 7。index 恰好指向当前栈顶节点 15，那么我们会依次从栈顶弹出 15 和 20，
    并且将 index 向右移动了两次。我们将 7 作为最后弹出的节点 20 的右儿子，并将 7 入栈。

    stack = [7]
    index -> inorder[8] = 7
    此时遍历结束，我们就构造出了正确的二叉树。

    算法

    我们归纳出上述例子中的算法流程：

    我们用一个栈和一个指针辅助进行二叉树的构造。初始时栈中存放了根节点（前序遍历的第一个节点），指针指向中序遍历的第一个节点；

    我们依次枚举前序遍历中除了第一个节点以外的每个节点。如果 index 恰好指向栈顶节点，那么我们不断地弹出栈顶节点并向右移动 index，
    并将当前节点作为最后一个弹出的节点的右儿子；如果 index 和栈顶节点不同，我们将当前节点作为栈顶节点的左儿子；

    无论是哪一种情况，我们最后都将当前的节点入栈。

    最后得到的二叉树即为答案。
复杂度分析

时间复杂度：O(n)，其中 n 是树中的节点个数。
空间复杂度：O(n)，除去返回的答案需要的 O(n) 空间之外，我们还需要使用 O(h)（其中 h 是树的高度）的空间存储栈。这里 h<n，
所以（在最坏情况下）总空间复杂度为 O(n)。

     */
    public TreeNode buildTree_iterative(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        int inorderIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }


    /* 199 Medium
    KXU: this is typical BFS algo

    Given the root of a binary tree, imagine yourself standing on the right side of it,
    return the values of the nodes you can see ordered from top to bottom.

    Example 1:
    Input: root = [1,2,3,null,5,null,4]
    Output: [1,3,4]

    Example 2:
    Input: root = [1,null,3]
    Output: [1,3]

    Example 3:
    Input: root = []
    Output: []


    Constraints:
    The number of nodes in the tree is in the range [0, 100].
    -100 <= Node.val <= 100

    链接：https://leetcode-cn.com/problems/binary-tree-right-side-view
    Runtime: 1 ms, faster than 77.19% of Java online submissions for Binary Tree Right Side View.
Memory Usage: 37.9 MB, less than 31.46% of Java online submissions for Binary Tree Right Side View.
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        // at first I used the queue, but it's hard to get the last element.
        // so I use the linked list instead.
        LinkedList<TreeNode> levelNodes = new LinkedList<>();
        levelNodes.add(root);
        while (!levelNodes.isEmpty()) {
            int size = levelNodes.size();
            // get the last node's value
            res.add(levelNodes.getLast().val);
            for (int i = 0; i < size; i++) {
                TreeNode n = levelNodes.peekFirst();
                if (n != null) {
                    levelNodes.removeFirst();
                    if (n.left != null) {
                        levelNodes.add(n.left);
                    }
                    if (n.right != null) {
                        levelNodes.add(n.right);
                    }
                }
            }
        }
        return res;
    }

    /*
    103. Binary Tree Zigzag Level Order Traversal median level
    Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
    (i.e., from left to right, then right to left for the next level and alternate between).

    Example 1:
    Input: root = [3,9,20,null,null,15,7]
    Output: [[3],[20,9],[15,7]]

    Example 2:
    Input: root = [1]
    Output: [[1]]

    Example 3:
    Input: root = []
    Output: []

    Constraints:
    The number of nodes in the tree is in the range [0, 2000].
    -100 <= Node.val <= 100
    通过次数157,599提交次数275,808
     */
    @Test
    public void test_zigzagLevelOrder() {
        TreeNode root = new TreeNode(3,
                new TreeNode(9), new TreeNode(20,
                new TreeNode(15), new TreeNode(7)));
        System.out.println(zigzagLevelOrder(root));
    }
//Runtime: 1 ms, faster than 75.34% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
//Memory Usage: 39.3 MB, less than 21.95% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        // at first I used the queue, but it's hard to get the last element.
        // so I use the linked list instead.
        Queue<TreeNode> levelNodes = new LinkedList<>();
        boolean direction = false;
        levelNodes.add(root);
        while (!levelNodes.isEmpty()) {
            List<Integer> levelRes = new ArrayList<>();
            int size = levelNodes.size();
            // get the last node's value
            for (int i = 0; i < size; i++) {
                TreeNode n = levelNodes.peek();
                if (n != null) {
                    levelNodes.remove();
                    levelRes.add(n.val);
                    if (n.left != null) {
                        levelNodes.add(n.left);
                    }
                    if (n.right != null) {
                        levelNodes.add(n.right);
                    }
                }
            }
            if (direction) {
                Collections.reverse(levelRes);
            }
            res.add(levelRes);
            direction = !direction;
        }
        return res;
    }

    /*
    124. Binary Tree Maximum Path Sum
    A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has
     an edge connecting them. A node can only appear in the sequence at most once. Note that the path
     does not need to pass through the root.

    The path sum of a path is the sum of the node's values in the path.
    Given the root of a binary tree, return the maximum path sum of any path.

    Example 1:
    Input: root = [1,2,3]
    Output: 6
    Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

    Example 2:
    Input: root = [-10,9,20,null,null,15,7]
    Output: 42
    Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.

    Constraints:
    The number of nodes in the tree is in the range [1, 3 * 104].
    -1000 <= Node.val <= 1000
    通过次数140,825提交次数319,362
     */
    public int maxPathSum(TreeNode root) {
        return 0;
    }

    /*
    104. Maximum Depth of Binary Tree
    Given the root of a binary tree, return its maximum depth.
    A binary tree's maximum depth is the number of nodes along the longest path
     from the root node down to the farthest leaf node.

    Example 1:
    Input: root = [3,9,20,null,null,15,7]
    Output: 3

    Example 2:
    Input: root = [1,null,2]
    Output: 2

    Example 3:
    Input: root = []
    Output: 0

    Example 4:
    Input: root = [0]
    Output: 1

    Constraints:
    The number of nodes in the tree is in the range [0, 104].
    -100 <= Node.val <= 100
    通过次数468,799提交次数613,129
     */
    public int maxDepth(TreeNode root) {
        return 0;
    }

    /*
    98. Validate Binary Search Tree
    Given the root of a binary tree, determine if it is a valid binary search tree (BST).

    A valid BST is defined as follows:
    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.

    Example 1:
    Input: root = [2,1,3]
    Output: true

    Example 2:
    Input: root = [5,1,4,null,null,3,6]
    Output: false
    Explanation: The root node's value is 5 but its right child's value is 4.

    Constraints:
    The number of nodes in the tree is in the range [1, 104].
    -231 <= Node.val <= 231 - 1
    通过次数311,449提交次数898,477
         */
    public boolean isValidBST(TreeNode root) {
        return true;
    }

    /*
    96. Unique Binary Search Trees
    Given an integer n, return the number of structurally unique BST's (binary search trees)
    which has exactly n nodes of unique values from 1 to n.

    Example 1:
    Input: n = 3
    Output: 5

    Example 2:
    Input: n = 1
    Output: 1

    Constraints:
    1 <= n <= 19
    通过次数144,740提交次数207,176
     */
    public int numTrees(int n) {
        return 0;
    }

    /*
    543. Diameter of Binary Tree
    Given the root of a binary tree, return the length of the diameter of the tree.
    The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
     This path may or may not pass through the root.
    The length of a path between two nodes is represented by the number of edges between them.

    Example 1:
    Input: root = [1,2,3,4,5]
    Output: 3
    Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].

    Example 2:
    Input: root = [1,2]
    Output: 1

    Constraints:
    The number of nodes in the tree is in the range [1, 104].
    -100 <= Node.val <= 100
    通过次数133,576提交次数244,954
     */
    public int diameterOfBinaryTree(TreeNode root) {
        return 0;
    }

    /*
    102. Binary Tree Level Order Traversal
    Given the root of a binary tree, return the level order traversal of its nodes' values.
    (i.e., from left to right, level by level).

    Example 1:
    Input: root = [3,9,20,null,null,15,7]
    Output: [[3],[9,20],[15,7]]

    Example 2:
    Input: root = [1]
    Output: [[1]]

    Example 3:
    Input: root = []
    Output: []

    Constraints:
    The number of nodes in the tree is in the range [0, 2000].
    -1000 <= Node.val <= 1000
    通过次数355,560提交次数553,793
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        return null;
    }

    /*606. Construct String from Binary Tree (easy)
    Given the root of a binary tree, construct a string consists of parenthesis and integers
    from a binary tree with the preorder traversing way, and return it.
    Omit all the empty parenthesis pairs that do not affect the one-to-one mapping relationship
     between the string and the original binary tree.

    Example 1:
    Input: root = [1,2,3,4]
    Output: "1(2(4))(3)"
    Explanation: Originallay it needs to be "1(2(4)())(3()())", but you need to omit all the unnecessary
    empty parenthesis pairs. And it will be "1(2(4))(3)"

    Example 2:
    Input: root = [1,2,3,null,4]
    Output: "1(2()(4))(3)"
    Explanation: Almost the same as the first example, except we cannot omit the first parenthesis
    pair to break the one-to-one mapping relationship between the input and the output.

    Constraints:
    The number of nodes in the tree is in the range [1, 104].
    -1000 <= Node.val <= 1000
    通过次数26,773提交次数47,207
     */
    public String tree2str(TreeNode root) {
        return null;
    }
}
package CheatSheet.BinaryTree;

import java.util.List;


public class BinaryTreeOps
{
	//Definition for a binary tree node.
	public class TreeNode
	{
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}

		TreeNode( int val ) {this.val = val;}

		TreeNode( int val, TreeNode left, TreeNode right )
		{
			this.val = val;
			this.left = left;
			this.right = right;
		}
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
	public TreeNode ConstructBinaryTreeFromPreorderAndInorderTraversal( int[] preorder, int[] inorder )
	{
		return null;
	}

	/* 199
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
	 */
	public List<Integer> rightSideView( TreeNode root )
	{
		return null;
	}

	/*
	103. Binary Tree Zigzag Level Order Traversal
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
	public List<List<Integer>> zigzagLevelOrder( TreeNode root )
	{
		return null;
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
	public int maxPathSum( TreeNode root )
	{
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
	public int maxDepth( TreeNode root )
	{
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
	public boolean isValidBST( TreeNode root )
	{
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
	public int numTrees( int n )
	{
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
	public int diameterOfBinaryTree( TreeNode root )
	{
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
	public List<List<Integer>> levelOrder( TreeNode root )
	{
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
	public String tree2str( TreeNode root )
	{
		return null;
	}
}

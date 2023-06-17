package com.andreymironov.leetcode;

import com.andreymironov.leetcode.domain.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeUtils {
    /**
     * @param p
     * @param q
     * @return true if p,q are equal as the tree nodes
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null ^ q == null) {
            return false;
        } else if (p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return false;
        }
    }

    /**
     * @param root
     * @return true if root is symmetric related to middle axis
     */
    public static boolean isSymmetric(TreeNode root) {
        return areSymmetric(root.left, root.right);
    }

    private static boolean areSymmetric(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p.val != q.val) {
            return false;
        }

        return areSymmetric(p.left, q.right) && areSymmetric(p.right, q.left);
    }

    /**
     * @param root
     * @return true if the depth of 2 subtrees of every node never differs by more than 1
     */
    public static boolean isBalanced(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return true;
        }

        if (root.left == null) {
            return getHeight(root.right) == 0;
        }

        if (root.right == null) {
            return getHeight(root.left) == 0;
        }

        if (Math.abs(getHeight(root.left) - getHeight(root.right)) > 1) {
            return false;
        }

        return isBalanced(root.left) && isBalanced(root.right);
    }

    private static int getHeight(TreeNode node) {
        if (node == null || (node.left == null && node.right == null)) {
            return 0;
        }

        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }


    /**
     * @param root
     * @param targetSum
     * @return all values of root-to-leaf paths, which add up to targetSum
     */
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        if (root.left == null && root.right == null) {
            if (root.val == targetSum) {
                result.add(new ArrayList<>(List.of(root.val)));
            }

            return result;
        }


        if (root.left != null) {
            List<List<Integer>> leftResult = pathSum(root.left, targetSum - root.val);
            leftResult.forEach(path -> {
                path.add(0, root.val);
                result.add(path);
            });
        }

        if (root.right != null) {
            List<List<Integer>> rightResult = pathSum(root.right, targetSum - root.val);
            rightResult.forEach(path -> {
                path.add(0, root.val);
                result.add(path);
            });
        }

        return result;
    }
}
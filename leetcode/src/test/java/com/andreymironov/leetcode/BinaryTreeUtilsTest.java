package com.andreymironov.leetcode;

import com.andreymironov.leetcode.domain.TreeNode;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BinaryTreeUtilsTest {
    @Test
    void should_be_symmetric() {
        TreeNode left = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        TreeNode right = new TreeNode(1, new TreeNode(3), new TreeNode(2));
        TreeNode node = new TreeNode(0, left, right);
        Assertions.assertThat(BinaryTreeUtils.isSymmetric(node)).isTrue();
    }

    @Test
    void should_not_be_symmetric() {
        TreeNode left = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        TreeNode right = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        TreeNode node = new TreeNode(0, left, right);
        Assertions.assertThat(BinaryTreeUtils.isSymmetric(node)).isFalse();
    }

    @Test
    void should_compare() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(BinaryTreeUtils.isSameTree(new TreeNode(), new TreeNode())).isTrue();
            softAssertions.assertThat(BinaryTreeUtils.isSameTree(new TreeNode(1), new TreeNode(2))).isFalse();
        });
    }

    @Test
    void should_be_balanced() {
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2, new TreeNode(), null);
        TreeNode node = new TreeNode(0, left, right);
        Assertions.assertThat(BinaryTreeUtils.isBalanced(node)).isTrue();
    }

    @Test
    void should_not_be_balanced() {
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2, new TreeNode(3, new TreeNode(), null), null);
        TreeNode node = new TreeNode(0, left, right);
        Assertions.assertThat(BinaryTreeUtils.isBalanced(node)).isFalse();
    }

    @Test
    void should_not_be_balanced_2() {
        TreeNode right = new TreeNode(2, null, new TreeNode(3, null, null));
        TreeNode node = new TreeNode(1, null, right);
        Assertions.assertThat(BinaryTreeUtils.isBalanced(node)).isFalse();
    }

    @Test
    void should_not_find_path_sum() {
        TreeNode node = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        Assertions.assertThat(BinaryTreeUtils.pathSum(node, 5)).isEmpty();
    }

    @Test
    void should_find_path_sum() {
        TreeNode node = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        Assertions.assertThat(BinaryTreeUtils.pathSum(node, 3)).containsExactly(List.of(1, 2));
    }
}
package com.andreymironov.algorithms;

import java.util.Arrays;

public class NonDuplicateElementSearchUtils {
    /**
     * @param nums - array with all elements duplicated except one
     * @return index of target or -1 if array doesn't contain target
     */
    public static int getNonDuplicate(int[] nums) {
        return Arrays.stream(nums).reduce(0, (i, j) -> i ^ j);
    }
}

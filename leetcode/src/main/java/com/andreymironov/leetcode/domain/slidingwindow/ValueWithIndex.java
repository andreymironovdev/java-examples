package com.andreymironov.leetcode.domain.slidingwindow;

public record ValueWithIndex(int value, int index) implements Comparable<ValueWithIndex> {
    @Override
    public int compareTo(ValueWithIndex o) {
        return this.value == o.value
                ? Integer.compare(this.index, o.index)
                : Integer.compare(this.value, o.value);
    }
}

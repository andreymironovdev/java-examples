package com.andreymironov.algorithms;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class WordsUtilsTest {
    @Test
    void should_find_top_k_using_priority_queue() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(
                            WordsUtils.topKFrequentUsingPriorityQueue(new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2))
                    .containsExactly("i", "love");
            softAssertions.assertThat(
                            WordsUtils.topKFrequentUsingPriorityQueue(
                                    new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4))
                    .containsExactly("the", "is", "sunny", "day");
            softAssertions.assertThat(
                            WordsUtils.topKFrequentUsingPriorityQueue(
                                    new String[]{"glarko", "zlfiwwb", "nsfspyox", "pwqvwmlgri", "qggx", "qrkgmliewc", "zskaqzwo", "zskaqzwo", "ijy", "htpvnmozay", "jqrlad", "ccjel", "qrkgmliewc", "qkjzgws", "fqizrrnmif", "jqrlad", "nbuorw", "qrkgmliewc", "htpvnmozay", "nftk", "glarko", "hdemkfr", "axyak", "hdemkfr", "nsfspyox", "nsfspyox", "qrkgmliewc", "nftk", "nftk", "ccjel", "qrkgmliewc", "ocgjsu", "ijy", "glarko", "nbuorw", "nsfspyox", "qkjzgws", "qkjzgws", "fqizrrnmif", "pwqvwmlgri", "nftk", "qrkgmliewc", "jqrlad", "nftk", "zskaqzwo", "glarko", "nsfspyox", "zlfiwwb", "hwlvqgkdbo", "htpvnmozay", "nsfspyox", "zskaqzwo", "htpvnmozay", "zskaqzwo", "nbuorw", "qkjzgws", "zlfiwwb", "pwqvwmlgri", "zskaqzwo", "qengse", "glarko", "qkjzgws", "pwqvwmlgri", "fqizrrnmif", "nbuorw", "nftk", "ijy", "hdemkfr", "nftk", "qkjzgws", "jqrlad", "nftk", "ccjel", "qggx", "ijy", "qengse", "nftk", "htpvnmozay", "qengse", "eonrg", "qengse", "fqizrrnmif", "hwlvqgkdbo", "qengse", "qengse", "qggx", "qkjzgws", "qggx", "pwqvwmlgri", "htpvnmozay", "qrkgmliewc", "qengse", "fqizrrnmif", "qkjzgws", "qengse", "nftk", "htpvnmozay", "qggx", "zlfiwwb", "bwp", "ocgjsu", "qrkgmliewc", "ccjel", "hdemkfr", "nsfspyox", "hdemkfr", "qggx", "zlfiwwb", "nsfspyox", "ijy", "qkjzgws", "fqizrrnmif", "qkjzgws", "qrkgmliewc", "glarko", "hdemkfr", "pwqvwmlgri"},
                                    14))
                    .containsExactly("nftk", "qkjzgws", "qrkgmliewc", "nsfspyox", "qengse", "htpvnmozay", "fqizrrnmif",
                            "glarko", "hdemkfr", "pwqvwmlgri", "qggx", "zskaqzwo", "ijy", "zlfiwwb");
            softAssertions.assertThat(
                            WordsUtils.topKFrequentUsingPriorityQueue(
                                    new String[]{"i", "love", "leetcode", "i", "love", "coding"},
                                    3))
                    .containsExactly("i","love","coding");
        });
    }

    @Test
    void should_find_top_k() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(
                            WordsUtils.topKFrequent(new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2))
                    .containsExactly("i", "love");
            softAssertions.assertThat(
                            WordsUtils.topKFrequent(
                                    new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4))
                    .containsExactly("the", "is", "sunny", "day");
            softAssertions.assertThat(
                            WordsUtils.topKFrequent(
                                    new String[]{"glarko", "zlfiwwb", "nsfspyox", "pwqvwmlgri", "qggx", "qrkgmliewc", "zskaqzwo", "zskaqzwo", "ijy", "htpvnmozay", "jqrlad", "ccjel", "qrkgmliewc", "qkjzgws", "fqizrrnmif", "jqrlad", "nbuorw", "qrkgmliewc", "htpvnmozay", "nftk", "glarko", "hdemkfr", "axyak", "hdemkfr", "nsfspyox", "nsfspyox", "qrkgmliewc", "nftk", "nftk", "ccjel", "qrkgmliewc", "ocgjsu", "ijy", "glarko", "nbuorw", "nsfspyox", "qkjzgws", "qkjzgws", "fqizrrnmif", "pwqvwmlgri", "nftk", "qrkgmliewc", "jqrlad", "nftk", "zskaqzwo", "glarko", "nsfspyox", "zlfiwwb", "hwlvqgkdbo", "htpvnmozay", "nsfspyox", "zskaqzwo", "htpvnmozay", "zskaqzwo", "nbuorw", "qkjzgws", "zlfiwwb", "pwqvwmlgri", "zskaqzwo", "qengse", "glarko", "qkjzgws", "pwqvwmlgri", "fqizrrnmif", "nbuorw", "nftk", "ijy", "hdemkfr", "nftk", "qkjzgws", "jqrlad", "nftk", "ccjel", "qggx", "ijy", "qengse", "nftk", "htpvnmozay", "qengse", "eonrg", "qengse", "fqizrrnmif", "hwlvqgkdbo", "qengse", "qengse", "qggx", "qkjzgws", "qggx", "pwqvwmlgri", "htpvnmozay", "qrkgmliewc", "qengse", "fqizrrnmif", "qkjzgws", "qengse", "nftk", "htpvnmozay", "qggx", "zlfiwwb", "bwp", "ocgjsu", "qrkgmliewc", "ccjel", "hdemkfr", "nsfspyox", "hdemkfr", "qggx", "zlfiwwb", "nsfspyox", "ijy", "qkjzgws", "fqizrrnmif", "qkjzgws", "qrkgmliewc", "glarko", "hdemkfr", "pwqvwmlgri"},
                                    14))
                    .containsExactly("nftk", "qkjzgws", "qrkgmliewc", "nsfspyox", "qengse", "htpvnmozay", "fqizrrnmif",
                            "glarko", "hdemkfr", "pwqvwmlgri", "qggx", "zskaqzwo", "ijy", "zlfiwwb");
            softAssertions.assertThat(
                            WordsUtils.topKFrequent(
                                    new String[]{"i", "love", "leetcode", "i", "love", "coding"},
                                    3))
                    .containsExactly("i","love","coding");
        });
    }
}
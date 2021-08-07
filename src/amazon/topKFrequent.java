package amazon;

/**
 * 692. Top K Frequent Words
 * Given a non-empty list of words, return the k most frequent elements.
 * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency,
 * then the word with the lower alphabetical order comes first.
 * <p>
 * Example 1:
 * Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words.
 * Note that "i" comes before "love" due to a lower alphabetical order.
 * <p>
 * Example 2:
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["the", "is", "sunny", "day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 * with the number of occurrence being 4, 3, 2 and 1 respectively.
 * <p>
 * Note:
 * 1.	You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * 2.	Input words contain only lowercase letters.
 * <p>
 * Follow up:
 * 1.	Try to solve it in O(n log k) time and O(n) extra space.
 */

import org.junit.jupiter.api.Test;

import java.util.*;

public class topKFrequent {
    /*
    n: array length , m : word count, k: return count
    use a hashMap to count , so it's time: O(1), space: O(n)
    then priorityqueue and custom comparator, time O(logm), space O(m)
    then result list:
    Runtime: 5 ms, faster than 79.36% of Java online submissions for Top K Frequent Words.
Memory Usage: 39.2 MB, less than 51.26% of Java online submissions for Top K Frequent Words.
     */
    public List<String> topKFrequent(String[] words, int k) {
        // default is natural order comparator
        Map<String, Integer> countMap = new HashMap<>();
        Arrays.stream(words).forEach(w -> countMap.put(w, countMap.getOrDefault(w, 0) + 1));
        PriorityQueue<Map.Entry<String, Integer>> sortedQueue = new PriorityQueue<>((a, b) ->
        {
            int ret = 0;
            if (a.getValue() > b.getValue()) ret = -1;
            else if (a.getValue() == b.getValue()) ret = a.getKey().compareTo(b.getKey());
            else ret = 1;
            return ret;
        });

        sortedQueue.addAll(countMap.entrySet());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(sortedQueue.remove().getKey());
        }
        return result;
    }

    @Test
    public void test_topKFrequent() {
        String[] l1 = {"i", "love", "leetcode", "i", "love", "coding"};
        System.out.println(topKFrequent(l1, 2));
        String[] l2 = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
        System.out.println(topKFrequent(l2, 4));
    }
}
package amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 763. Partition Labels
 * A string S of lowercase English letters is given. We want to partition this string into as many parts
 * as possible so that each letter appears in at most one part, and return a list of integers representing
 * the size of these parts.
 * 
 * Example 1:
 * Input: S = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 * 
 * Note:
 * •	S will have length in range [1, 500].
 * •	S will consist of lowercase English letters ('a' to 'z') only.
 */
public class partitionLabels {
    public int getCharLastIndex(String S, char c, int prevLast) {
        for (int last = S.length() - 1; last >= prevLast; --last) {
            if (S.charAt(last) == c) return last;
        }
        return -1;
    }

    /*Runtime: 6 ms, faster than 22.98% of Java online submissions for Partition Labels.
    Memory Usage: 39.7 MB, less than 5.17% of Java online submissions for Partition Labels.*/
    /*second try: add previous last position to the getCharLastIndex() function, so no need to check
    * if can't find the matching char beyond current last. result:
    * Runtime: 3 ms, faster than 82.41% of Java online submissions for Partition Labels.
Memory Usage: 37.6 MB, less than 66.73% of Java online submissions for Partition Labels.*/
    public List<Integer> solution(String S) {
        if (S == null || S.isEmpty()) return null;
        List<Integer> result = new ArrayList<>();
        int len = S.length();
        int start = 0;
        Set<Character> cache = new HashSet<>();
        while (start < len) {
            int curr = start, last = curr;
            do {
                char c = S.charAt(curr);
                if (!cache.contains(c)) {
                    last = Math.max(last, getCharLastIndex(S, c, last));
                    cache.add(c);
                }
            } while (++curr <= last);
            result.add(last + 1 - start);
            start = last + 1;
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> ret = new partitionLabels().solution("ababcbacadefegdehijhklij");
    }
}
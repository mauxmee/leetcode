package amazon;

/*819. Most Common Word
Given a string paragraph and a string array of the banned words banned, return the most frequent word that is not banned.
 It is guaranteed there is at least one word that is not banned, and that the answer is unique.

The words in paragraph are case-insensitive and the answer should be returned in lowercase.
Example 1:
Input: paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]
Output: "ball"
Explanation:
"hit" occurs 3 times, but it is a banned word.
"ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
Note that words in the paragraph are not case sensitive,
that punctuation is ignored (even if adjacent to words, such as "ball,"),
and that "hit" isn't the answer even though it occurs more because it is banned.

Example 2:
Input: paragraph = "a.", banned = []
Output: "a"

Constraints:

1 <= paragraph.length <= 1000
paragraph consists of English letters, space ' ', or one of the symbols: "!?',;.".
0 <= banned.length <= 100
1 <= banned[i].length <= 10
banned[i] consists of only lowercase English letters.
通过次数20,037提交次数47,624*/

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MostCommonWord {
    //Runtime: 13 ms, faster than 64.51% of Java online submissions for Most Common Word.
    //Memory Usage: 40.1 MB, less than 18.51% of Java online submissions for Most Common Word.
    public String solution(String paragraph, String[] banned) {
        String[] parts = paragraph.split("[\\s\\!\\?\\'\\,\\;\\.]+");
        Map<String, Integer> countMap = new HashMap<>();
        // -1 means it's banned
        int max = 0;
        String ret = null;
        Arrays.stream(banned).forEach(s -> countMap.computeIfAbsent(s.toLowerCase(), k -> -1));
        for (int i = 0; i < parts.length; i++) {
            String k = parts[i].toLowerCase();
            int count = countMap.computeIfAbsent(k, k1 -> 0);
            if (count >= 0) {
                countMap.put(k, ++count);
                if (max < count) {
                    max = count;
                    ret = k;
                }
            }
        }
        return ret;
    }

    /* solution from the leetcode
        public String mostCommonWord(String paragraph, String[] banned) {

        // 1). replace the punctuations with spaces,
        // and put all letters in lower case
        String normalizedStr = paragraph.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();

        // 2). split the string into words
        String[] words = normalizedStr.split("\\s+");

        Set<String> bannedWords = new HashSet();
        for (String word : banned)
            bannedWords.add(word);

        Map<String, Integer> wordCount = new HashMap();
        // 3). count the appearance of each word, excluding the banned words
        for (String word : words) {
            if (!bannedWords.contains(word))
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // 4). return the word with the highest frequency
        return Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    * */
    @Test
    public void test_solution() {
        String[] banned = {"hit"};
        System.out.println(solution("Bob hit a ball,    the hit BALL flew far after it was hit.", banned));
    }
}
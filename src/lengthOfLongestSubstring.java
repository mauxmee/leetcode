import java.util.HashMap;
import java.util.Map;

/**
 * User: kaixue
 * Date: 14/04/2021
 * Time: 9:43 am
 */
/*
* Given a string s, find the length of the longest substring without repeating characters.



Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
Example 4:

Input: s = ""
Output: 0


Constraints:

0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.*/
public class lengthOfLongestSubstring {
    public static int solution(String s) {
        if (s == null || s.isEmpty()) return 0;
        // map<char, index>
        Map<Character, Integer> hit = new HashMap<>();
        int strlen = s.length(), start = 0, end = 0, reslen = 0;
        hit.put(s.charAt(0), 0);
        while (++end < strlen) {
            Character c = s.charAt(end);
            Integer index = hit.get(c);
            if (index != null) {
                reslen = Math.max(reslen, end - start);
                start = index + 1;
                for (int i = start; i <= end; ++i) {
                    hit.put(s.charAt(i), i);
                }
            } else {
                hit.put(c, end);
            }
        }
        return Math.max(reslen, end - start);
    }

    public static int solution2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            Character c = s.charAt(j);
            Integer index = map.get(c);
            if (index != null) {
                i = Math.max(index + 1, i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(c, j);
        }
        return ans;
    }

    public static void main(String[] args) {
        int reslen = solution2("pwwkew");
        System.out.println(reslen);
    }
}

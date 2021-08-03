package amazon;

import org.junit.jupiter.api.Test;

/**
 * 5. Longest Palindromic Substring
 * Given a string s, return the longest palindromic substring in s.
 * A palindromic number (also known as a numeral palindrome or a numeric palindrome) is a number
 * (such as 16461) that remains the same when its digits are reversed.
 * In other words, it has reflectional symmetry across a vertical axis.
 * <p>
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * <p>
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 * <p>
 * Example 3:
 * Input: s = "a"
 * Output: "a"
 * <p>
 * Example 4:
 * Input: s = "ac"
 * Output: "a"
 * <p>
 * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters (lower-case and/or upper-case),
 */
public class longestPalindrome {
    /*Runtime: 364 ms, faster than 16.94% of Java online submissions for Longest Palindromic Substring.
Memory Usage: 39.9 MB, less than 37.85% of Java online submissions for Longest Palindromic Substring
Approach 2: Brute Force
The obvious brute force solution is to pick all possible starting and ending positions for a substring, and verify if it is a palindrome.
Complexity Analysis
Time complexity : O(n^3)Assume that nn is the length of the input string,
Space complexity : O(1).*/
    public String bruteForce(String s) {
        String result = s.substring(0, 1);
        // two pointers, one from start, one from end, converge to the middle
        int start = 0, end = s.length() - 1;
        while (start < end) {
            char c = s.charAt(start);
            // reversely find the same letter from end, up to the middle one
            int last = end;
            while (last > start) {
                while (s.charAt(last) != c) {
                    --last;
                }
                // we found one
                if (last > start) {
                    // detect if it's a palindromic string
                    int i = start + 1, j = last - 1;
                    while (i < j && s.charAt(i) == s.charAt(j)) {
                        ++i;
                        --j;
                    }
                    if (i >= j) {
                        // we found one, is this the longest one?
                        //System.out.println(s.substring(start, last + 1) + " " + start + " - " + last);
                        String r = s.substring(start, last + 1);
                        if (r.length() > result.length()) {
                            result = r;
                        }
                        last = start;
                    }
                    // it's not a palindromic string, move last
                    else {
                        --last;
                    }
                }
            }
            // if not found, move start
            ++start;
        }

        return result;
    }

    /*Runtime: 23 ms, faster than 91.52% of Java online submissions for Longest Palindromic Substring.
Memory Usage: 39 MB, less than 74.34% of Java online submissions for Longest Palindromic Substring.

Approach 4: Expand Around Center
In fact, we could solve it in O(n^2) ) time using only constant space.
We observe that a palindrome mirrors around its center. Therefore, a palindrome can be expanded from its center,
 and there are only 2n - 1 such centers.
You might be asking why there are 2n - 1 but not n centers? The reason is the center of a palindrome can be
 in between two letters. Such palindromes have even number of letters (such as "abba") and its center are between the two 'b's.*/
    public String expandAroundCenter(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    @Test
    public void test_bruteForce() {
        System.out.println(bruteForce("101234432167"));
        System.out.println(bruteForce("aaaa"));
        System.out.println(bruteForce("babad"));
        System.out.println(bruteForce("abbd"));
        System.out.println(bruteForce("ab"));
        System.out.println(bruteForce("b"));
    }

    @Test
    public void test_expandAroundCenter() {
        System.out.println(expandAroundCenter("101234432167"));
        System.out.println(expandAroundCenter("aaaa"));
        System.out.println(expandAroundCenter("babad"));
        System.out.println(expandAroundCenter("abbd"));
        System.out.println(expandAroundCenter("ab"));
        System.out.println(expandAroundCenter("b"));
    }
}
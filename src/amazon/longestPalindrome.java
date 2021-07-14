package amazon;

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
    public String solution(String s) {
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

    public static void main(String[] args) {
        System.out.println(new longestPalindrome().solution("101234432167"));
        System.out.println(new longestPalindrome().solution("aaaa"));
        System.out.println(new longestPalindrome().solution("babad"));
        System.out.println(new longestPalindrome().solution("abbd"));
        System.out.println(new longestPalindrome().solution("ab"));
        System.out.println(new longestPalindrome().solution("b"));
    }
}
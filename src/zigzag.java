/*
* ZigZag Conversion
Medium

2291

5710

Add to List

Share
The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want
* to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);


Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"
Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:
P     I    N
A   L S  I G
Y A   H R
P     I
Example 3:

Input: s = "A", numRows = 1
Output: "A"


Constraints:

1 <= s.length <= 1000
s consists of English letters (lower-case and upper-case), ',' and '.'.
1 <= numRows <= 1000*/


public class zigzag {
    public static String convert(String s, int numRows) {
        if(numRows <= 1) return s;
        StringBuffer sb = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < numRows; ++i) {
            int j = i;
            boolean odd = true;
            while (j < len) {
                sb.append(s.charAt(j));
                int step = 0, count = 0;
                do {
                    step = odd ? 2 * (numRows - 1 - i) : 2 * i;
                    odd = !odd;
                } while (step == 0 && count++ < 2);
                if(step == 0) continue;
                j += step;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String result;
        //result = convert("PAYPALISHIRING", 4);
        //assert (result.equals("PINALSIGYAHRPI"));
        //System.out.println(result);
        result = convert("AB", 1);
        System.out.println(result);
    }
}
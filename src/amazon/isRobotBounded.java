package amazon;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 1041. Robot Bounded In Circle
 * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:
 * •	"G": go straight 1 unit;
 * •	"L": turn 90 degrees to the left;
 * •	"R": turn 90 degrees to the right.
 * The robot performs the instructions given in order, and repeats them forever.
 * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
 * <p>
 * Example 1:
 * Input: instructions = "GGLLGG"
 * Output: true
 * Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
 * When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
 * <p>
 * Example 2:
 * Input: instructions = "GG"
 * Output: false
 * Explanation: The robot moves north indefinitely.
 * <p>
 * Example 3:
 * Input: instructions = "GL"
 * Output: true
 * Explanation: The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
 * <p>
 * Constraints:
 * •	1 <= instructions.length <= 100
 * •	instructions[i] is 'G', 'L' or, 'R'.
 */

public class isRobotBounded {

    class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int instance2() {
            return x * x + y * y;
        }
    }


    class Direction    {
        char name;
        char left;
        char right;

        public Direction( char n, char l, char r)
        {
            name = n;
            left = l;
            right = r;
        }
    }

//    // N: y + 1; S: y - 1; E: x + 1, W: x - 1
//    // initial direction is E
//    public boolean solution(String instructions) {
//        if (instructions == null || instructions.isEmpty()) return true;
//        int x = 0, y = 0, d = 0, count = 0;
//        LinkedList<Character> directions = new LinkedList<>(Arrays.asList('E', 'N', 'W', 'S'));
//        while (count++ < 4) {
//            for (char d : instructions.toCharArray()) {
//                if (d)
//            }
//        }
//        return false;
//    }
//
//    public static void main(String[] args) {
//        boolean ret = new isRobotBounded(direction).solution("GGLLGG");
//
//    }
}
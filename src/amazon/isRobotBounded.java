package amazon;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
//内存消耗：36.1 MB, 在所有 Java 提交中击败了97.34%的用户
    public boolean solution(String instructions) {
        // record the position
        int x = 0, y = 0;
        // N:0, W:1, S:2 E:3
        int direction = 0;
        // L : (direction + 1) % 4
        // R: (direction -1) % 4;
        // G: direction 0: y + 1, 1: x -1; 2: y -1; 3: x + 1
        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);
            if (c == 'G') {
                switch (direction) {
                    case 0:
                        ++y;
                        break;
                    case 1:
                        --x;
                        break;
                    case 2:
                        --y;
                        break;
                    case 3:
                        ++x;
                        break;
                    default:
                        break;
                }
            } else if (c == 'L') {
                direction = (direction + 1) % 4;
            } else if (c == 'R') {
                // can't directly -1 since 0 -> -1, so need to do +4-1
                direction = (direction + 3) % 4;
            }
        }
        // after all instrument processed, check the position and direction
//        return !((x > 0 && direction == 3) ||
//                (x < 0 && direction == 1) ||
//                (y > 0 && direction == 0) ||
//                (y < 0 && direction == 2));
        // correct condition: if it returns back to original point;
        // or the direction is not the same direction as last one, it'll eventually in a circle
        return (x == 0 && y == 0) || direction != 0;
    }

    @Test
    public void test_solution() {
        assertEquals(solution("GGLLGG"), true);
        assertEquals(solution("GG"), false);
        assertEquals(solution("GL"), true);
        assertEquals(solution("GLRLLGLL"), true);
        assertEquals(solution("LLGRL"), true);
        assertEquals(solution("GLGLGGLGL"), false);
        assertEquals(solution("RGL"), false);
    }
}
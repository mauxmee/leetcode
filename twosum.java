import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class twosum {
    private static final int LOW_BOUND = -1 * 1000000000;
    private static final int HIGH_BOUND = 1000000000;
    private static final String INPUT_FILE = "twosum.input.txt";

    public static boolean invalidInput(int n) {
        return n < LOW_BOUND || n > HIGH_BOUND;
    }


    // my first version, it works but very slow , O(n^2)
    public static int[] sum_1(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        if (nums == null || nums.length < 2 || invalidInput(target)) {
            System.out.println("invalid original input");
        } else {
            int len = nums.length;

            for (int i = 0; i < len; ++i) {
                for (int j = i + 1; j < len; ++j) {
                    if (invalidInput(nums[i]) || invalidInput(nums[j])) {
                        System.out.println("Invalid element");
                        return result;
                    }
                    if (nums[i] + nums[j] == target) {
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    // this is the best solution with hashmap. O(n)
    public static int[] sum_2(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        if (nums == null || nums.length < 2 || invalidInput(target)) {
            System.out.println("invalid original input");
        } else {
            // map< value, index>
            Map<Integer, Integer> cache = new HashMap<>();

            for (int i = 0; i < nums.length; ++i) {
                int n = nums[i];
                if (invalidInput(n)) {
                    System.out.println("invalid original input");
                    break;
                }
                int m = target - n;
                Integer index = cache.get(m);
                if (index != null) {
                    // found it
                    result[0] = i;
                    result[1] = index;
                    break;
                } else {
                    cache.put(n, i);
                }
            }
        }
        return result;
    }

    // test 4: what if there are millions of input
    public static void sum_4(String fileName, int target) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String l = reader.readLine();
            int i = 0;
            Map<Integer, Integer> cache = new HashMap<>(997);
            while (l != null) {
                int n = Integer.parseInt(l);
                if (!invalidInput(n)) {
                    int m = target - n;
                    Integer index = cache.get(m);
                    if (index != null) {
                        // found it
                        System.out.println("" + n + "/" + i + " + " + m + "/" + index + " = " + target);
                        return;
                    } else {
                        cache.put(n, i);
                    }
                }
                l = reader.readLine();
                ++i;
            }
        } catch (Exception ignore) {
        }
        System.out.println("can't find a sum for " + target);
    }

    // now the array is sorted, do it the best way
    public static int[] sum_3(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        if (nums == null || nums.length < 2 || invalidInput(target)) {
            System.out.println("invalid original input");
        } else {
            int s = 0, e = nums.length - 1;
            do {
                int sum = nums[s] + nums[e];
                if (sum > target) {
                    --e;
                } else if (sum < target) {
                    ++s;
                } else {
                    result[0] = s;
                    result[1] = e;
                    return result;
                }
            } while (s < e);
        }
        return result;
    }

    public static void generateInputFile() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(INPUT_FILE), "utf-8"))) {
            for (int i = 0; i < 100000; ++i) {
                int v = (int) (Math.floor(Math.random() * (HIGH_BOUND - LOW_BOUND + 1) + LOW_BOUND));
                writer.write(Integer.toString(v) + '\n');
            }
        } catch (Exception ignore) {
        }
    }

    public static void main(String[] args) {
        int[] nums = IntStream.of(2, 3, 4, 6, 7, 89, 1, 23, 65, 34, 57, 45, 12, 67, 87, 32).toArray();
        int target = 103205611;

        // test 1
        System.out.println("Test 1");
        System.out.println("Nums: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        int[] result = twosum.sum_1(nums, target);
        System.out.println(Arrays.toString(result));
        if (result[0] != -1) {
            System.out.println("" + nums[result[0]] + " + " + nums[result[1]] + " = " + target);
        }


        // test 2
        System.out.println("Test 2");
        System.out.println("Nums: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        result = twosum.sum_2(nums, target);
        System.out.println(Arrays.toString(result));
        if (result[0] != -1) {
            System.out.println("" + nums[result[0]] + " + " + nums[result[1]] + " = " + target);
        }
        // test 3
        // sort the list first then test
        Arrays.sort(nums);
        System.out.println("Test 3. the array is sorted");
        System.out.println("Nums: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        result = twosum.sum_3(nums, target);
        System.out.println(Arrays.toString(result));
        if (result[0] != -1) {
            System.out.println("" + nums[result[0]] + " + " + nums[result[1]] + " = " + target);
        }

        // test 4: generate a huge input file, random target sum, then input into sum_4
        generateInputFile();
        twosum.sum_4(INPUT_FILE, target);


    }
}

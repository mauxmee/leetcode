package normal;

import java.util.BitSet;


public class TableRowCache {

    public static class CheckResult {
        public boolean needToAdjust = false;
        public int first = -1;
        public int last = -1;
    }

    public static final int BLOCK_SIZE = 256;
    public static final int DEFAULT_NUM = 8196;
    // initial 2 million rows,
    private BitSet m_cache = new BitSet(DEFAULT_NUM);

    public CheckResult check(int first, int last) {
        CheckResult result = new CheckResult();
        if (first < 0 || last < 0) {
            return result;
        }
        first >>= 8;
        last >>= 8;

        if (!m_cache.get(first)) {
            result.needToAdjust = true;
            m_cache.set(first);
            result.first = first * BLOCK_SIZE;
            result.last = (first + 1) * BLOCK_SIZE - 1;
        }
        if (last != first && !m_cache.get(last)) {
            result.needToAdjust = true;
            m_cache.set(last);
            if (result.first == -1) {
                result.first = last * BLOCK_SIZE;
            }
            result.last = (last + 1) * BLOCK_SIZE - 1;
        }
        return result;
    }

    public static void printResult(CheckResult r) {
        System.out.println("Adjust: " + r.needToAdjust + " first: " + r.first + " last: " + r.last);
    }

    public static void main(String[] args) {
        TableRowCache cache = new TableRowCache();

        printResult(cache.check(100, 300));
        printResult(cache.check(200, 205));
        printResult(cache.check(600, 1000));
        printResult(cache.check(-1, -1));
        printResult(cache.check(0, 205));
        printResult(cache.check(200, 205));
        printResult(cache.check(700, 1025));

    }
}
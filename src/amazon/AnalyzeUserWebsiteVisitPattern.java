package amazon;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/*1152. Analyze User Website Visit Pattern
We are given some website visits: the user with name username[i] visited the website website[i]
at time timestamp[i].

A 3-sequence is a list of websites of length 3 sorted in ascending order by the time of their visits.
(The websites in a 3-sequence are not necessarily distinct.)

Find the 3-sequence visited by the largest number of users. If there is more than one solution,
return the lexicographically smallest such 3-sequence.

Example 1:
Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"],
timestamp = [1,2,3,4,5,6,7,8,9,10],
website = ["home","about","career","home","cart","maps","home","home","about","career"]
Output: ["home","about","career"]
Explanation:
The tuples in this example are:
["joe", 1, "home"]
["joe", 2, "about"]
["joe", 3, "career"]
["james", 4, "home"]
["james", 5, "cart"]
["james", 6, "maps"]
["james", 7, "home"]
["mary", 8, "home"]
["mary", 9, "about"]
["mary", 10, "career"]
The 3-sequence ("home", "about", "career") was visited at least once by 2 users.
The 3-sequence ("home", "cart", "maps") was visited at least once by 1 user.
The 3-sequence ("home", "cart", "home") was visited at least once by 1 user.
The 3-sequence ("home", "maps", "home") was visited at least once by 1 user.
The 3-sequence ("cart", "maps", "home") was visited at least once by 1 user.

Note:
3 <= N = username.length = timestamp.length = website.length <= 50
1 <= username[i].length <= 10
0 <= timestamp[i] <= 10^9
1 <= website[i].length <= 10
Both username[i] and website[i] contain only lowercase characters.
It is guaranteed that there is at least one user who visited at least 3 websites.
No user visits two websites at the same time.
1、同一个用户的路径并不都是在一起，所以要分开总结；
2、时间也不是按照顺学排的，所以分好类之后，需要对同一用户的路径按照时间排序再做总结
3、同样频率答案，选字母顺序小的答案（需排序）
4、这是最恶心的！！！！！！！！ 我查了好久，那就是他要的是最多客户访问的答案，不是最多访问次数。也就是有的路径三个人，分别访问了一次，算作3，但是如果是一个人访问了3次，只算1；

通过次数1,232提交次数3,238*/
public class AnalyzeUserWebsiteVisitPattern {
    public static final int SEQ_N = 3;

    public class Seq {
        List<String> words = new ArrayList<>(3);
        String user;

        boolean isComplete() {
            return words.size() == SEQ_N;
        }

        boolean set(String u, String w) {
            if (user == null) {
                user = u;
            } else if (!user.equals(u)) {
                return false;
            }
            if (!isComplete()) {
                words.add(w);
                return true;
            }
            return false;
        }
    }

    // kxu: give up for now , too complicated.
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int N = website.length;
        List<Seq> seq = new ArrayList<>();
        for (int i = 0; i < N; i++) {

        }
        return null;
    }

    @Test
    public void test_mostVisitedPattern() {
        String[] usernames = {"joe", "joe", "joe", "james", "james", "james", "james", "mary", "mary", "mary"};
        int[] timestamps = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] websites = {"home", "about", "career", "home", "cart", "maps", "home", "home", "about", "career"};
        System.out.println(mostVisitedPattern(usernames, timestamps, websites));
    }
}
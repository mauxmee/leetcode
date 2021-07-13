package amazon;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*given a string, find the matching braces like [], (), "", if not found print a message */
public class FindMatchingBrace {
    // example: braces: "()[]{}""''<>"
    public static class Pair
    {
        char first, second;

        public Pair(char first, char second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair braces = (Pair) o;
            return first == braces.first && second == braces.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
    public void solution(final String source, final String braces) {
        // first parse the braces and put into list of pair
        // second scan the source, and find next start brace,
        // note the brace may nested, say ([{""}]) or ([{)}] they are matching, so need to
        // save the unmatched braces and pop if found match.
        Map<Character, Character> braceMap = new HashMap<>();
        for (int i = 0; i < braces.length(); i++) {
            char first = braces.charAt(i++);
            if(i < braces.length()) {
                char second = braces.charAt(i);
                braceMap.put(first, second);
            }
        }
        for (int j   = 0; j < source.length(); j++) {
            char c = source.charAt(j);
            if(
        }
    }
}
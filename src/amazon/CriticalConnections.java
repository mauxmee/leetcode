package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 1192. Critical Connections in a Network
难度困难
There are n servers numbered from 0 to n - 1 connected by undirected server-to-server connections forming a network where connections[i] = [ai, bi] represents a connection between servers ai and bi. Any server can reach other servers directly or indirectly through the network.
A critical connection is a connection that, if removed, will make some servers unable to reach some other server.
Return all critical connections in the network in any order.

Example 1:

Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.
*/
public class CriticalConnections {
    static class Conn {
        public int a, b;
        public boolean checked = false;
        public boolean critical = false;

        public Conn(int a, int b) {
            this.a = Math.min(a, b);
            this.b = Math.max(a, b);
        }

        public Conn(List<Integer> nodes) {
            if (nodes != null && nodes.size() == 2) {
                a = Math.min(nodes.get(0), nodes.get(1));
                b = Math.max(nodes.get(0), nodes.get(1));
            }
        }
    }

    public List<List<Integer>> solution(int n, List<List<Integer>> connections) {
        Map<Integer, List<Conn>> routes = new HashMap<>();
        connections.forEach(nodes -> {
            Conn conn = new Conn(nodes);
            routes.computeIfAbsent(conn.a, (k) -> new ArrayList<>()).add(conn);
        });
        routes.keySet().forEach(a -> {

        });
        return null;
    }
}
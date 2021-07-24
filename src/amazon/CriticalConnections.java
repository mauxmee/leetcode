package amazon;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 1192. Critical Connections in a Network
难度困难
There are n servers numbered from 0 to n - 1 connected by undirected server-to-server connections forming a network
* where connections[i] = [ai, bi] represents a connection between servers ai and bi. Any server can reach other
* servers directly or indirectly through the network.
A critical connection is a connection that, if removed, will make some servers unable to reach some other server.
Return all critical connections in the network in any order.

Example 1:

Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.
*/
public class CriticalConnections
{
	// This is Tarjan algo for the graph, see
	// https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
	// time complexity: O( Number of nodes +number of edges)
	// considering the flags (desc and low ) have O(1) access since they are array

	/*Basically, it uses dfs to travel through the graph to find if current vertex u,
	can travel back to u or previous vertex
low[u] records the lowest vertex u can reach
disc[u] records the time when u was discovered*/

	/*Runtime: 110 ms, faster than 70.87% of Java online submissions for Critical Connections in a Network.
Memory Usage: 114.8 MB, less than 61.35% of Java online submissions for Critical Connections in a Network.*/
	public List<List<Integer>> solution( int n, List<List<Integer>> connections )
	{
        /*Disc: This indicates the time for when a particular node is discovered or visited during DFS traversal.
        For each node we increase the Disc time value by 1.

		Low: This indicates the node with lowest discovery time accessible from a given node. If there is a back edge then
		we update the low value based on some conditions. The maximum value Low for a node can be assigned is equal to the
		Disc value of that node since the minimum discovery time for a node is the time to visit/discover itself.
		Note: The Disc value once assigned will not change while we keep on updating the low value traversing each node.
		We will discuss the condition next.
		追溯值
		追溯值用来表示从当前节点 x 作为搜索树的根节点出发，能够访问到的所有节点中，时间戳最小的值 —— low[x]。
		那么，我们要限定下什么是“能够访问到 的所有节点”？，其需要满足下面的条件之一即可：

		以 x 为根的搜索树的所有节点
		通过一条非搜索树上的边，能够到达搜索树的所有节点
		为*/
		int[] disc = new int[ n ], low = new int[ n ];
		Arrays.fill( disc, -1 ); // use disc to track if visited (disc[i] == -1)

		List<Integer>[] graph = new ArrayList[ n ];
		for ( int i = 0; i < n; i++ )
		{
			graph[ i ] = new ArrayList<>();
		}
		// build graph
		for ( int i = 0; i < connections.size(); i++ )
		{
			int from = connections.get( i ).get( 0 );
			int to = connections.get( i ).get( 1 );
			graph[ from ].add( to );
			graph[ to ].add( from );
		}
		// result
		List<List<Integer>> res = new ArrayList<>();
		for ( int i = 0; i < n; i++ )
		{
			if ( disc[ i ] == -1 )
			{
				dfs( i, low, disc, graph, res, i );
			}
		}
		return res;
	}

	//时间戳是用来标记图中每个节点在进行深度优先搜索时被访问的时间顺序，当然，你可以理解成一个序号（这个序号由小到大
	int time = 0; // time when discover each vertex

	private void dfs( int u, int[] low, int[] disc, List<Integer>[] graph, List<List<Integer>> res, int pre )
	{
		disc[ u ] = low[ u ] = ++time; // discover u
		for ( int j = 0; j < graph[ u ].size(); j++ )
		{
			int v = graph[ u ].get( j );
			if ( v == pre )
			{
				continue; // if parent vertex, ignore
			}
			if ( disc[ v ] == -1 )
			{ // if not discovered
				dfs( v, low, disc, graph, res, u );
				/*Now if for an edge (u,v) if v node is not present in stack then it is a tree edge or
				a neighboring edge. In such case, we update the low value for that particular node as :
					if (Tree-Edge) then Low[u] = Min ( Low[u] , Low[v] ).*/
				low[ u ] = Math.min( low[ u ], low[ v ] );

				// critical
				//节点 u 被访问的时间 disc[ u ]，要优先于（小于）以下这些节点被访问的时间 —— low[v] 。
				//以节点 v 为根的搜索树中的所有节点
				//通过一条非搜索树上的边，能够到达搜索树的所有节点
				if ( low[ v ] > disc[ u ] )
				{
					// u - v is critical, there is no path for v to reach back to u or previous vertices of u
					res.add( Arrays.asList( u, v ) );
				}
				/*We determine the head or start node of each SCC when we get a node whose Disc[u] = Low[u],
				such a node is the head node of that SCC.
				Every SCC should have such a node maintaining this condition. */
				else if ( low[ u ] == disc[ u ] )
				{
					//Note: A Strongly Connected Component must have all its low values same.
					System.out.print( "Strongly Connected Component: " );
					for ( int i = 0; i < low.length; i++ )
					{
						if ( low[ i ] == low[ u ] )
						{
							System.out.print( i + " " );
						}
					}
					System.out.println();
				}
			}
			else
			{ // if v discovered and is not parent of u, update low[u], cannot use low[v] because u is not subtree of v
				/*So,If for an edge (u,v) if v node is already present in  disc, then it is a back edge and (u,v) pair
				is Strongly Connected.  So we change the low value as :
						if(Back-Edge) then Low[u] = Min ( Low[u] , Disc[v] ).

				After visiting this node on returning the call to its parent node we will update the Low value
				for each node to ensure that Low value remains the same for all nodes in the
				Strongly Connected Component.*/
				low[ u ] = Math.min( low[ u ], disc[ v ] );
			}
		}
	}

	List<List<Integer>> input = new ArrayList<>();

	private void add( int a, int b )
	{
		List<Integer> l = new ArrayList<>();
		l.add( a );
		l.add( b );
		input.add( l );
	}

	@BeforeEach
	public void init()
	{
		add( 0, 1 );
		add( 1, 2 );
		add( 2, 3 );
		add( 3, 4 );
		add( 4, 0 );
		add( 0, 5 );
		add( 5, 6 );
		add( 5, 7 );
		add( 7, 8 );
		add( 8, 5 );
	}

	@Test
	public void test_tarjan()
	{
		var result = solution( 9, input );
		System.out.println( "Critical Connections: " + result );
	}
}
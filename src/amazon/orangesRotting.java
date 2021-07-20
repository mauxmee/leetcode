package amazon;


import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 994. Rotting Oranges
 * You are given an m x n grid where each cell can have one of three values:
 * •	0 representing an empty cell,
 * •	1 representing a fresh orange, or
 * •	2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
 *
 * Example 1:
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 * Constraints:
 * •	m == grid.length
 * •	n == grid[i].length
 * •	1 <= m, n <= 10
 * •	grid[i][j] is 0, 1, or 2.
 */
public class orangesRotting
{
	public static final int PROCESSED = 3;

	public static class Pixel
	{
		public int x;
		public int y;

		public Pixel( final int x, final int y )
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals( final Object o )
		{
			if ( this == o ) { return true; }
			if ( o == null || getClass() != o.getClass() ) { return false; }
			final Pixel pixel = ( Pixel ) o;
			return x == pixel.x && y == pixel.y;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash( x, y );
		}
	}

	/*Runtime: 6 ms, faster than 15.63% of Java online submissions for Rotting Oranges.
Memory Usage: 38.3 MB, less than 79.06% of Java online submissions for Rotting Oranges.*/
	public int solution( int[][] grid )
	{
		if ( grid == null ) { return -1; }
		Set<Pixel> currRotten = new HashSet<>(), currFresh = new HashSet<>();
		int row = grid.length, col = grid[ 0 ].length;
		// first sweep the grid to get the rotten ones
		for ( int y = 0; y < row; ++y )
		{
			for ( int x = 0; x < col; ++x )
			{
				if ( grid[ y ][ x ] == 2 )
				{
					currRotten.add( new Pixel( x, y ) );
					// once processed the rotten one will become 3, indicating it's processed
					grid[ y ][ x ] = PROCESSED;
				}
				else if ( grid[ y ][ x ] == 1 )
				{
					currFresh.add( new Pixel( x, y ) );
				}
			}
		}
		if ( currRotten.isEmpty() ) { return currFresh.isEmpty() ? 0 : -1; }
		int days = 0;

		Set<Pixel> newRotten = new HashSet<>();
		while ( !currRotten.isEmpty() )
		{
			// get the rotten one next days
			currRotten.forEach( p -> {
				if ( p.x - 1 >= 0 && grid[ p.y ][ p.x - 1 ] == 1 )
				{
					process( newRotten, currFresh, grid, p.x - 1, p.y );
				}
				if ( p.x + 1 < col && grid[ p.y ][ p.x + 1 ] == 1 )
				{
					process( newRotten, currFresh, grid, p.x + 1, p.y );
				}
				if ( p.y - 1 >= 0 && grid[ p.y - 1 ][ p.x ] == 1 )
				{
					process( newRotten, currFresh, grid, p.x, p.y - 1 );
				}
				if ( p.y + 1 < row && grid[ p.y + 1 ][ p.x ] == 1 )
				{
					process( newRotten, currFresh, grid, p.x, p.y + 1 );
				}
			} );

			if ( newRotten.isEmpty() )
			{
				return currFresh.isEmpty() ? days : -1;
			}
			days++;
			currRotten = new HashSet<>( newRotten );
			newRotten.clear();
		}
		return days;
	}

	private void process( Set<Pixel> rotten, Set<Pixel> fresh, int[][] grid, int x, int y )
	{
		Pixel p = new Pixel( x, y );
		fresh.remove( p );
		rotten.add( p );
		grid[ y ][ x ] = PROCESSED;
	}

	// this is actually my method, it's called breadth first search
	/*Runtime: 2 ms, faster than 96.17% of Java online submissions for Rotting Oranges.
Memory Usage: 38.7 MB, less than 32.61% of Java online submissions for Rotting Oranges.
多源bfs
先将所有坏橘子入队
同时统计新鲜橘子的个数
然后bfs，每一轮分钟数加一，由于while中没有return操作，最后一步时间会多加一分钟，所以结果减了1
最后看被腐蚀的橘子与初始的新鲜橘子个数是否相等，相等则返回分钟数，不相等则-1
如何写（最短路径的） BFS 代码
我们都知道 BFS 需要使用队列，代码框架是这样子的（伪代码）：

Python

while queue 非空:
	node = queue.pop()
    for node 的所有相邻结点 m:
        if m 未访问过:
            queue.push(m)
但是用 BFS 来求最短路径的话，这个队列中第 1 层和第 2 层的结点会紧挨在一起，无法区分。因此，我们需要稍微修改一下代码，
在每一层遍历开始前，记录队列中的结点数量 nn ，然后一口气处理完这一层的 nn 个结点。代码框架是这样的：

Python

depth = 0 # 记录遍历到第几层
while queue 非空:
    depth++
    n = queue 中的元素个数
    循环 n 次:
        node = queue.pop()
        for node 的所有相邻结点 m:
            if m 未访问过:
                queue.push(m)
本题题解
有了计算最短路径的层序 BFS 代码框架，写这道题就很简单了。这道题的主要思路是：

一开始，我们找出所有腐烂的橘子，将它们放入队列，作为第 0 层的结点。
然后进行 BFS 遍历，每个结点的相邻结点可能是上、下、左、右四个方向的结点，注意判断结点位于网格边界的特殊情况。
由于可能存在无法被污染的橘子，我们需要记录新鲜橘子的数量。在 BFS 中，每遍历到一个橘子（污染了一个橘子），
就将新鲜橘子的数量减一。如果 BFS 结束后这个数量仍未减为零，说明存在无法被污染的橘子。
*/
	public int Breadthfirstsearch( int[][] grid )
	{
		int m = grid.length;
		int n = grid[ 0 ].length;
		int min = 0;
		int cnt = 0;
		int sum = 0;    //新鲜的个数
		// use a queue , so new rotten orange put into the queue
		Queue<int[]> queue = new LinkedList<>();
		for ( int i = 0; i < m; i++ )
		{
			for ( int j = 0; j < n; j++ )
			{
				// use a int[] to store x,y, easier than a class like me
				if ( grid[ i ][ j ] == 2 ) { queue.offer( new int[] { i, j } ); }
				if ( grid[ i ][ j ] == 1 ) { sum++; }
			}
		}
		if ( sum == 0 ) { return 0; }
		int bad = queue.size();
		while ( !queue.isEmpty() )
		{
			// each round the queue size
			int s = queue.size();
			cnt += s;
			for ( int i = 0; i < s; i++ )
			{
				int arr[] = queue.poll();
				int a = arr[ 0 ];
				int b = arr[ 1 ];
				if ( a - 1 >= 0 && grid[ a - 1 ][ b ] == 1 )
				{
					queue.offer( new int[] { a - 1, b } );
					grid[ a - 1 ][ b ] = 2;
				}
				if ( a + 1 < m && grid[ a + 1 ][ b ] == 1 )
				{
					queue.offer( new int[] { a + 1, b } );
					grid[ a + 1 ][ b ] = 2;
				}
				if ( b - 1 >= 0 && grid[ a ][ b - 1 ] == 1 )
				{
					queue.offer( new int[] { a, b - 1 } );
					grid[ a ][ b - 1 ] = 2;
				}
				if ( b + 1 < n && grid[ a ][ b + 1 ] == 1 )
				{
					queue.offer( new int[] { a, b + 1 } );
					grid[ a ][ b + 1 ] = 2;
				}
			}
			min++;
		}
		// here the boundary check is tricky
		return cnt - bad == sum ? min - 1 : -1;
	}


	@Test
	public void test_rottenlogic()
	{
		int[][] grid1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		assertEquals( solution( grid1 ), 4 );

		int[][] grid2 = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
		assertEquals( solution( grid2 ), -1 );

		int[][] grid3 = { { 0, 2 } };
		assertEquals( solution( grid3 ), 0 );

		int[][] grid4 = { { 2, 1, 0, 0, 1, 0 }, { 0, 0, 2, 1, 0, 1 }, { 0, 1, 0, 0, 2, 1 }, { 1, 1, 1, 1, 1, 2 } };
		assertEquals( solution( grid4 ), -1 );

		int[][] grid5 = { { 2, 1, 0, 1, 1, 0 }, { 0, 0, 2, 1, 0, 1 }, { 0, 1, 0, 0, 2, 1 }, { 1, 1, 1, 1, 1, 2 } };
		assertEquals( solution( grid5 ), 5 );

		int[][] grid6 = { { 0 } };
		assertEquals( solution( grid6 ), 0 );

		int[][] grid7 = { { 1 } };
		assertEquals( solution( grid7 ), -1 );
	}

	@Test
	public void test_bfs()
	{
		int[][] grid1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		assertEquals( Breadthfirstsearch( grid1 ), 4 );

		int[][] grid2 = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
		assertEquals( Breadthfirstsearch( grid2 ), -1 );

		int[][] grid3 = { { 0, 2 } };
		assertEquals( Breadthfirstsearch( grid3 ), 0 );

		int[][] grid4 = { { 2, 1, 0, 0, 1, 0 }, { 0, 0, 2, 1, 0, 1 }, { 0, 1, 0, 0, 2, 1 }, { 1, 1, 1, 1, 1, 2 } };
		assertEquals( Breadthfirstsearch( grid4 ), -1 );

		int[][] grid5 = { { 2, 1, 0, 1, 1, 0 }, { 0, 0, 2, 1, 0, 1 }, { 0, 1, 0, 0, 2, 1 }, { 1, 1, 1, 1, 1, 2 } };
		assertEquals( Breadthfirstsearch( grid5 ), 5 );

		int[][] grid6 = { { 0 } };
		assertEquals( Breadthfirstsearch( grid6 ), 0 );

		int[][] grid7 = { { 1 } };
		assertEquals( Breadthfirstsearch( grid7 ), -1 );
	}
}
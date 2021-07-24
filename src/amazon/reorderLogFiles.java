package amazon;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/*
*
* 937. Reorder Data in Log Files
You are given an array of logs. Each log is a space-delimited string of words, where the first word is the identifier.

There are two types of logs:

Letter-logs: All words (except the identifier) consist of lowercase English letters.
Digit-logs: All words (except the identifier) consist of digits.
Reorder these logs so that:

The letter-logs come before all digit-logs.
The letter-logs are sorted lexicographically by their contents. If their contents are the same, then sort them lexicographically by their identifiers.
The digit-logs maintain their relative ordering.
Return the final order of the logs.

Example 1:
Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
Explanation:
The letter-log contents are all different, so their ordering is "art can", "art zero", "own kit dig".
The digit-logs have a relative order of "dig1 8 1 5 1", "dig2 3 6".

* Example 2:
Input: logs = ["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
Output: ["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4 7"]


Constraints:

1 <= logs.length <= 100
3 <= logs[i].length <= 100
All the tokens of logs[i] are separated by a single space.
logs[i] is guaranteed to have an identifier and at least one word after the identifier.
通过次数10,963提交次数18,622*/
public class reorderLogFiles
{
	String[] input1 = { "dig1 8 1 5 1", "let1 art can", "dig2 3 6", "let2 own kit dig", "let3 art zero" };
	String[] input2 = { "a1 9 2 3 1", "g1 act car", "zo4 4 7", "ab1 off key dog", "a8 act zoo" };

	static class ParseResult
	{
		int location = 0;
		boolean isDigit = false;
		String id = null;
		String content = null;

		ParseResult( String log, int location )
		{
			//			int p = log.indexOf( " " );
			//			if ( p > 0 )
			//			{
			//				this.location = location;
			//				// can't find, ignore
			//				id = log.substring( 0, p ).trim();
			//				content = log.substring( p + 1 ).trim();
			//				char firstChar = content.charAt( 0 );
			//				isDigit = firstChar >= '0' && firstChar <= '9';
			//			}

			this.location = location;
			String[] parts = log.split( " ", 2 );
			id = parts[ 0 ];
			content = parts[ 1 ];
			isDigit = Character.isDigit( content.charAt( 0 ) );
		}

		ParseResult( String log )
		{
			this( log, 0 );
		}

		@Override
		public String toString()
		{
			return "ParseResult{" +
					"isDigit=" + isDigit +
					", id='" + id + '\'' +
					", content='" + content + '\'' +
					'}';
		}
	}

	/*Runtime: 4 ms, faster than 78.69% of Java online submissions for Reorder Data in Log Files.
Memory Usage: 39.5 MB, less than 51.16% of Java online submissions for Reorder Data in Log Files.*/
	public String[] solution( String[] logs )
	{
		// first create two array
		TreeMap<ParseResult, String> map = new TreeMap<>( ( r1, r2 ) -> {
			int result = 0;
			if ( !r1.isDigit && !r2.isDigit )
			{
				result = r1.content.compareTo( r2.content );
				if ( result == 0 )
				{
					result = r1.id.compareTo( r2.id );
				}
			}
			else
			{
				result = r1.isDigit && !r2.isDigit ? 1 :
						!r1.isDigit && r2.isDigit ? -1 : r1.location - r2.location;
			}
			return result;
		} );
		for ( int i = 0; i < logs.length; i++ )
		{
			String log = logs[ i ];
			map.put( new ParseResult( log, i ), log );
		}

		return map.values().toArray( new String[ 0 ] );

	}

	public String[] solution_arraySort( String[] logs )
	{
		Comparator<String> comp = ( log1, log2 ) -> {
			int result = 0;
			ParseResult r1 = new ParseResult( log1 );
			ParseResult r2 = new ParseResult( log2 );
			if ( !r1.isDigit && !r2.isDigit )
			{
				result = r1.content.compareTo( r2.content );
				if ( result == 0 )
				{
					result = r1.id.compareTo( r2.id );
				}
			}
			else
			{
				result = r1.isDigit && !r2.isDigit ? 1 :
						!r1.isDigit && r2.isDigit ? -1 : 0;
			}
			return result;
		};
		Arrays.sort( logs, comp );
		return logs;
	}

	@Test
	public void test_parser()
	{
		Arrays.stream( input1 ).forEach( log ->
				System.out.println( new ParseResult( log ) ) );
		Arrays.stream( input2 ).forEach( log ->
				System.out.println( new ParseResult( log ) ) );
	}

	@Test
	public void test_solution_treemap()
	{
		System.out.println( Arrays.toString( solution( input1 ) ) );
		System.out.println( Arrays.toString( solution( input2 ) ) );
	}

	@Test
	public void test_solution_arraysort()
	{
		// so here the arraysort is stable sorting, but treemap is not,
		// we need to add the count to TreeMap sorting comparator
		System.out.println( Arrays.toString( solution_arraySort( input1 ) ) );
		System.out.println( Arrays.toString( solution_arraySort( input2 ) ) );
	}
}
package amazon;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/*given a string, find the matching braces like [], (), "", if not found print a message */
public class FindMatchingBrace
{
	public void solution( final String source, final String braces )
	{
		// first parse the braces and put into list of pair
		// second scan the source, and find next start brace,
		// note the brace may nested, say ([{""}]) or ([{)}] they are matching, so need to
		// save the unmatched braces and pop if found match.
		Map<Character, Character> braceMap = new HashMap<>();
		Map<Character, Integer> countMap = new HashMap<>();
		if ( braces != null )
		{
			for ( int i = 0; i < braces.length(); i++ )
			{
				char first = braces.charAt( i++ );
				if ( i < braces.length() )
				{
					char second = braces.charAt( i );
					braceMap.put( first, second );
					countMap.put( first, 0 );
					if ( first != second )
					{
						countMap.put( second, 0 );
					}
				}
			}
		}
		//System.out.println( braceMap );
		// map< second brace, number of mismatch
		if ( source != null )
		{
			source.chars().forEach( c -> countMap.computeIfPresent( ( char ) c, ( k, v ) -> v + 1 ) );
		}
		System.out.println( countMap );
		// if first == second, check the count is even number;
		// if first != second, check first count == second count
		braceMap.entrySet().forEach( pair -> {
			char k = pair.getKey();
			char v = pair.getValue();
			if ( ( k == v && countMap.get( k ) % 2 != 0 ) ||
			( k != v && !countMap.get( k ).equals( countMap.get( v ) ) ))
			{
				System.out.println( "Mismatch found for " + k + " and " + v );
			}
		} );
	}

	@Test
	public void testParser()
	{
		String braces = "(){}[]\"\"''<>";
		solution( "[[]", braces );
		solution( "[]]", braces );
		solution( "([{\"\"\"]}]}}))'')", braces );
	}
}
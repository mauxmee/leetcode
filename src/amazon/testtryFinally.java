package amazon;


import org.junit.jupiter.api.Test;

public class testtryFinally
{
	public void test_try_finally()
	{
		try
		{
			System.out.println( "try" );
			throw new RuntimeException( "test" );
		}
		catch ( RuntimeException ignore )
		{
			System.out.println( "runtime exception" );
			return;
		}
		finally
		{
			System.out.println( "finally block" );
		}
	}
	@Test
	public void test() {
		test_try_finally();
	}
}
package multiThreadLeetCode;

/*
* Suppose you are given the following code:

class FooBar {
  public void foo() {
  for (int i = 0; i < n; i++) {
   print("foo");
   }
  }

  public void bar() {
  for (int i = 0; i < n; i++) {
   print("bar");
  }
  }
}
The same instance of FooBar will be passed to two different threads. Thread A will callfoo() while thread B will callbar().Modify the given program to output "foobar" n times.



Example 1:

Input: n = 1
Output: "foobar"
Explanation: There are two threads being fired asynchronously. One of them calls foo(), while the other calls bar(). "foobar" is being output 1 time.
Example 2:

Input: n = 2
Output: "foobarfoobar"
Explanation: "foobar" is being output 2 times.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/print-foobar-alternately
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class FooBar
{
	private int n;

	public FooBar( int n )
	{
		this.n = n;
	}

	public void foo( Runnable printFoo ) throws InterruptedException
	{

		for ( int i = 0; i < n; i++ )
		{

			// printFoo.run() outputs "foo". Do not change or remove this line.
			printFoo.run();
		}
	}

	public void bar( Runnable printBar ) throws InterruptedException
	{

		for ( int i = 0; i < n; i++ )
		{

			// printBar.run() outputs "bar". Do not change or remove this line.
			printBar.run();
		}
	}
}

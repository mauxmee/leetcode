package multiThreadLeetCode;

/*Write a program that outputs the string representation of numbers from 1 ton, however:

If the number is divisible by 3, output "fizz".
If the number is divisible by 5, output"buzz".
If the number is divisible by both 3 and 5, output"fizzbuzz".
For example, forn = 15, we output:1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.

Suppose you are given the following code:

class FizzBuzz {
public FizzBuzz(int n) { ... }              // constructor
  public void fizz(printFizz) { ... }          // only output "fizz"
  public void buzz(printBuzz) { ... }          // only output "buzz"
  public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
  public void number(printNumber) { ... }      // only output the numbers
}
Implement a multithreaded version of FizzBuzz with four threads. The same instance of FizzBuzz will be passed to four different threads:

Thread A will callfizz()to check for divisibility of 3 and outputsfizz.
Thread B will callbuzz()to check for divisibility of 5 and outputsbuzz.
Thread C will call fizzbuzz()to check for divisibility of 3 and 5 and outputsfizzbuzz.
Thread D will call number() which should only output the numbers.
通过次数11,936提交次数18,879

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/fizz-buzz-multithreaded
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
public class FizzBuzz
{
	private int n;

	public FizzBuzz(int n) {
		this.n = n;
	}

	// printFizz.run() outputs "fizz".
	public void fizz(Runnable printFizz) throws InterruptedException {

	}

	// printBuzz.run() outputs "buzz".
	public void buzz(Runnable printBuzz) throws InterruptedException {

	}

	// printFizzBuzz.run() outputs "fizzbuzz".
	public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {

	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void number(IntConsumer printNumber) throws InterruptedException {

	}
}

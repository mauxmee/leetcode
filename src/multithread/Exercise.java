package multithread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercise
{
	CopyOnWriteArrayList list = new CopyOnWriteArrayList();


	Logger m_logger = Logger.getLogger( Exercise.class.getName() );

	public void test1()
	{
		Runnable runnable = () -> System.out.println( Thread.currentThread().getName() + " is finished" );

		Thread t1 = new Thread( runnable, "t1" );
		Thread t2 = new Thread( runnable, "t2" );
		Thread t3 = new Thread( runnable, "t3" );

		try
		{
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			t3.start();
			t3.join();
		}
		catch ( InterruptedException e )
		{
			m_logger.log( Level.INFO, "Interrupted", e );
		}
	}

	public void test2()
	{
		Thread thread = new Thread( () -> {
			System.out.println( "Thread name: " + Thread.currentThread().getName() );
			System.out.println( "Thread priority: " + Thread.currentThread().getPriority() );
		} );
		thread.setName( "worker thread" );
		thread.setPriority( Thread.MAX_PRIORITY );
		thread.setUncaughtExceptionHandler( ( t, e ) -> {
			// clean up resources, log error, etc.
			System.out.println( "exception caught" );
		} );
		thread.start();
	}

	public void test3()
	{
		Thread thread = new MyThread();
		// main thread doesn't need to wait for this thread to close
		//thread.setDaemon( true );
		thread.start();
		// the thread may not repsonse to interrupt
		thread.interrupt();
	}

	public static class MyThread extends Thread
	{
		public MyThread()
		{
			super();
			setName( "my thread" );
			setPriority( NORM_PRIORITY );
		}

		@Override
		public void run()
		{
			System.out.println( "Thread name: " + getName() );
			System.out.println( "Thread priority: " + getPriority() );

			try
			{
				Thread.sleep( 50000 );
			}
			catch ( InterruptedException e )
			{
				System.out.println( "I am Interrupted. " );
			}
			double j = 0;
			for ( int i = 0; i < Integer.MAX_VALUE - 1; i++ )
			{
				j += i * Math.pow( i, 2 );
			}
			System.out.println( "j=" + j );

		}
	}

	static public class BlockingThread extends Thread
	{

	}

	public static void main( String[] args )
	{
		Exercise ex1 = new Exercise();
		//ex1.method();
		//System.out.println( "test is done" );
		//ex1.test2();
		//ex1.test3();
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("number of core: " + processors);
	}

	public static class MinMaxMetrics {
		private volatile long min = Long.MAX_VALUE;
		private volatile long max = Long.MIN_VALUE;

		public MinMaxMetrics()
		{
		}

		public synchronized  void addSample(long sample) {
			if(sample < min) {
				min = sample;
			}
			if(sample > max) {
				max = sample;
			}
		}
		public long getMin() {
			return min;
		}

		/**
		 * Returns the biggest sample we've seen so far.
		 */
		public long getMax() {
			return max;
		}

	}
}

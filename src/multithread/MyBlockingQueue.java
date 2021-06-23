package multiThread;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBlockingQueue<E>
{
	private final List<E> m_list = new LinkedList<>();
	private final int m_limit;

	public MyBlockingQueue( final int limit )
	{
		m_limit = limit;
	}

	public synchronized void put( E e )
	{
		int count = 0;
		while ( m_list.size() >= m_limit && count++ < 10 )
		{
			try
			{
				System.out.println( "waiting to put" );
				wait( 1000 );
			}
			catch ( InterruptedException ex )
			{
				ex.printStackTrace();
			}
		}
		if ( m_list.size() >= m_limit )
		{
			System.out.println( "Failed to put.queue is full after wait and check" );
		}
		else
		{
			System.out.println( "start to put. List: " + m_list + " put: " + e );
			m_list.add( e );
		}
		notifyAll();
	}

	public synchronized E take()
	{
		int count = 0;
		while ( m_list.isEmpty() && count++ < 10 )
		{
			try
			{
				System.out.println( "waiting to take" );
				wait( 1000 );
			}
			catch ( InterruptedException ex )
			{
				ex.printStackTrace();
			}
		}
		E e = null;
		if ( m_list.isEmpty() )
		{
			System.out.println( "Failed to take. nothing to take after wait and try" );
		}
		else
		{
			e = m_list.remove( 0 );
			System.out.println( "start to take. List: " + m_list + " take: " + e );
		}
		notifyAll();
		return e;
	}

	static class TestRunnable implements Runnable
	{
		private final MyBlockingQueue<Integer> m_myBlockingQueue;

		public TestRunnable( final MyBlockingQueue<Integer> myBlockingQueue )
		{
			m_myBlockingQueue = myBlockingQueue;
		}

		@Override
		public void run()
		{
			Random random = new Random();
			int r = random.nextInt( 100 );
			if ( r < 50)
			{
				m_myBlockingQueue.put( r );
			}
			else
			{
				m_myBlockingQueue.take();
			}
		}
	}

	public static void main( String[] args )
	{
		final MyBlockingQueue bq = new MyBlockingQueue( 10 );
		ExecutorService exec = Executors.newCachedThreadPool();
		for ( int i = 0; i < 100; i++ )
		{
			exec.execute( new TestRunnable( bq ) );
		}
		exec.shutdown();
		System.out.println( "all threads finished" );
	}
}
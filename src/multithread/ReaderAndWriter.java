package multithread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ReaderAndWriter<K, V>
{
	private final ReadWriteLock m_lock = new ReentrantReadWriteLock();
	private final Lock m_readLock = m_lock.readLock();
	private final Lock m_writeLock = m_lock.writeLock();
	private final Map<K, V> m_map;
	private final Logger m_logger = Logger.getLogger( ReaderAndWriter.class.getName() );

	public ReaderAndWriter( final Map<K, V> map )
	{
		m_map = map;
	}

	public V put( K key, V value )
	{
		while ( true )
		{
			if ( m_writeLock.tryLock() )
			{
				try
				{
					//m_logger.info( "put " + key + "=" + value );
					System.out.println("put " + key + "=" + value);
					return m_map.put( key, value );
				}
				finally
				{
					m_writeLock.unlock();
				}
			}
		}
	}

	public V get( K key )
	{
		while ( true )
		{
			if ( m_readLock.tryLock() )
			{
				try
				{
					V v = m_map.get( key );
					//m_logger.info( "get " + key + "=" + v );
					System.out.println("get " + key + "=" + v);
					return v;
				}
				finally
				{
					m_readLock.unlock();
				}
			}
		}
	}

	static class TestRunnable implements Runnable
	{
		private final ReaderAndWriter<String, Integer> m_rw;
		private final int m_index;
		public static final String[] s_keys = {"X", "Y", "Z" };

		public TestRunnable( final ReaderAndWriter<String, Integer> rw, final int index )
		{
			m_rw = rw;
			m_index = index;
		}

		@Override
		public void run()
		{
			Random ran = new Random();
			int r = ran.nextInt( 100 );
			String key = s_keys[ r % 3 ];
			if ( r < 30 )
			{
				m_rw.put( key, r );
			}
			else
			{
				m_rw.get( key );
			}
		}
	}

	public static void main( String[] args )
	{
		final ReaderAndWriter<String, Integer> rw = new ReaderAndWriter<>( new HashMap<>() );
		ExecutorService exec = Executors.newCachedThreadPool();
		for ( int i = 0; i < 100; i++ )
		{
			exec.execute( new TestRunnable( rw, i ) );
		}
		exec.shutdown();
	}
}

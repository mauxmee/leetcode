package multithread;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueEx
{
	public static class Producer implements Runnable
	{
		private final BlockingQueue<Integer> queue;
		private final int POISON;

		public Producer( final BlockingQueue<Integer> queue, final int P )
		{
			this.queue = queue;
			POISON = P;
		}

		@Override
		public void run()
		{
			try
			{
				process();
			}
			catch ( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
			finally
			{
				while ( true )
				{
					try
					{
						queue.put( POISON );
						System.out.println( "put poison. quit" );
						break;
					}
					catch ( InterruptedException ignore )
					{

					}
				}
			}
		}

		private void process() throws InterruptedException
		{
			for ( int i = 0; i < 20; i++ )
			{
				System.out.println( "producer put: " + i );
				queue.put( i );
				System.out.println( "queue remaining cap: " + queue.remainingCapacity() );
				Thread.sleep( 100 );
			}
		}
	}

	public static class Consumer implements Runnable
	{
		private final BlockingQueue<Integer> queue;
		private final int POISON;

		public Consumer( final BlockingQueue<Integer> queue, final int P )
		{
			this.queue = queue;
			POISON = P;
		}

		@Override
		public void run()
		{
			try
			{
				while ( true )
				{
					Integer take = queue.take();
					if ( take == POISON )
					{
						System.out.println( "eat poison, quit" );
						break;
					}
					process( take );
				}
			}
			catch ( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
		}

		private void process( Integer take ) throws InterruptedException
		{
			System.out.println( "consumer take: " + take );
			Thread.sleep( 500 );
		}
	}


	private static void test1() throws InterruptedException
	{
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>( 10 );
		int poison = Integer.MIN_VALUE;
		Thread p = new Thread( new Producer( queue, poison ) );
		p.setDaemon( true );
		p.start();
		Thread c = new Thread( new Consumer( queue, poison ) );
		c.setDaemon( true );
		c.start();

		p.join();
		c.join();

		System.out.println( "finished" );
	}

	public static class FileCrawlerProducer implements Runnable
	{
		private final BlockingQueue<File> fileQueue;
		private final FileFilter fileFilter;
		private final File f;
		private final File POISON;
		private final int N_POISON_PILL_PER_PRODUCER;

		public FileCrawlerProducer( final BlockingQueue<File> fileQueue, final FileFilter fileFilter, final File f,
		                            final File POISON,
		                            final int n_POISON_PILL_PER_PRODUCER )
		{
			this.fileQueue = fileQueue;
			this.fileFilter = fileFilter;
			this.f = f;
			this.POISON = POISON;
			N_POISON_PILL_PER_PRODUCER = n_POISON_PILL_PER_PRODUCER;
		}

		@Override
		public void run()
		{
			try
			{
				crawl( f );
			}
			catch ( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
			finally
			{
				while ( true )
				{
					try
					{
						System.out.println( Thread.currentThread().getName() + "  poison all the consumer threads" );
						for ( int i = 0; i < N_POISON_PILL_PER_PRODUCER; i++ )
						{
							fileQueue.put( POISON );
						}
						break;
					}
					catch ( InterruptedException ignore )
					{
					}
				}
			}
		}

		private void crawl( final File root ) throws InterruptedException
		{
			File[] entries = root.listFiles( fileFilter );
			if ( entries != null )
			{
				for ( File entry : entries )
				{
					if ( entry.isDirectory() )
					{
						crawl( entry );
					}
					else if ( !isIndexed( entry ) )
					{
						System.out.println( "Producer found " + entry.getAbsolutePath() );
						fileQueue.put( entry );
					}
				}
			}
		}

		private boolean isIndexed( final File entry )
		{
			return false;
		}
	}

	public static class IndexerConsumer implements Runnable
	{
		private final BlockingQueue<File> fileQueue;
		private final File POISON;

		public IndexerConsumer( final BlockingQueue<File> fileQueue, final File POISON )
		{
			this.fileQueue = fileQueue;
			this.POISON = POISON;
		}

		@Override
		public void run()
		{
			try
			{
				while ( true )
				{
					File take = fileQueue.take();
					if ( take.equals( POISON ) )
					{
						System.out.println( Thread.currentThread().getName() + " quits" );
						break;
					}
					indexFile( take );
				}
			}
			catch ( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
		}

		private void indexFile( final File f )
		{
			if ( f.isFile() )
			{
				System.out.println( Thread.currentThread().getName() + " indexing... " + f.getAbsolutePath() );
			}
		}
	}

	public static void test2()
	{
		final File POISON = new File( "This is a POISON PILL" );
		int N_PRODUCERS = 2;
		int N_CONSUMERS = Runtime.getRuntime().availableProcessors() / 2 - N_PRODUCERS;
		int N_POISON_PILL_PER_PRODUCER = N_CONSUMERS / N_PRODUCERS;
		int N_POISON_PILL_REMAIN = N_CONSUMERS % N_PRODUCERS;

		System.out.println( "N_PRODUCERS : " + N_PRODUCERS );
		System.out.println( "N_CONSUMERS : " + N_CONSUMERS );
		System.out.println( "N_POISON_PILL_PER_PRODUCER : " + N_POISON_PILL_PER_PRODUCER );
		System.out.println( "N_POISON_PILL_REMAIN : " + N_POISON_PILL_REMAIN );

		BlockingQueue<File> queue = new LinkedBlockingQueue<>(100);
		FileFilter filter = pathname -> true;

		File root = new File( "c:\\dev\\app\\" );

		for ( int i = 0; i < N_PRODUCERS - 1; i++ )
		{
			new Thread( new FileCrawlerProducer( queue, filter, root,
					POISON, N_POISON_PILL_PER_PRODUCER ) ).start();
		}
		new Thread( new FileCrawlerProducer( queue, filter, root, POISON,
				N_POISON_PILL_PER_PRODUCER + N_POISON_PILL_REMAIN ) ).start();

		for ( int i = 0; i < N_CONSUMERS; i++ )
		{
			new Thread( new IndexerConsumer( queue, POISON ) ).start();
		}
		System.out.println("finish");
	}

	public static void main( String[] args ) throws InterruptedException
	{
		//test1();
		test2();
	}

}

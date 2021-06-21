package multithread;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class executorServiceEx
{
	private static AtomicInteger count = new AtomicInteger( 0 );

	public static void main( String[] args ) throws ExecutionException, InterruptedException
	{
		ExecutorService ex = Executors.newFixedThreadPool( 10 );
		Runnable task = () -> {
			try
			{
				Thread.sleep( 1000 );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
			System.out.println( " async task" );
		};
		Future<?> future = ex.submit( task );
		try
		{
			System.out.println( "Result: " + future.get() );
		}
		catch ( InterruptedException | ExecutionException e )
		{
			e.printStackTrace();
		}
		Callable<String> callable = new Callable<String>()
		{
			@Override
			public String call() throws Exception
			{
				try
				{
					Thread.sleep( 1000 );
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace();
				}
				return " async task";
			}
		};

		future = ex.submit( callable );
		try
		{
			System.out.println( "Result: " + future.get() );
		}
		catch ( InterruptedException | ExecutionException e )
		{
			e.printStackTrace();
		}

		ScheduledExecutorService ses = Executors.newScheduledThreadPool( 1 );
		Runnable task2 = () -> System.out.println( "running task 2... " );
		ses.schedule( task2, 2, TimeUnit.SECONDS );
		ses.shutdown();

		Set<Callable<String>> callables = new HashSet<>();

		callables.add( () -> "Task 1" );
		callables.add( () -> "Task 2" );
		callables.add( () -> "Task 3" );

		List<Future<String>> futures = ex.invokeAll( callables );

		for ( Future<String> f : futures )
		{
			System.out.println( "future.get = " + f.get() );
		}
		ex.shutdown();
		ex.awaitTermination( 1000, TimeUnit.MILLISECONDS );

		ExecutorService executor = Executors.newCachedThreadPool();

		List<Callable<Integer>> listOfCallable = Arrays.asList(
				() -> 1,
				() -> 2,
				() -> 3 );

		try
		{

			List<Future<Integer>> futures2 = executor.invokeAll( listOfCallable );

			int sum = futures2.stream().map( f -> {
				try
				{
					return f.get();
				}
				catch ( Exception e )
				{
					throw new IllegalStateException( e );
				}
			} ).mapToInt( Integer::intValue ).sum();

			System.out.println( sum );

		}
		catch ( InterruptedException e )
		{// thread was interrupted
			e.printStackTrace();
		}
		finally
		{

			// shut down the executor manually
			executor.shutdown();

		}

		Runnable task3 = () -> {
			System.out.println( "count++: " + count.incrementAndGet() );
		};

		Runnable task4 = () -> {
			System.out.println( "count--: " + count.decrementAndGet() );
		};
		ScheduledExecutorService ses2 = Executors.newScheduledThreadPool( 3 );
		ScheduledFuture<?> sfutures1 = ses2.scheduleAtFixedRate( task3, 5, 1, TimeUnit.SECONDS );
		ScheduledFuture<?> sfutures2 = ses2.scheduleAtFixedRate( task4, 5, 2, TimeUnit.SECONDS );
		while ( true )
		{
			Thread.sleep( 1000 );
			if ( count.get() == 10 )
			{
				System.out.println( "Count is 10, cancel the scheduledFuture!" );
				sfutures1.cancel( true );
				sfutures2.cancel( true );
				ses2.shutdown();
				break;
			}
		}
	}
}

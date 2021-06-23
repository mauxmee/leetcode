package multiThread;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class concurrentHashMapExTest
{
	@Test
	public void givenHashMap_whenSumParallel_thenError() throws Exception
	{
		Map<String, Integer> map = new HashMap<>();
		List<Integer> sumList = parallelSum100( map, 100 );

		assertNotEquals( 1, sumList
				.stream()
				.distinct()
				.count() );
		long wrongResultCount = sumList
				.stream()
				.filter( num -> num != 100 )
				.count();

		System.out.println( "hashMap incorrect result count: " + wrongResultCount );
		assertTrue( wrongResultCount > 0 );
	}

	private List<Integer> parallelSum100( Map<String, Integer> map, int executionTimes ) throws InterruptedException
	{
		List<Integer> sumList = new ArrayList<>( 1000 );
		for ( int i = 0; i < executionTimes; i++ )
		{
			map.put( "test", 0 );
			ExecutorService executorService =
					Executors.newFixedThreadPool( 4 );
			for ( int j = 0; j < 10; j++ )
			{
				executorService.execute( () -> {
					for ( int k = 0; k < 10; k++ )
					{
						/*For each map.computeIfPresent action in parallel,
						HashMap does not provide a consistent view of what should be the present integer value,
						leading to inconsistent and undesirable results.
						As for ConcurrentHashMap, we can get a consistent and correct result
						*/
						map.computeIfPresent(
								"test",
								( key, value ) -> value + 1
						);
					}
				} );
			}
			executorService.shutdown();
			executorService.awaitTermination( 5, TimeUnit.SECONDS );
			sumList.add( map.get( "test" ) );
		}
		return sumList;
	}

	@Test
	public void givenConcurrentMap_whenSumParallel_thenCorrect()
			throws Exception
	{
		Map<String, Integer> map = new ConcurrentHashMap<>();
		List<Integer> sumList = parallelSum100( map, 1000 );

		assertEquals( 1, sumList
				.stream()
				.distinct()
				.count() );
		long wrongResultCount = sumList
				.stream()
				.filter( num -> num != 100 )
				.count();

		assertEquals( 0, wrongResultCount );
	}

	@Test
	public void givenConcurrentHashMap_whenPutWithNullKeyOrValue_thenThrowsNPE()
	{
		Map<String, Integer> map = new ConcurrentHashMap<>();
		assertThrows( NullPointerException.class, () -> map.put( null, 1 ) );
		assertThrows( NullPointerException.class, () -> map.put( "test", null ) );
	}

	@Test
	public void givenKeyPresent_whenComputeRemappingNull_thenMappingRemoved()
	{
		Map<String, Object> map = new ConcurrentHashMap<>();
		Object oldValue = new Object();
		map.put( "test", oldValue );
		map.compute( "test", ( s, o ) -> null );

		assertNull( map.get( "test" ) );
	}
	/*
	* Java 8 provides Stream support in the ConcurrentHashMap as well.
Unlike most stream methods, the bulk (sequential and parallel) operations allow concurrent modification safely.
* ConcurrentModificationException won't be thrown, which also applies to its iterators.
* Relevant to streams, several forEach*, search, and reduce* methods are also added to support
* richer traversal and map-reduce operations.
* */

	/*Under the hood, ConcurrentHashMap is somewhat similar to HashMap,
	with data access and update based on a hash table (though more complex).
And of course, the ConcurrentHashMap should yield much better performance in most
concurrent cases for data retrieval and update.

Let's write a quick micro-benchmark for get and put performance and compare
that to Hashtable and Collections.synchronizedMap,
running both operations for 500,000 times in 4 threads.*/

	@Test
	public void givenMaps_whenGetPut500KTimes_thenConcurrentMapFaster()
			throws Exception
	{
		Map<String, Object> hashtable = new Hashtable<>();
		Map<String, Object> synchronizedHashMap =
				Collections.synchronizedMap( new HashMap<>() );
		Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

		long hashtableAvgRuntime = timeElapseForGetPut( hashtable );
		System.out.println( "hashtable runtime: " + hashtableAvgRuntime );
		long syncHashMapAvgRuntime =
				timeElapseForGetPut( synchronizedHashMap );
		System.out.println( "sync hashmap runtime: " + syncHashMapAvgRuntime );
		long concurrentHashMapAvgRuntime =
				timeElapseForGetPut( concurrentHashMap );
		System.out.println( "concurrentHashMap runtime: " + concurrentHashMapAvgRuntime );

		assertTrue( hashtableAvgRuntime > concurrentHashMapAvgRuntime );
		assertTrue( syncHashMapAvgRuntime > concurrentHashMapAvgRuntime );
	}

	private long timeElapseForGetPut( Map<String, Object> map )
			throws InterruptedException
	{
		final int threadNum = Runtime.getRuntime().availableProcessors() / 2;
		ExecutorService executorService =
				Executors.newFixedThreadPool( threadNum );
		long startTime = System.nanoTime();
		for ( int i = 0; i < threadNum; i++ )
		{
			executorService.execute( () -> {
				for ( int j = 0; j < 1000000; j++ )
				{
					// ThreadLocalRandom is better for multi thread env.
					int value = ThreadLocalRandom
							.current()
							.nextInt( 10000 );
					String key = String.valueOf( value );
					map.put( key, value );
					map.get( key );
				}
			} );
		}
		executorService.shutdown();
		executorService.awaitTermination( 1, TimeUnit.MINUTES );
		return ( System.nanoTime() - startTime ) / 500000;
	}

	@Test
	public void givenConcurrentMap_whenUpdatingAndGetSize_thenError()
			throws InterruptedException
	{
		final int MAX_SIZE = 10000;
		ExecutorService executorService =
				Executors.newFixedThreadPool( 2 );
		Map<String, Object> concurrentMap = new ConcurrentHashMap<>();
		List<Integer> mapSizes = new ArrayList<>( MAX_SIZE );
		Runnable collectMapSizes = () -> {
			for ( int i = 0; i < MAX_SIZE; i++ )
			{
				mapSizes.add( concurrentMap.size() );
			}
		};
		Runnable updateMapData = () -> {
			for ( int i = 0; i < MAX_SIZE; i++ )
			{
				concurrentMap.put( String.valueOf( i ), i );
			}
		};
		executorService.execute( updateMapData );
		executorService.execute( collectMapSizes );
		executorService.shutdown();
		executorService.awaitTermination( 1, TimeUnit.MINUTES );

		int cachedSize = mapSizes.get( MAX_SIZE - 1 ).intValue();
		int realSize = concurrentMap.size();
		System.out.println( "recorded size: " + cachedSize + " actual size: " + realSize );
		assertNotEquals( MAX_SIZE, cachedSize );
		assertEquals( MAX_SIZE, realSize );
	}

	@Test
	public void givenSkipListMap_whenNavConcurrently_thenCountCorrect()
			throws InterruptedException {
		NavigableMap<Integer, Integer> skipListMap
				= new ConcurrentSkipListMap<>();
		int count = countMapElementByPollingFirstEntry(skipListMap, 10000, 4);
		System.out.println("ConcurrentSkipListMap count: " + count);
		assertEquals(10000 * 4, count);
	}

	@Test
	public void givenTreeMap_whenNavConcurrently_thenCountError()
			throws InterruptedException {
		NavigableMap<Integer, Integer> treeMap = new TreeMap<>();
		int count = countMapElementByPollingFirstEntry(treeMap, 10000, 4);
		System.out.println("TreeMap count: " + count);

		assertNotEquals(10000 * 4, count);
	}

	private int countMapElementByPollingFirstEntry(
			NavigableMap<Integer, Integer> navigableMap,
			int elementCount,
			int concurrencyLevel) throws InterruptedException {

		for (int i = 0; i < elementCount * concurrencyLevel; i++) {
			navigableMap.put(i, i);
		}

		AtomicInteger counter = new AtomicInteger(0);
		ExecutorService executorService
				= Executors.newFixedThreadPool(concurrencyLevel);
		for (int j = 0; j < concurrencyLevel; j++) {
			executorService.execute(() -> {
				for (int i = 0; i < elementCount; i++) {
					if (navigableMap.pollFirstEntry() != null) {
						counter.incrementAndGet();
					}
				}
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		return counter.get();
	}
}
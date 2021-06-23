package multiThread;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentSkipListMapTest {
    public static class Event {
        private ZonedDateTime eventTime;
        private String content;

        public Event(ZonedDateTime eventTime, String content) {
            this.eventTime = eventTime;
            this.content = content;
        }

        public ZonedDateTime getEventTime() {
            return eventTime;
        }

        public void setEventTime(ZonedDateTime eventTime) {
            this.eventTime = eventTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class EventWindowSort {
        ConcurrentSkipListMap<ZonedDateTime, String> events = new ConcurrentSkipListMap<>(
                Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));

        public void acceptEvent(Event event) {
            events.put(event.getEventTime(), event.getContent());
        }

        public ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromlastMinute() {
            return events.tailMap(ZonedDateTime.now().minusMinutes(1));
        }

        public ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThanOneMinute() {
            return events.headMap(ZonedDateTime.now().minusMinutes(1));
        }

        public void printout() {
            System.out.println("event size: " + events.size());
            events.forEach((k, v) -> System.out.println("k: " + k.toInstant().toEpochMilli() + ", v: " + v));
        }
    }


    @Test
    public void test_ConcurrentSkipListMap_snapshot() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numOfThreads = 2;
        Runnable producer = () -> IntStream
                .rangeClosed(0, 200)
                .forEach(i -> eventWindowSort.acceptEvent(
                        new Event(ZonedDateTime.now().minusSeconds(i), UUID.randomUUID().toString())
                ));

        for (int i = 0; i < numOfThreads; i++) {
            es.execute(producer);
        }

        Thread.sleep(300);
        var eventsFromlastMin = eventWindowSort.getEventsFromlastMinute();
        long eventsOlderThanOneMin = eventsFromlastMin.entrySet().stream()
                .filter(e -> e.getKey().isBefore(ZonedDateTime.now().minusMinutes(1)))
                .count();


        assertEquals(eventsOlderThanOneMin, 0);

        long eventsYoungerThanOneMin = eventsFromlastMin.entrySet().stream()
                .filter(e -> e.getKey().isAfter(ZonedDateTime.now().minusMinutes(1)))
                .count();

        System.out.println("eventsYoungerThanOneMin: " + eventsYoungerThanOneMin);
        assertTrue(eventsYoungerThanOneMin > 0);
        var eventsFromLastMinute
                = eventWindowSort.getEventsOlderThanOneMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
                .entrySet()
                .stream()
                .filter(e -> e.getKey().isBefore(ZonedDateTime.now().minusMinutes(1)))
                .count();

        System.out.println("eventsOlderThanOneMinute: " + eventsOlderThanOneMinute);
        assertTrue(eventsOlderThanOneMinute > 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
                .entrySet()
                .stream()
                .filter(e -> e.getKey().isAfter(ZonedDateTime.now().minusMinutes(1)))
                .count();

        assertEquals(eventYoungerThanOneMinute, 0);
        es.shutdown();
        es.awaitTermination(5, TimeUnit.MINUTES);
        eventWindowSort.printout();
    }


}
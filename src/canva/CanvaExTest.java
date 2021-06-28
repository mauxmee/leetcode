package canva;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanvaExTest {
    @Test
    public void test_create_design() {
        AuthContext bob = new AuthContext("bob");
        AuthContext tom = new AuthContext("tom");
        DesignService ds = new DesignServiceImpl();
        String bobid1 = ds.createDesign(bob, "test1");
        String bobid2 = ds.createDesign(bob, "test2");
        String tomid1 = ds.createDesign(tom, "test3");
        String tomid2 = ds.createDesign(tom, "test4");

        String bobdesign1 = ds.getDesign(bob, bobid1);
        assertEquals(bobdesign1, "test1");

        var bobDesigns = ds.findDesigns(bob);
        assert (bobDesigns.size() == 2);

        ds.shareDesign(bob, bobid1, tom.getUserId());

        ds.updateDesign(bob, bobid1, "new design");
        String tomshareddesign = ds.getDesign(tom, bobid1);
        assertEquals(tomshareddesign, "new design");
    }

    @Test
    public void concurrent_designservice_test() throws InterruptedException {
        final int WORKER_THREAD_NUM = 3;
        DesignService ds = new DesignServiceMultiThreadImpl();
        Set<AuthContext> ctxs = ConcurrentHashMap.newKeySet();
        // initial create two users in the queue
        ctxs.add(new AuthContext("bob"));
        ctxs.add(new AuthContext("tom"));
        int totalThreadNum = WORKER_THREAD_NUM * 3 + 1;
        CyclicBarrier barrier = new CyclicBarrier(totalThreadNum);
        // first randomly create users in one thread, 1 in 100ms, stop when there are 10 users.
        Runnable createUserTask = () -> {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }
            System.out.println("start create user task" + Thread.currentThread().getId());

            while (ctxs.size() < 10) {
                int userId = ThreadLocalRandom.current().nextInt(10);
                ctxs.add(new AuthContext("user" + userId));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // create designs, 1 in 10 ms
        Runnable createDesignTask = () -> {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }
            // wait some time for creating users.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start create design task" + Thread.currentThread().getId());
            for (int i = 0; i < 500; i++) {
                AuthContext ctx = getRandomUser(ctxs);
                String designContent = "design" + ThreadLocalRandom.current().nextInt(1000);
                ds.createDesign(ctx, designContent);
                //System.out.println("created design for " + ctx.getUserId() + " content: " + designContent );
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        };

        // then update tasks from the design, 1 in 2 ms
        Runnable updateDesignTask = () -> {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }
            // wait some time for creating designs
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start update design task" + Thread.currentThread().getId());
            for (int i = 0; i < 1000; i++) {
                AuthContext ctx = getRandomUser(ctxs);
                String designId = getRandomUserDesign(ds.getUserDesigns(ctx));
                String designContent = ds.getDesign(ctx, designId);
                if (designContent != null) {
                    ds.updateDesign(ctx, designId, "New " + designContent);
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // then share tasks from the design, 1 in 5 ms
        Runnable shareDesignTask = () -> {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }
            // wait some time for creating designs
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start share design task" + Thread.currentThread().getId());
            for (int i = 0; i < 100; i++) {
                AuthContext ctx = getRandomUser(ctxs);
                String targetUserId = getRandomUser(ctxs).getUserId();
                String designId = getRandomUserDesign(ds.getUserDesigns(ctx));
                String designContent = ds.getDesign(ctx, designId);
                if (designContent != null) {
                    ds.shareDesign(ctx, designId, targetUserId);
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(totalThreadNum);
        executorService.submit(createUserTask);
        for (int i = 0; i < WORKER_THREAD_NUM; i++) {
            executorService.submit(createDesignTask);
            executorService.submit(updateDesignTask);
            executorService.submit(shareDesignTask);
        }

        // 2 threads to update designs
        // 2 threads to share designs
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        assertEquals(ctxs.size(), 10);

    }

    private AuthContext getRandomUser(Set<AuthContext> ctxs) {
        List<AuthContext> users = ctxs.stream().toList();
        int target = ThreadLocalRandom.current().nextInt(ctxs.size());
        return users.get(target);
    }

    private String getRandomUserDesign(Map<String, StringBuffer> userDesigns) {
        List<String> designIds = userDesigns.keySet().stream().toList();
        int target = ThreadLocalRandom.current().nextInt(designIds.size());
        return designIds.get(target);
    }
}
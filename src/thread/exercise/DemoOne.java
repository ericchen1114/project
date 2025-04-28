package thread.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class DemoOne {
    public void test() throws InterruptedException {

        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        List<Integer> integerCopyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 100; i++) {
            queue.offer(i);
        }

        Runnable task = () -> {
            try {
                Integer value;
                while ((value = queue.poll()) != null) {
                    integerCopyOnWriteArrayList.add(value);
                }
            } catch (Exception e) {
                System.out.println("Exception1:" + e.getMessage());
            }
        };

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            System.out.println("run task:" + i);
            futures.add(CompletableFuture.runAsync(task));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("getTaskList:" + integerCopyOnWriteArrayList);

    }
}


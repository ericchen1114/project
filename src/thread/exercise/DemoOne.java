package thread.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DemoOne {
    public void test() throws InterruptedException {

        //使用佇列 線程安全的儲存物件，避免資料遺失
        List<String> list = new ArrayList<>();
        final Queue<String> getTaskList = new ConcurrentLinkedQueue<>();
        for (int i = 1; i <= 100; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println("list:" + list.toString());

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                try {
                    for (String str : list) {
                        getTaskList.offer(str);
                    }
                } catch (Exception e) {

                    System.out.println("Exception1:" + e.getMessage());
                }

            }
        };

        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                try {
                    for (String str : list) {
                        getTaskList.offer(str);
                    }
                } catch (Exception e) {
                    System.out.println("Exection2:" + e.getMessage());
                }

            }
        };


        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        // 這樣寫getTaskList=[] 因為主程序和副程序問題造成的
        thread1.start();
        thread2.start();
        // 必須等待主副程序做完再Add
        thread1.join();
        thread2.join();

        System.out.println("getTaskList:"+getTaskList);
    }
}


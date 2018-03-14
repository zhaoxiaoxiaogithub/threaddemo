import java.util.Random;
import java.util.concurrent.*;

public class Main1blockingQueue {
    public static void main(String[] args) {
        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(100);//最大容量100

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(23);
//3个生产者
        for (int i = 0; i < 3; i++) {
            executor.submit(new Thread(()->{
                while (true){
                    try {
                        String msg = Thread.currentThread().getName()+ " producer :" + (long) (Math.random() * 1000);
                        System.out.println(msg);
                        linkedBlockingDeque.put(msg);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
//20个消费者
        for (int i = 0; i < 20; i++) {
            executor.submit(new Thread(()->{
                while (true){
                    try {
                        System.out.println(Thread.currentThread().getName()+ " consumer :" + linkedBlockingDeque.take());
                        linkedBlockingDeque.take();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

    }


}




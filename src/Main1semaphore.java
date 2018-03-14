import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Food {
    private String name;

    public Food(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

class Plate {
    private List<Food> plate = new ArrayList(100);
    Semaphore mutex = new Semaphore(1);
    Semaphore isFull = new Semaphore(100);

    Semaphore isEmpty = new Semaphore(0);

    public void put(Food food) throws InterruptedException {
        //大于0，就放行
        //acquire，就是减操作，如果小于0，就阻塞
        //release，就是加操作，如果大于0，就不会被阻塞
        isFull.acquire();
        try {
            mutex.acquire();
            plate.add(food);
        } finally {
            mutex.release();
            isEmpty.release();
        }
    }

    public Food take() throws InterruptedException {
        Food food = null;
        isEmpty.acquire();
        try {
            mutex.acquire();
            food = plate.remove(0);
        } finally {
            mutex.release();
            isFull.release();
        }
        return food;
    }

}

//消费者
class Consumer implements Runnable {
    private Plate plate;
    private String name;

    public Consumer(Plate plate, String name) {
        this.plate = plate;
        this.name = name;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("consumer[ " + name + " ]: "+ plate.take());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

//生产者
class Producer implements Runnable {
    private Plate plate;
    private String name;

    public Producer(Plate plate, String name) {
        this.plate = plate;
        this.name = name;
    }

    public void run() {

        while (true) {
            try {
                Food foodle = new Food("name-" + (long) (Math.random() * 1000));
                System.out.println("produce[ " + name + " ]: " + foodle);
                plate.put(foodle);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}

public class Main1semaphore {
    public static void main(String args[]) {
        Plate plate = new Plate();
        //线程池管理
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0;i<23;i++){
            if(i<3){
                service.execute(new Producer(plate,"p"+i));
            }
            service.execute(new Consumer(plate,"c"+i));
        }


    }
}
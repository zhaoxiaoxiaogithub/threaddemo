import java.util.Date;
import java.util.concurrent.*;

public class Main2 {
    public static void main(String[] args) throws BrokenBarrierException,InterruptedException {
        int num=3;//3个人
        CyclicBarrier cyclicBarrier=new CyclicBarrier(num);
        ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
        for(int i=0;i<num;i++){
            threadPoolExecutor.submit(new Action(cyclicBarrier,"p"+i));
        }
        do{
            //System.out.println("导游等待中。。。。。。");
        }while (threadPoolExecutor.getCompletedTaskCount()!=num);
        threadPoolExecutor.shutdown();
        //人到齐
        System.out.println("go go go。。。。。");
    }
}
class Action implements  Runnable{
    CyclicBarrier cyclicBarrier;
    String name;

    public Action(CyclicBarrier cyclicBarrier, String name) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    @Override
    public void run() {
        eatBreakfast(name);
        setOff(name);
        arrive(name);
        try{
            cyclicBarrier.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (BrokenBarrierException e){
            e.printStackTrace();
        }
    }

    //吃饭
    private void eatBreakfast(String name){
        System.out.printf("%s: %s Task :eat breakfast started on: %s\n ",Thread.currentThread().getName(),name,new Date());
        //休眠
        try {
            Long duration=(long)(Math.random()*5);
            System.out.printf("%s: %s Task :eat breakfast during %d seconds\n ",Thread.currentThread().getName(),name,duration);
            TimeUnit.SECONDS.sleep(duration);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf("%s: %s Task :eat breakfast Finished on: %s\n ",Thread.currentThread().getName(),name,new Date());
    }
//出发
    private void setOff(String name){
        System.out.printf("%s: %s Task :setOff started on: %s\n ",Thread.currentThread().getName(),name,new Date());
        //休眠
        try {
            Long duration=(long)(Math.random()*5);
            System.out.printf("%s: %s Task :setOff during %d seconds\n ",Thread.currentThread().getName(),name,duration);
            TimeUnit.SECONDS.sleep(duration);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf("%s: %s Task :setOff Finished on: %s\n ",Thread.currentThread().getName(),name,new Date());
    }
    //到达集合点
    private void arrive(String name){
        System.out.printf("%s: %s Task :Arrived: %s\n ",Thread.currentThread().getName(),name,new Date());
    }
}
public class Test {
    public static Object a=new Object();
    public static Object b=new Object();
    public static Object c=new Object();

    public class Runner1 implements  Runnable {

        @Override
        public void run() {
            for(int i=0;i<10;i++) {
                try {
                    synchronized (a) {
                        synchronized (b) {
                            System.out.println("A");
                            b.notify();//t1唤醒b
                        }
                        if (i < 9) {
                            a.wait();
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                }


        }
    }
    public  class Runner2 implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    synchronized (b) {
                        synchronized (c) {
                            System.out.println("B");
                            c.notify();
                        }
                        if (i < 9) {
                            b.wait();

                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public class  Runner3 implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    synchronized (c) {
                        synchronized (a) {
                            System.out.println("C");
                            a.notify();
                        }
                        if (i < 9) {
                            c.wait();
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Test test=new Test();
        Thread thread1=new Thread(test.new Runner1(),"thread1");
        Thread thread2=new Thread(test.new Runner2(),"thread2");
        Thread thread3=new Thread(test.new Runner3(),"thread3");
        thread1.start();
        try {
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread2.start();
        try {
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread3.start();

    }

}

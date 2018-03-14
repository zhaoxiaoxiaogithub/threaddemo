

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by
 */
public class Main3 {
    public static void main(String[] args) {
        /*
         * 初始化二维数组, 10行,每行100个数字(100列)
         */
        final int ROWS = 10;					// 行
        final int NUMBERS = 100;				// 列
        MatrixMock mock = new MatrixMock(ROWS, NUMBERS);

        //执行器
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        //处理结果
        List<Future<Integer>> resultList = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(ROWS);
        for(int i = 0;i<ROWS;i++){
            Future<Integer> result = executor.submit(new Task(mock.getRow(i), countDownLatch));
            resultList.add(result);
        }

        try {
            //等待所有线程处理完毕
            countDownLatch.await();

            executor.shutdown();

            int result_sum = 0;
            for(Future<Integer> result : resultList){
                result_sum +=result.get();
            }
            System.out.println("实际总和：" + result_sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
//计算每个数组的和
class Task implements Callable<Integer>{

    private int[] nums;

    private CountDownLatch countDownLatch;

    public Task(int[] nums, CountDownLatch countDownLatch) {
        this.nums = nums;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public Integer call() {
        int result = 0;

        for(Integer num : nums)
            result += num;
        countDownLatch.countDown();
        return result;
    }
}
/**
 * 生成1到100之间的随机矩阵
 **/
class MatrixMock {

    /**
     * 随机数的二维数组
     */
    private int data[][];


    /**
     * 统计要查找的数字出现的数字
     * @param size		数组的行数
     * @param length	数组的列数
     */
    public MatrixMock(int size, int length) {
        int sum = 0;	// 总和
        data = new int[size][length];

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(100);
                // 统计要查找的数字出现的数字
                sum+=data[i][j];
            }
        }

        System.out.printf("Mock: All data sum is %d.\n", sum);
    }

    /**
     * 返回二维数组的一行
     */
    public int[] getRow(int row) {
        if ((row >= 0) && (row < data.length)) {
            return data[row];
        }

        return null;
    }
}
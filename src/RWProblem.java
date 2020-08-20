import java.util.concurrent.Semaphore;

/**
 * @author 江海彬
 * @since 2020-08-20 15:30
 */
public class RWProblem {
    private final Semaphore x = new Semaphore(1);
    private final Semaphore wr = new Semaphore(1);
    private int readCount = 0;

    /**
     * 读者
     */
    class Reader implements Runnable {
        /**
         * 线程的序号
         */
        private final String num;
        /**
         * 线程操作申请时间
         */
        private final long startTime;
        /**
         * 线程操作申请时间
         */
        private final long workTime;

        Reader(String num, long startTime, long workTime) {
            this.num = num;
            this.startTime = startTime * 1000;
            this.workTime = workTime * 1000;
            System.out.println(num + "号读进程被创建");
        }

        /**
         * 读过程
         */
        private void read() {
            System.out.println(num + "号线程开始读操作");
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num + "号线程结束读操作");
        }

        @Override

        public void run() {
            try {
                Thread.sleep(startTime);
                System.out.println(num + "号线程发出读操作申请");
                x.acquire();
                readCount++;
                if (readCount == 1) {
                    wr.acquire();
                }
                x.release();
                read(); //读过程
                x.acquire();
                readCount--;
                if (readCount == 0) {
                    wr.release();
                }
                x.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写者
     */
    class Writer implements Runnable {

        private final String num; //线程的序号
        private final long startTime; //线程操作申请时间
        private final long workTime; //线程的执行时间

        Writer(String num, long startTime, long workTime) {
            this.num = num;
            this.startTime = startTime * 1000;
            this.workTime = workTime * 1000;
            System.out.println(num + "号写进程被创建");

        }

        /**
         * 写过程
         */
        private void write() {
            System.out.println(num + "号线程开始写操作");
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num + "号线程结束写操作");
        }

        @Override
        public void run() {
            try {
                Thread.sleep(startTime);
                System.out.println(num + "号线程发出写操作申请");
                wr.acquire();
                write(); //写过程
                wr.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

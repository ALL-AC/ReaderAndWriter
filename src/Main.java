import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author 江海彬
 * @since 2020-08-20 15:36
 */
public class Main {
    public static void main(String[] args) {
        RWProblem readerAndWriter = new RWProblem();
        String filepath = "src/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                //线程数
                String num = words[0];
                //线程类型
                String type = words[1];
                //线程操作申请时间
                long startTime = Long.parseLong(words[2]);
                //线程的执行时间
                long workTime = Long.parseLong(words[3]);
                if ("R".equals(type)) {
                    RWProblem.Reader reader = readerAndWriter.new Reader(num, startTime, workTime);
                    new Thread(reader).start();

                } else if ("W".equals(type)) {
                    RWProblem.Writer writer = readerAndWriter.new Writer(num, startTime, workTime);
                    new Thread(writer).start();
                } else {
                    System.out.println("测试文件出错");
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

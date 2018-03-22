import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 实际执行任务的业务类,需要实现Job接口
 *
 * @author feizi
 * @remark
 * @time 2015-3-23下午2:48:57
 */
public class TestJobOne implements Job {

    /**
     * 执行任务的方法
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("================执行任务一...." + this.toString());
        context.getJobDetail().getJobDataMap().forEach((key, value) -> {
            System.out.println(key + "::::::::" + value);
        });
        context.getTrigger().getJobDataMap().forEach((key, value) -> {
            System.out.println(key + "::::::::::::::" + value);
        });
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=============== 任务一 结束=-================");
        //do more...这里可以执行其他需要执行的任务  
    }

}
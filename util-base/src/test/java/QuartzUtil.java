import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

/**
 * 任务调度公共类
 *
 * @author feizi
 * @remark
 * @time 2015-3-23下午3:04:12
 */
public class QuartzUtil {

    private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";//任务组
    private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";//触发器组
    private static Logger log = LoggerFactory.getLogger(QuartzUtil.class);//日志

    //创建一个SchedulerFactory工厂实例
    private static SchedulerFactory sf = new StdSchedulerFactory();


    //通过SchedulerFactory构建Scheduler对象
    private static Scheduler sche;

    static {
        try {
            sche = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加任务的方法
     *
     * @param jobName     任务名
     * @param triggerName 触发器名
     * @param jobClass    执行任务的类
     * @param seconds     间隔时间
     * @throws SchedulerException
     */
    public static void addJob(String jobName, String triggerName, Class<? extends Job> jobClass, int seconds) throws SchedulerException {
        log.info("==================initialization=================");

        log.info("===================initialize finshed===================");

        log.info("==============add the Job to Scheduler==================");

        //用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, JOB_GROUP_NAME)
                .usingJobData("key1", 1L)
                .usingJobData("key2", "s1")
                .build();

        JobDataMap jobDataMap = new JobDataMap();
        Runnable runnable = () -> {
            System.out.println("123");
        };
        jobDataMap.put("aaaa", runnable);

        //构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()//创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(triggerName, TRIGGER_GROUP_NAME)//给触发器起一个名字和组名
                .usingJobData("trigger", "triggerData")
                .usingJobData(jobDataMap)
                .startNow()//立即执行
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(seconds)//时间间隔  单位：秒
                                .repeatForever()//一直执行
                )
                .build();//产生触发器

        //构建一个触发器，规定触发的规则
        Trigger trigger1 = TriggerBuilder.newTrigger()//创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity("trigger33", TRIGGER_GROUP_NAME)//给触发器起一个名字和组名
                .usingJobData("trigger", "triggerData")
                .usingJobData(jobDataMap)
                .startNow()//立即执行
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(seconds)//时间间隔  单位：秒
                                .repeatForever()//一直执行
                ).forJob(jobDetail)
                .build();//产生触发器


        //向Scheduler中添加job任务和trigger触发器
        sche.scheduleJob(jobDetail, new TreeSet<Trigger>(Arrays.asList(trigger, trigger1)), false);
//        sche.scheduleJob(trigger1);
//        sche.scheduleJob(jobDetail, Collections.singletonMap(trigger1, "").keySet(), false);
        //启动
        sche.start();
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
//            //添加第一个任务  每隔10秒执行一次
//            QuartzUtil.addJob("job1", "trigger1", TestJobOne.class, 2);

            //添加第二个任务  每隔20秒执行一次
            QuartzUtil.addJob("Job2", "trigger2", TestJobTwo.class, 5);
//            QuartzUtil.addJob("Job3", "trigger3", TestJobTwo.class, 1);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
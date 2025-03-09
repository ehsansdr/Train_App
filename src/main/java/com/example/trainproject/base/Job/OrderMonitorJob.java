package com.example.trainproject.base.Job;

import com.example.trainproject.base.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("'${Train.enableJob}' == 'true' ")
public class OrderMonitorJob extends BaseScheduler implements Job {

  private final OrderRepository orderRepository;

  public OrderMonitorJob(OrderRepository orderRepository) {
    super("OrderMonitor", 1);
    this.orderRepository = orderRepository;
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println(orderRepository.findAll());
    System.out.println("OrderMonitorJob executed");
  }

  @Bean
  @Qualifier("orderMonitorJobDetail")
  public JobDetail orderMonitorJobDetail() {
    return JobBuilder.newJob(OrderMonitorJob.class) // be care full about this of the anouther class come here we will active another job
        .withIdentity("orderMonitorJob", "group4") // Ensure you are using a unique name and group
        .storeDurably()  // Keeps the job even if it isn't triggered
        .build();
  }

  @Bean
  @Qualifier("orderMonitorTrigger")
  public Trigger orderMonitorTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob("orderMonitorJob", "group4")
        .withIdentity("orderMonitorTrigger", "group4")
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)
            .repeatForever())
        .build();
  }

  @Bean
  @Qualifier("orderMonitorScheduler")
  public Scheduler orderMonitorScheduler(
      @Qualifier("orderMonitorJobDetail") JobDetail jobDetail,
      @Qualifier("orderMonitorTrigger") Trigger trigger,
      SchedulerFactoryBean factory
  ) {
    Scheduler scheduler = factory.getScheduler();

    return scheduler;
  }
}

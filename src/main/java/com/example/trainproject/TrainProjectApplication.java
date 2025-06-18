package com.example.trainproject;


import com.example.trainproject.Model.Task;
import com.example.trainproject.Model.User;
import com.example.trainproject.constants.CallSatus;
import com.example.trainproject.constants.CallType;
import com.example.trainproject.constants.Gender;
import com.example.trainproject.constants.KycStep;
import com.example.trainproject.constants.Role;
import com.example.trainproject.constants.TaskTopic;
import com.example.trainproject.constants.TaskType;
import com.example.trainproject.repository.TaskRepository;
import com.example.trainproject.repository.UserRepository;
import com.github.javafaker.Faker;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableFeignClients
@AllArgsConstructor
//@EnableElasticsearchRepositories(basePackages = "com.example.trainproject.repository")
public class TrainProjectApplication {

  private UserRepository employeeRepository;

  public static void main(String[] args) {
    System.out.println(System.currentTimeMillis());
    SpringApplication.run(TrainProjectApplication.class, args);
  }


  @Bean
  public CommandLineRunner init(UserRepository employeeRepository) {
    return args -> {
      for (int i = 0; i < 10; i++) {
        Faker faker = new Faker();
        User user = new User();
        user.setGender(Gender.FEMALE);
        user.setRole(Role.ADMIN);
        user.setNationalCode(faker.number().digits(10));
        user.setKycStep(KycStep.ACCEPTED);
        employeeRepository.save(user);
      }
    };
  }
  @Bean
  public CommandLineRunner initTask(TaskRepository taskRepository, UserRepository userRepository) {
    return args -> {
      for (int i = 0; i < 10; i++) {
        Faker faker = new Faker();
        Task task = new Task();
        task.setTaskTopic(TaskTopic.BANKING_QUESTIONS);
        task.setCallType(CallType.INBOUND);
        task.setTaskType(TaskType.TRAIN);
        task.setCallSatus(CallSatus.ANSWERED);
        task.setCreatedBy(userRepository.findByNationalCode("1592570809"));
        task.setUpdatedBy(userRepository.findByNationalCode("3045027948"));
        taskRepository.save(task);
      }
    };
  }


}

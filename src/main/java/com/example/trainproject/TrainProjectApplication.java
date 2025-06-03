package com.example.trainproject;


import com.example.trainproject.Model.Task;
import com.example.trainproject.Model.User;
import com.example.trainproject.repository.TaskRepository;
import com.example.trainproject.repository.UserRepository;
import com.github.javafaker.Faker;
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
    SpringApplication.run(TrainProjectApplication.class, args);
  }


  @Bean
  public CommandLineRunner init(UserRepository employeeRepository) {
    return args -> {
      for (int i = 0; i < 10; i++) {
        Faker faker = new Faker();
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setSurname(faker.ancient().god());
        user.setName(faker.name().firstName());
        employeeRepository.save(user);
      }
    };
  }
  // @Bean
  public CommandLineRunner initTask(TaskRepository taskRepository) {
    return args -> {
      for (int i = 0; i < 10; i++) {
        Faker faker = new Faker();
        Task task = new Task();
        taskRepository.save(task);
      }
    };
  }


}

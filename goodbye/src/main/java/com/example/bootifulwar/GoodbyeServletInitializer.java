package com.example.bootifulwar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class GoodbyeServletInitializer
    extends SpringBootServletInitializer
{

  @Value("${messageForUser}")
  private String message;

  @Value("${canWeBuildIt:'No!'}")
  private String canWeBuildIt;

  public static void main(String[] args) {

    SpringApplication.run(GoodbyeServletInitializer.class, args);
  }

  @Scheduled(fixedRate = 2000)
  public void sayGoodbyeTo() {

    log.info(message + " Goodbye!");
  }

  @Override
  public SpringApplicationBuilder configure(SpringApplicationBuilder application) {

    log.info(
        "\n*********************\n" +
            "Can we build it?\n" +
            canWeBuildIt +
            "\n*********************\n");
    return application.sources(GoodbyeServletInitializer.class);
  }
}

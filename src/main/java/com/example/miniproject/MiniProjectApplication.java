package com.example.miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectApplication {

    //    public static final String APPLICATION_LOCATIONS = "spring.config.location="
//            + "classpath:application.yml,"
//            + "classpath:aws.yml";
//
//    public static void main(String[] args) {
//        new SpringApplicationBuilder(MiniProjectApplication.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
//    }

    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("KST"));
    }

    public static void main(String[] args) {
        SpringApplication.run(MiniProjectApplication.class, args);
    }
}


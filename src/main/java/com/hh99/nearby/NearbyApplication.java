package com.hh99.nearby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class NearbyApplication {
    public static void main(String[] args) {
        SpringApplication.run(NearbyApplication.class, args);
    }


//    @PostConstruct
//    public void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//    }
}

package com.opencourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xujianxing@sensetime.com
 * @date 2023年09月04日 15:34
 */
@SpringBootApplication(scanBasePackages = {"com.opencourse"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

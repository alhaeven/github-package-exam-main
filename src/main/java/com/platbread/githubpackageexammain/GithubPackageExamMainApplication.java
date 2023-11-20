package com.platbread.githubpackageexammain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(
        scanBasePackages = {"com.platbread"}
)
public class GithubPackageExamMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubPackageExamMainApplication.class, args);
    }

}

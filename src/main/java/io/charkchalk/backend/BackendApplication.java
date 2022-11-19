package io.charkchalk.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.shell.standard.ShellComponent;

// Todo: Remove the exclude when the security setting is implemented
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ShellComponent
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}

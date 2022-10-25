package io.charkchalk.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;

@SpringBootApplication
@ShellComponent
public class BackendApplication {

    private BackendApplication() {}

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}

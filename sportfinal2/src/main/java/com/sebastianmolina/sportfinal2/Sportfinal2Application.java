package com.sebastianmolina.sportfinal2;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sportfinal2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Sportfinal2Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Api funcionando correctamente");
    }

}

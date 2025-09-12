package com.sebastianmolina.mechanicalshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MechanicalShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MechanicalShopApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Api mecanica funcionando bien");
    }
}

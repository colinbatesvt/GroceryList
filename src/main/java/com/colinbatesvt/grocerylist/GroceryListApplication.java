package com.colinbatesvt.grocerylist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(exclude = SqlInitializationAutoConfiguration.class)
public class GroceryListApplication {

	public static void main(String[] args) {
		try {
			System.out.println("Grocery List Application Launched");
			SpringApplication.run(GroceryListApplication.class, args);
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			t.printStackTrace();
		}
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}

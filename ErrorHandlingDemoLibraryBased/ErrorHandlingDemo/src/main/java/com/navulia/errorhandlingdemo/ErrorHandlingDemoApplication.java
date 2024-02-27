package com.navulia.errorhandlingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.navulia.genericerrorhandler","com.navulia.errorhandlingdemo"})
public class ErrorHandlingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErrorHandlingDemoApplication.class, args);
	}

}


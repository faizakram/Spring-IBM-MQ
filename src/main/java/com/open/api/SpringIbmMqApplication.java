package com.open.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SpringIbmMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIbmMqApplication.class, args);
	}

}

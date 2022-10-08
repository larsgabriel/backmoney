package com.moneyestudoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.moneyestudoapi.config.properties.BackProperties;

@SpringBootApplication
@EnableConfigurationProperties(BackProperties.class)
public class MoneyestudoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyestudoApiApplication.class, args);
	}

}

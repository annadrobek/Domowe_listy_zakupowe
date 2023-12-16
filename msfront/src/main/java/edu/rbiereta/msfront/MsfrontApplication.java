package edu.rbiereta.msfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsfrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsfrontApplication.class, args);
	}

}

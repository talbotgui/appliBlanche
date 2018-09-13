package com.guillaumetalbot.applicationblanche.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ BatchApplication.PACKAGE_BATCH })
public class BatchApplication {

	public static final String PACKAGE_BATCH = "com.guillaumetalbot.applicationblanche.batch";

	public static void main(final String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
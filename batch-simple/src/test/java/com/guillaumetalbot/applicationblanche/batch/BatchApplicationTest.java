package com.guillaumetalbot.applicationblanche.batch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.guillaumetalbot.applicationblanche.batch.BatchApplication;

@SpringBootApplication
@ComponentScan({ BatchApplication.PACKAGE_BATCH })
public class BatchApplicationTest {

}
package com.experis.worldoffice.batchservice.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {

    private static final Logger LOG = LogManager.getLogger(ScheduleConfiguration.class);
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Value("${loadProducts.inputFile.url}")
    private Resource inputResource;

    public static void main(String[] args) {
        SpringApplication.run(ScheduleConfiguration.class, args);
    }

    @Scheduled(cron = "*/60 * * * * ?")
    public void perform() throws Exception {
        if (inputResource.exists()) {
            JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
            jobLauncher.run(job, params);
        } else {
            LOG.warn("No resource exist for run job");
        }
    }
}
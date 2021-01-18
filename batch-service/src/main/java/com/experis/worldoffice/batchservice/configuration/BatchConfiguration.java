package com.experis.worldoffice.batchservice.configuration;

import com.experis.worldoffice.batchservice.dto.ProductDto;
import com.experis.worldoffice.batchservice.job.experis.CleanDBTasklet;
import com.experis.worldoffice.batchservice.job.experis.MoveFilesTasklet;
import com.experis.worldoffice.batchservice.job.experis.ProductProcessor;
import com.experis.worldoffice.batchservice.job.experis.ProductWriter;
import com.experis.worldoffice.batchservice.model.entity.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MoveFilesTasklet moveFilesTasklet;

    @Autowired
    private CleanDBTasklet cleanDBTasklet;



    @Value("${loadProducts.inputFile.url}")
    private Resource inputResource;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
            .get("readCSVFileJob")
            .incrementer(new RunIdIncrementer())
            .start(cleabDb())
            .next(step())
            .next(moveFiles())
            .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
            .get("step")
            .<ProductDto, Product>chunk(5)
            .reader(reader())
            .processor(proccesor())
            .writer(writer())
            .build();
    }

    @Bean
    public ItemProcessor<ProductDto,Product> proccesor() {
        return new ProductProcessor();
    }

    @Bean
    public ItemWriter<Product> writer() {
        return new ProductWriter();
    }


    @Bean
    public FlatFileItemReader<ProductDto> reader() {
        FlatFileItemReader<ProductDto> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<ProductDto> lineMapper() {
        DefaultLineMapper<ProductDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "brand", "price", "stock", "state", "discount");
        lineTokenizer.setIncludedFields(0, 1, 2,3,4,5);
        BeanWrapperFieldSetMapper<ProductDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductDto.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    protected Step moveFiles() {
        return stepBuilderFactory
            .get("moveFiles")
            .tasklet(moveFilesTasklet)
            .build();
    }

    @Bean
    protected Step cleabDb(){
        return stepBuilderFactory
            .get("cleanDb")
            .tasklet(cleanDBTasklet)
            .build();
    }
}
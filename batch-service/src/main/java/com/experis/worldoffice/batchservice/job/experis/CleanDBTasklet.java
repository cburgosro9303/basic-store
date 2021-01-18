package com.experis.worldoffice.batchservice.job.experis;

import com.experis.worldoffice.batchservice.repository.ProductRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CleanDBTasklet implements Tasklet {

    private ProductRepository productRepository;

    public CleanDBTasklet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        productRepository.deleteAll();
        if(productRepository.count() >0){
            throw new Exception("Database don't let truncate products table");
        }
        return RepeatStatus.FINISHED;

    }

}
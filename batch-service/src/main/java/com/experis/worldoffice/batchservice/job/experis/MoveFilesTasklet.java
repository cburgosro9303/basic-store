package com.experis.worldoffice.batchservice.job.experis;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileSystemException;

@Component
public class MoveFilesTasklet implements Tasklet {

    @Value("/tmp/input.csv")
    private String inputFile;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String outputFile = "/tmp/processed_at_"+System.currentTimeMillis()+".csv";
        File fileToMove = new File(inputFile);
        boolean isMoved = fileToMove.renameTo(new File(outputFile));
        if (!isMoved) {
            throw new FileSystemException(outputFile);
        }
        return RepeatStatus.FINISHED;

    }

}
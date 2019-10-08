package com.hari.springbatchcsvreader.config;

import com.hari.springbatchcsvreader.model.Movie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Movie> itemReader,
                   ItemProcessor itemProcessor,
                   ItemWriter<Movie> itemWriter) {

        // Link what happens in a Step
        Step step = stepBuilderFactory.get("csv-file-load")
                .<Movie, Movie>chunk(1) // Prefixed with <Movie,Movie> because input is Movie and output is Movie
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("csv-processor")
                .incrementer(new RunIdIncrementer()) // increment is needed as spring batch uses database to maintain execution state
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader itemReader(@Value("${input}") Resource resource) {

        System.out.println("Reading from file...");

        FlatFileItemReader<Movie> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setStrict(false);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;

    }

    @Bean
    public LineMapper<Movie> lineMapper() {
        DefaultLineMapper<Movie> defaultLineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<Movie> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "review", "revenue"});

        fieldSetMapper.setTargetType(Movie.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}

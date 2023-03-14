package com.batch.config;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.batch.entities.Data;

import javax.sql.DataSource;



@Configuration
@EnableBatchProcessing
public class batchConfig {
    @Autowired
    private DataSource dataSource;//provides access to a data source whose information we have given in application.prop
    @Autowired
    private JobBuilderFactory jobBuilderFactory;//provides methods for creating and configuring Job objects
    @Autowired
    private StepBuilderFactory stepBuilderFactory;//provides methods for creating and configuring Step objects

    
    @Bean
    public FlatFileItemReader<Data> reader(){
        FlatFileItemReader<Data> reader = new FlatFileItemReader<>(); //reads data from a flat file
        reader.setResource(new PathResource("C:\\Users\\divyam.baukhandi\\eclipse-workspace\\batch\\src\\main\\resources\\WHO-COVID-19-global-data.csv")); // reads data from the input resource
        reader.setLineMapper(getLineMapper()); //used to map the line which we will read
        reader.setLinesToSkip(1); //to skip the first line of the file (header row)
        return reader;
    }

    private LineMapper<Data> getLineMapper() {
        // lineMapper Subclass
        DefaultLineMapper<Data> lineMapper = new DefaultLineMapper<>();//subclass of LineMapper that provides a default implementation for mapping lines of data to objects
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();//will be used read the coloun which we want from the file
        lineTokenizer.setNames(new String[]{"Date_reported","Country_code","Country","WHO_region","New_cases","Cumulative_cases","New_deaths","Cumulative_deaths"});// to read columns
        lineTokenizer.setIncludedFields(new int[]{0,1,2,3,4,5,6,7});// fields which we want to include

        BeanWrapperFieldSetMapper<Data>  fieldSetMapper = new BeanWrapperFieldSetMapper<>();// used to set the colomns in the class which we want
        fieldSetMapper.setTargetType(Data.class);// to give target class

        lineMapper.setLineTokenizer(lineTokenizer);////tokenizer that can handle lines of data that are delimited by a specified character(,)
        lineMapper.setFieldSetMapper(fieldSetMapper);// used to set the field

        return lineMapper;
    }

    @Bean
    public Data_Processor processor(){
        return new Data_Processor();
    }

    @Bean
    public JdbcBatchItemWriter<Data> writer(){
        JdbcBatchItemWriter<Data> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Data>());//map the properties of the Data objects to the  SQL query parameters(source of parameter)

        // to insert data into the database
        writer.setSql("insert into data(Date_reported,Country_code,Country,WHO_region,New_cases,Cumulative_cases,New_deaths,Cumulative_deaths) values (:Date_reported,:Country_code,:Country,:WHO_region,:New_cases,:Cumulative_cases,:New_deaths,:Cumulative_deaths)");
        writer.setDataSource(this.dataSource);//used to connect to the database

        return writer;
    }

    @Bean
    public Job importDataJob(){

        return this.jobBuilderFactory.get("Data_Import_Job")
                .incrementer(new RunIdIncrementer()) //generate a unique run id for each execution of the job
                .flow(step1())//to specify the Step instance that will be executed as part of the job
                .end()//to indicate that the job configuration is complete
                .build(); //to create the Job instance.
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<Data , Data>chunk(15) //to specify the size of the chunk for processing the data
                .reader(reader())//to specify the ItemReader instance that will be used to read data from the input file
                .processor(processor())//to specify the ItemProcessor instance that will be used to process the data
                .writer(writer())//to specify the ItemWriter instance that will be used to write the data to the database
                .build();//to create the Step instance
    }
}

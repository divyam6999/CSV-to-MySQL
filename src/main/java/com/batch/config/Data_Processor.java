package com.batch.config;
import org.springframework.batch.item.ItemProcessor;

import com.batch.entities.Data;


public class Data_Processor implements ItemProcessor<Data, Data>{
    @Override
    public Data process(Data data_init) throws Exception {
        return data_init;
    }
}

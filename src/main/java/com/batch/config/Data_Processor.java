package com.batch.config;
import org.springframework.batch.item.ItemProcessor;

import com.batch.entities.Data;


public class Data_Processor implements ItemProcessor<Data, Data>{
    @Override
    public Data process(Data data) throws Exception {

    	 if(data.getCountry().equals("France")) {
             return new Data(
        		data.getDate_reported(),
        		data.getCountry_code().toLowerCase(),
        		data.getCountry().toUpperCase(),
        		data.getWHO_region().toLowerCase(),
        		data.getNew_cases(),
        		data.getCumulative_cases(),
        		data.getNew_deaths(),
        		data.getCumulative_deaths()
        		
        		);
         }else{
             return null;
         }
     }
}

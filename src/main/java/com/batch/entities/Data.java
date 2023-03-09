package com.batch.entities;

public class Data {
    private String Date_reported;
    private String Country_code;
    private String Country;
    private String WHO_region;
    private int New_cases;
    private int Cumulative_cases;
    private int New_deaths;
    private int Cumulative_deaths;

    public Data() {

    }
    
   
    

    public Data(String date_reported, String country_code, String country, String WHO_region, int new_cases,
                     int cumulative_cases, int new_deaths, int cumulative_deaths) {
        this.Date_reported = date_reported;
        this.Country_code = country_code;
        this.Country = country;
        this.WHO_region = WHO_region;
        this.New_cases = new_cases;
        this.Cumulative_cases = cumulative_cases;
        this.New_deaths = new_deaths;
        this.Cumulative_deaths = cumulative_deaths;
    }

    public String getDate_reported() {
        return Date_reported;
    }

    public void setDate_reported(String date_reported) {
    	this.Date_reported = date_reported;
    }

    public String getCountry_code() {
        return Country_code;
    }

    public void setCountry_code(String country_code) {
    	this.Country_code = country_code;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
    	this.Country = country;
    }

    public String getWHO_region() {
        return WHO_region;
    }

    public void setWHO_region(String WHO_region) {
        this.WHO_region = WHO_region;
    }

    public int getNew_cases() {
        return New_cases;
    }

    public void setNew_cases(int new_cases) {
    	this.New_cases = new_cases;
    }

    public int getCumulative_cases() {
        return Cumulative_cases;
    }

    public void setCumulative_cases(int cumulative_cases) {
    	this.Cumulative_cases = cumulative_cases;
    }

    public int getNew_deaths() {
        return New_deaths;
    }

    public void setNew_deaths(int new_deaths) {
    	this.New_deaths = new_deaths;
    }

    public int getCumulative_deaths() {
        return Cumulative_deaths;
    }

    public void setCumulative_deaths(int cumulative_deaths) {
    	this.Cumulative_deaths = cumulative_deaths;
    }
}

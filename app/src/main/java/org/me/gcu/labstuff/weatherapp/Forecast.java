package org.me.gcu.labstuff.weatherapp;

public class Forecast {

    private String title;
    private String description;
    private String link;
    private String windDirection;
    private String location;
    private int windSpeed;
    private int minTemp;
    private int maxTemp;
    private String conditions;
    private String day;

    //Constructors for the Forecast Objects
    public Forecast() {
        this.title ="";
        this.description="";
        this.windDirection ="";
        this.windSpeed=0;
        this.minTemp = 0;
        this.maxTemp = 0;
        this.conditions="";
        this.day="";

    }

    public Forecast(String title, String description, String windDirection, int windSpeed, int maxTemp, int minTemp, String conditions, String location, String day){
        this.title = title;
        this.description = description;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.conditions = conditions;
        this.location = location;
        this.day = day;
    }

    //Getters and Setters for the Forecast Object

    public String getTitle(){

        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getWindDirection(){
        return this.windDirection;
    }

    public void setWindDirection(String windDirection){
        this.windDirection = windDirection;
    }

    public int getWindSpeed(){
        return this.windSpeed;
    }

    public void setWindSpeed(int windSpeed){
        this.windSpeed = windSpeed;
    }

    public void setMaxTemp(int maxTemp){
        this.maxTemp=maxTemp;
    }

    public int getMaxTemp(){
        return this.maxTemp;
    }

    public int getMinTemp(){
        return this.minTemp;
    }

    public void setMinTemp(int minTemp){
        this.minTemp = minTemp;
    }

    public String getConditions(){
        return this.conditions;
    }

    public void setConditions(String conditions){
        this.conditions = conditions;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public String getLink(){

        return this.link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String toString()
    {
        String forecast;

        forecast = "Title: " + title + "\n" + "Description: "+ description + "\n" + "Wind Direction: "+ windDirection  + "\n" +"Wind Speed: "+ Integer.toString(windSpeed) +" MPH" + "\n" + "Min Temp: "+ Integer.toString(minTemp) + "\n" + "Max Temp: "+ Integer.toString(maxTemp);

        return forecast;
    }

}

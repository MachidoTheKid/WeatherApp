package org.me.gcu.labstuff.weatherapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;


public class RSSParser
{
    // Linked List containing top stories each with its own title, description and publication date
    LinkedList <Forecast> forecastList = null;

    // A LINKED LIST FOR HOLDING THE TITLES OF TOP STORIES
    // TO BE USED IN LISTVIEW
    LinkedList <String>  titleList = null;


    public LinkedList<Forecast> parseRSSString(String rssString)
    {
        Forecast forecast = new Forecast();

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( rssString ) );
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        forecastList  = new LinkedList<Forecast>();
                        titleList     = new LinkedList<String>();


                        /*eventType = xpp.next();
                        while(!xpp.getName().equalsIgnoreCase("item"))
                        {
                            eventType = xpp.next();

                        }*/

                        for(int i=0; i<28;i++){
                            eventType=xpp.next();
                        }

                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        forecast = new Forecast();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        String[] conditionGrab = temp.split(",");
                        System.out.println("MY CONDITION: "+ conditionGrab[0]);
                        forecast.setConditions(conditionGrab[0]);

                        //Problem here was that I had to find a way to ignore the content of the first
                        //"title" tag. For some reason, it wasn't skipped. And so in trying to isolate the data
                        //I kept getting an out of bounds error. This is a workaround to that error.
                        /*if((!"BBC Weather - Forecast for  London".equals(conditionGrab[0]))){


                            String elaborateConditions = conditionGrab[0];
                            String[] finalConditions = elaborateConditions.split(":");
                            String conditions = finalConditions[1];
                            forecast.setConditions(conditions);
                            String day = finalConditions[0];
                            forecast.setDay(day);
                            System.out.println("REAL CONDITIONS:"+ conditions);

                        }*/


                        //forecast.setConditions(conditions);
                        forecast.setTitle(temp);

                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            forecast.setDescription(temp);
                            String[] consolidatedInfo = temp.split(",");
                            String[] checker = consolidatedInfo[1].split(":");
                            //System.out.println("INFO IS: "+consolidatedInfo[0]);

                            if(!" Wind Direction".equals(checker[0])){

                                //WindDirection
                                String windInfo = consolidatedInfo[2];
                                String[] splitWindInfo = windInfo.split(":");
                                String windDirection = splitWindInfo[1];
                                forecast.setWindDirection(windDirection);
                                //System.out.println("DIRECTION= "+forecast.getWindDirection());

                                //WindSpeed

                                String speedInfo = consolidatedInfo[3];
                                String[] splitSpeedInfo = speedInfo.split(":");
                                String windSpeedText = splitSpeedInfo[1];
                                String[] actualSpeed = windSpeedText.split("m");
                                String[] speed = actualSpeed[0].split(" ");
                                //System.out.println("HELLO SPEED IS: "+ speed[1]);
                                int windSpeed = Integer.parseInt(speed[1]);
                                forecast.setWindSpeed(windSpeed);


                                //Min Temp
                                String minTempInfo= consolidatedInfo[1];
                                String[] minTempInfo1= minTempInfo.split(":");
                                String minTempString = minTempInfo1[1];
                                String[] minTempString1 = minTempString.split(" ");
                                String[] minTempString2 = minTempString1[1].split(" ");
                                String[] finalTemp = minTempString2[0].split("°");
                                int minTemp = Integer.parseInt(finalTemp[0]);
                                forecast.setMinTemp(minTemp);
                                System.out.println(" MIN TEMP STRING IS: "+finalTemp[0]+"°C");

                                //Max Temp
                                String maxTempInfo= consolidatedInfo[0];
                                String[] maxTempInfo1= maxTempInfo.split(":");
                                String maxTempString = maxTempInfo1[1];
                                String[] maxTempString1 = maxTempString.split(" ");
                                String[] maxTempString2 = maxTempString1[1].split(" ");
                                String[] maxFinalTemp = maxTempString2[0].split("°");
                                int maxTemp = Integer.parseInt(maxFinalTemp[0]);
                                forecast.setMaxTemp(maxTemp);
                                System.out.println("MAX TEMP STRING IS: "+finalTemp[0]+"°C");

                            }

                            else {

                                //WindDirection
                                String windInfo = consolidatedInfo[1];
                                String[] splitWindInfo = windInfo.split(":");
                                String windDirection = splitWindInfo[1];
                                forecast.setWindDirection(windDirection);
                                //System.out.println("DIRECTION= "+forecast.getWindDirection());

                                //WindSpeed

                                String speedInfo = consolidatedInfo[2];
                                String[] splitSpeedInfo = speedInfo.split(":");
                                String windSpeedText = splitSpeedInfo[1];
                                String[] actualSpeed = windSpeedText.split("m");
                                String[] speed = actualSpeed[0].split(" ");
                                //System.out.println("HELLO SPEED IS: "+ speed[1]);
                                int windSpeed = Integer.parseInt(speed[1]);
                                forecast.setWindSpeed(windSpeed);


                                //Min Temp
                                String minTempInfo= consolidatedInfo[0];
                                String[] minTempInfo1= minTempInfo.split(":");
                                String minTempString = minTempInfo1[1];
                                String[] minTempString1 = minTempString.split(" ");
                                String[] minTempString2 = minTempString1[1].split(" ");
                                String[] finalTemp = minTempString2[0].split("°");
                                int minTemp = Integer.parseInt(finalTemp[0]);
                                forecast.setMinTemp(minTemp);
                                //System.out.println(" MIN TEMP STRING IS: "+finalTemp[0]+"°C");

                                //Max Temp

                                int maxTemp = 0;
                                forecast.setMaxTemp(maxTemp);
                                //System.out.println("MAX TEMP STRING IS: "+finalTemp[0]+"°C");


                            }





                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("windDirection"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                forecast.setWindDirection(temp);
                            }
                }
                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        forecastList.add(forecast);
                        titleList.add(forecast.getTitle());
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = forecastList.size();
                    }
                }

                // Get the next event
                eventType = xpp.next();
            }

        }

        catch (XmlPullParserException e)
        {
            System.out.println("Parsing Error "+e.toString());
        }

        catch (IOException e)
        {
            System.out.println("Parsing Error "+e.toString());
        }
        return forecastList;
    }

    // THE END
}
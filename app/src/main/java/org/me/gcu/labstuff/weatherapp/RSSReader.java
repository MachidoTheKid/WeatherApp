package org.me.gcu.labstuff.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RSSReader
    {
        private String urlString = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";

        //Glasgow https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579
        //London https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743

        private String rssString = "";

        public String getRssString()
        {
            return rssString;
        }
        public String getUrlString()
        {
            return urlString;
        }

        public void setUrlString(String urlString){
            this.urlString = urlString;
        }


        public void FetchRSS()
        {
            try
            {
                URL url = new URL(urlString);
                URLConnection conn = (URLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;

                while ((inputLine = in .readLine()) != null)
                {
                    rssString += inputLine;
                }
                in .close();

            }

            catch (Exception e)
            {
                System.out.println(e.toString());
            }

        }

    }


package org.me.gcu.labstuff.weatherapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //RSS PARSER AND RSS READER NEEDED TO GET THE RSS DATA
    RSSParser rssParser = new RSSParser();
    RSSReader rssReader = new RSSReader();

    Dialog dialog;

    //TEXTVIEWS

    private TextView windDirDisplay;
    private TextView locationDisplay;
    private TextView dayDisplay;
    private TextView day2Display;
    private TextView windSPDisplay;
    private TextView humidityDisplay;
    private TextView tempDisplay;
    private TextView parsedDataDisplay;
    private TextView dayTempDisplay;
    private TextView day2TempDisplay;

    //Main Layout
    private ScrollView scrollView;

    //Image View
    private ImageView weatherImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);


        //Data Displayed Here
        dayDisplay = (TextView)findViewById(R.id.dayDisplay);
        day2Display = (TextView)findViewById(R.id.day2Display);
        dayTempDisplay =(TextView)findViewById(R.id.dayTempDisplay);
        day2TempDisplay =(TextView)findViewById(R.id.day2TempDisplay);
        locationDisplay =(TextView)findViewById(R.id.locationDisplay);
        parsedDataDisplay = (TextView)findViewById(R.id.parsedDataDisplay);
        tempDisplay = (TextView)findViewById(R.id.tempDisplay);
        windDirDisplay = (TextView)findViewById(R.id.windDirDisplay);
        windSPDisplay = (TextView)findViewById(R.id.windSPDisplay);
        humidityDisplay = (TextView)findViewById(R.id.humidityDisplay);

        //Main Layout
        scrollView = findViewById(R.id.appLayout);

        //ImageView
        weatherImage = findViewById(R.id.weatherImg);

        dialog = new Dialog(this);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_2:
                        /*selectedFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_layout, selectedFragment).commit();*/
                        openDialog();
                        // do something here
                        break;

                }

                return true;
            }
        });

        asyncDisplay();

    }

    private void openDialog(){

        dialog.setContentView(R.layout.win_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        TextView tempDesc = dialog.findViewById(R.id.textView2);
        TextView windDirection = dialog.findViewById(R.id.textView3);
        TextView visibility = dialog.findViewById(R.id.textView4);
        TextView title = dialog.findViewById(R.id.textView);
        TextView pressure = dialog.findViewById(R.id.textView5);
        TextView windSpeed = dialog.findViewById(R.id.textView6);

        tempDesc.setText("Temperature: "+rssParser.forecastList.get(0).getMinTemp()+"/"+rssParser.forecastList.get(0).getMaxTemp()+"°C");
        windDirection.setText("Wind Direction: "+rssParser.forecastList.get(0).getWindDirection());
        visibility.setText("Visibility: Good");
        pressure.setText("Pressure: 1000db");
        windSpeed.setText("Wind Speed: "+rssParser.forecastList.get(0).getWindSpeed()+"mph");
        imageViewClose.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.glasgow) {
            System.out.println("Glasgow");
            RSSReader reader = new RSSReader();
            //asyncClearResults();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
            asyncUpdate(rssUrl, reader);
            locationDisplay.setText("Glasgow");
            return true;
        }

        if (id == R.id.london) {

            System.out.println("London");
            RSSReader reader = new RSSReader();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
            //asyncClearResults();
            asyncUpdate(rssUrl, reader);
            updateConditions(rssParser.forecastList.get(0).getConditions());
            locationDisplay.setText("London");
            return true;
        }

        if (id == R.id.newYork) {
            System.out.println("New York");
            RSSReader reader = new RSSReader();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
            //asyncClearResults();
            asyncUpdate(rssUrl, reader);
            updateConditions(rssParser.forecastList.get(0).getConditions());
            locationDisplay.setText("New York");
            return true;
        }

        if (id == R.id.oman) {
            System.out.println("Oman");
            RSSReader reader = new RSSReader();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
            //asyncClearResults();
            asyncUpdate(rssUrl, reader);
            updateConditions(rssParser.forecastList.get(0).getConditions());
            locationDisplay.setText("Oman");
            return true;
        }

        if (id == R.id.mauritius) {
            System.out.println("Mauritius");
            RSSReader reader = new RSSReader();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
            //asyncClearResults();
            asyncUpdate(rssUrl, reader);
            updateConditions(rssParser.forecastList.get(0).getConditions());
            locationDisplay.setText("Mauritius");
            return true;
        }

        if (id == R.id.bangladesh) {
            System.out.println("Bangladesh");
            RSSReader reader = new RSSReader();
            String rssUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
            //asyncClearResults();
            asyncUpdate(rssUrl, reader);
            updateConditions(rssParser.forecastList.get(0).getConditions());
            locationDisplay.setText("Bangladesh");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void asyncDisplay(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {

            @Override
            public void run() {

                LinkedList<Forecast> forecastLinkedList = null;
                //rssReader.setUrlString(rssUrl);
                rssReader.FetchRSS();
                System.out.println("URL IS: "+rssReader.getUrlString());

                rssParser.parseRSSString(rssReader.getRssString());

                ///////////////////////////////////////////////////////////////////////////////////
                // Clear the TextView and EditText fields
                handler.post(new Runnable() {
                    @Override
                    public void run() {



                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        parsedDataDisplay.setText(rssParser.forecastList.get(0).getTitle());
                        windDirDisplay.setText("Wind Direction: "+rssParser.forecastList.get(0).getWindDirection());
                        windSPDisplay.setText("Wind Speed: "+rssParser.forecastList.get(0).getWindSpeed());
                        humidityDisplay.setText("Humidity: 87%");



                    }
                });
                ///////////////////////////////////////////////////////////////////////////////////
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        tempDisplay.setText(rssParser.forecastList.get(0).getMaxTemp() + "°C");
                        for(int i=0; i<rssParser.forecastList.size(); i++){

                            Forecast forecast = rssParser.forecastList.get(i);
                            //Set Day Info
                            String info = forecast.getTitle();
                            String[] dayInfo = info.split(":");
                            String day = dayInfo[0];
                            forecast.setDay(day);
                            System.out.println("Day= " + forecast.getDay());


                            //Set Weather Conditions
                            String[] conditionGrab = info.split(":");
                            String elaborateConditions = conditionGrab[1];
                            String[] separateConditions = elaborateConditions.split(",");
                            String conditions = separateConditions[0];
                            forecast.setConditions(conditions);
                            System.out.println("REAL DAY CONDITIONS:"+ conditions);
                        }

                        dayDisplay.setText(rssParser.forecastList.get(1).getDay()+" "+getNextDay("dd/MM/yyyy",1));
                        day2Display.setText(rssParser.forecastList.get(2).getDay()+" "+getNextDay("dd/MM/yyyy",2));

                        dayTempDisplay.setText("Temperature: "+rssParser.forecastList.get(1).getMinTemp()+"/"+rssParser.forecastList.get(1).getMaxTemp()+"°C");
                        day2TempDisplay.setText("Temperature: "+rssParser.forecastList.get(2).getMinTemp()+"/"+rssParser.forecastList.get(2).getMaxTemp()+"°C");
                    }
                });
                ///////////////////////////////////////////////////////////////////////////////////
            }
        });
    }

    public void asyncUpdate(String rssUrl, RSSReader rssReader){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {

            @Override
            public void run() {

                LinkedList<Forecast> forecastLinkedList = null;
                //rssReader.setUrlString(rssUrl);
                rssReader.setUrlString(rssUrl);
                rssReader.FetchRSS();
                System.out.println("URL IS: "+rssReader.getUrlString());

                rssParser.parseRSSString(rssReader.getRssString());

                ///////////////////////////////////////////////////////////////////////////////////
                // Clear the TextView and EditText fields
                handler.post(new Runnable() {
                    @Override
                    public void run() {



                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        parsedDataDisplay.setText(rssParser.forecastList.get(0).getTitle());
                        windDirDisplay.setText("Wind Direction: "+rssParser.forecastList.get(0).getWindDirection());
                        windSPDisplay.setText("Wind Speed: "+rssParser.forecastList.get(0).getWindSpeed());
                        humidityDisplay.setText("Humidity: 87%");



                    }
                });
                ///////////////////////////////////////////////////////////////////////////////////
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        tempDisplay.setText(rssParser.forecastList.get(0).getMaxTemp() + "°C");
                        for(int i=0; i<rssParser.forecastList.size(); i++){

                            Forecast forecast = rssParser.forecastList.get(i);
                            //Set Day Info
                            String info = forecast.getTitle();
                            String[] dayInfo = info.split(":");
                            String day = dayInfo[0];
                            forecast.setDay(day);
                            System.out.println("Day= " + forecast.getDay());


                            //Set Weather Conditions
                            String[] conditionGrab = info.split(":");
                            String elaborateConditions = conditionGrab[1];
                            String[] separateConditions = elaborateConditions.split(",");
                            String conditions = separateConditions[0];
                            forecast.setConditions(conditions);
                            System.out.println("REAL DAY CONDITIONS:"+ conditions);
                        }



                        dayDisplay.setText(rssParser.forecastList.get(1).getDay()+" "+getNextDay("dd/MM/yyyy",1));
                        day2Display.setText(rssParser.forecastList.get(2).getDay()+" "+getNextDay("dd/MM/yyyy",2));

                        dayTempDisplay.setText("Temperature: "+rssParser.forecastList.get(1).getMinTemp()+"/"+rssParser.forecastList.get(1).getMaxTemp()+"°C");
                        day2TempDisplay.setText("Temperature: "+rssParser.forecastList.get(2).getMinTemp()+"/"+rssParser.forecastList.get(2).getMaxTemp()+"°C");

                        updateConditions(rssParser.forecastList.get(0).getConditions());
                    }
                });
                ///////////////////////////////////////////////////////////////////////////////////
            }
        });
    }

    public static String getNextDay(String strFormat, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, amount);
        return new SimpleDateFormat(strFormat).format(cal.getTime());
    }

    public String updateConditions(String conditions){
        for(int i=0; i<rssParser.forecastList.size(); i++){

            switch(rssParser.forecastList.get(i).getConditions()) {
                case " Sunny Intervals"  :
                    System.out.println("Sunny Intervals");
                    weatherImage.setImageResource(R.drawable.day_clear);
                    break;
                case " Light Rain Showers"  :
                    System.out.println("Light Rain Showers");
                    break;
                case " Thundery Showers" :
                    weatherImage.setImageResource(R.drawable.rain_thunder);
                    System.out.println("Thundery Showers");
                case " Sunny" :
                    System.out.println("Sunny");
                    break;
                case " Light Cloud" :
                    weatherImage.setImageResource(R.drawable.cloudy);
                    System.out.println("Light Cloud");
                    break;
                default :
                    System.out.println("Weather Case Not Considered");
            }
            System.out.println("Your Condition Is " + conditions);

        }
        return conditions;
    }

    public void getSource(String uri){
        uri = "@drawable/myresource";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        //Drawable res = getDrawable(imageResource);
        //weatherImage.setImageDrawable(res);
    }

    public void asyncClearResults(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {

            @Override
            public void run() {

                rssReader = new RSSReader();

                ///////////////////////////////////////////////////////////////////////////////////
                // Clear the TextView and EditText fields
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        parsedDataDisplay.setText("");
                        windDirDisplay.setText("");
                        windSPDisplay.setText("");
                        humidityDisplay.setText("");
                        dayDisplay.setText("");
                        day2Display.setText("");
                        rssReader.setUrlString("");

                    }
                });
                ///////////////////////////////////////////////////////////////////////////////////

            }
        });
    }

}
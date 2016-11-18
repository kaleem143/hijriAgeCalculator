package com.example.kalim.hijriagecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.chrono.IslamicChronology;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {
    DateTime dtIslamic;
    InterstitialAd mInterstitialAd;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private TextView dateView,islamicDateView,islamicAgeText,currentAge;

    LocalDate birthdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });

        requestNewInterstitial();
        islamicAgeText= (TextView) findViewById(R.id.islamic_age_text);
        islamicDateView= (TextView) findViewById(R.id.islamic_date_text);
        currentAge= (TextView) findViewById(R.id.d_current_age_text);
        
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("calender", String.valueOf(year));
        Log.d("calender", String.valueOf(month));
        Log.d("calender", String.valueOf(day));
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMaxDate(new Date().getTime());












        datePicker.init(
                day,
                month,
                year,
                new DatePicker.OnDateChangedListener(){

                    @Override
                    public void onDateChanged(DatePicker view,
                                              int year, int monthOfYear,int dayOfMonth) {



                       // showDate(year, monthOfYear+1, dayOfMonth);
                        try{
                            DateTime dtISO = new DateTime(year,monthOfYear+1,dayOfMonth,0,0);
                            dtIslamic = dtISO.withChronology(IslamicChronology.getInstance());
                            birthdate = new LocalDate(year, monthOfYear+1, dayOfMonth);
                        }catch (Exception e){
                            e.printStackTrace();
                        }





                    }});

    }

    private void showDate(int year, int monthOfYear, int dayOfMonth) {
        dateView.setText(new StringBuilder().append(dayOfMonth).append("/")
                .append(monthOfYear).append(year).append("/"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void calculateIslamicDate(View view) {
        try{
            currentAge.setText("106years,1months,11days");
            islamicDateView.setText(getString(R.string.saturday)+getString(R.string.hijri_date));
            islamicAgeText.setText(R.string.hijri_age);
            int year=dtIslamic.getYear();
            int month=dtIslamic.getMonthOfYear();
            int day=dtIslamic.getDayOfMonth();
            int weekday=dtIslamic.getDayOfWeek();
            Log.d("datevalues",""+year+""+month+day);
            String dayName = null;
            switch (weekday){
                case 1:
                    dayName=getString(R.string.monday);
                    break;
                case 2:
                    dayName=getString(R.string.tuesday);
                    break;
                case 3:
                    dayName=getString(R.string.wednesday);
                    break;
                case 4:
                    dayName=getString(R.string.thursday);
                    break;
                case 5:
                    dayName=getString(R.string.friday);
                    break;
                case 6:
                    dayName=getString(R.string.saturday);
                    break;

                case 7:
                    dayName=getString(R.string.sunday);
                    break;
            }
                          //Today's date
            DateTime dtISO = new DateTime();
            DateTime dtIslamic2 = dtISO.withChronology(IslamicChronology.getInstance());
            int year1=dtIslamic2.getYear();
            int month1=dtIslamic2.getMonthOfYear();
            int day1=dtIslamic2.getDayOfMonth();


            LocalDate now = new LocalDate();
            Period period = new Period(birthdate, now, PeriodType.yearMonthDay());

            currentAge.setText(""+period.getYears()+"years,"+period.getMonths()+"months,"+period.getDays()+"days");


            Period period2 = new Period(dtIslamic, dtIslamic2, PeriodType.yearMonthDay());
            String islamicAge=""+period2.getYears()+"Hijri years,"+period2.getMonths()+"months,"+period2.getDays()+"days";
            Log.d("islamicage",islamicAge);
            islamicAgeText.setText(islamicAge);

            switch (month){
                case 1:
                    islamicDateView.setText(dayName+","+day +getString(R.string.muharram)+"("+month+"),"+year);
                    break;
                case 2:
                    islamicDateView.setText(dayName+","+day +getString(R.string.safar)+"("+month+"),"+year);
                    break;
                case 3:
                    islamicDateView.setText(dayName+","+day +getString(R.string.rabi_al_awwal)+"("+month+"),"+year);
                    break;
                case 4:
                    islamicDateView.setText(dayName+","+day +getString(R.string.rabi_al_thani)+"("+month+"),"+year);
                    break;
                case 5:
                    islamicDateView.setText(dayName+","+day +getString(R.string.jummad_al_awwal)+"("+month+"),"+year);
                    break;
                case 6:
                    islamicDateView.setText(dayName+","+day +getString(R.string.jumad_al_thani)+"("+month+"),"+year);
                    break;
                case 7:
                    islamicDateView.setText(dayName+","+day +getString(R.string.rajab)+"("+month+"),"+year);
                    break;
                case 8:
                    islamicDateView.setText(dayName+","+day +getString(R.string.shaban)+"("+month+"),"+year);
                    break;
                case 9:
                    islamicDateView.setText(dayName+","+day +getString(R.string.ramadan)+"("+month+"),"+year);
                    break;
                case 10:
                    islamicDateView.setText(dayName+","+day +getString(R.string.shawwal)+"("+month+"),"+year);
                    break;
                case 11:
                    islamicDateView.setText(dayName+","+day +getString(R.string.zul_qaadah)+"("+month+"),"+year);
                    break;
                case 12:
                    islamicDateView.setText(dayName+","+day +getString(R.string.zul_hijjah)+"("+month+"),"+year);
                    break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (mInterstitialAd.isLoaded()) {
            final Handler handler = new Handler();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                           mInterstitialAd.show();
                        }
                    });
                }
            }, 5000);

        }

    }


    }


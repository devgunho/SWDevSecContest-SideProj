package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readBarrierFreeData();
    }

    private List<BFDataSample> bfSamples= new ArrayList<>();
    private void readBarrierFreeData() {
        InputStream is = getResources().openRawResource(R.raw.seoul_barrier_free);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line="";
        try {
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity","Line : "+line);
                // Split
                String[] tokens = line.split("\",\"");

                // Read Data
                BFDataSample sample = new BFDataSample();
                //sample.setNum(tokens[0]);
                sample.setBusinessName(tokens[1]);
                sample.setTel(tokens[2]);
                sample.setFax(tokens[3]);
                sample.setAddress(tokens[4]);
                sample.setOpTime(tokens[5]);
                sample.setClosedDay(tokens[6]);
                sample.setBasicInfo(tokens[7]);
                sample.setCategory(tokens[26]);
                sample.setLatitude(tokens[28]);
                sample.setLongitude(tokens[27]);
                bfSamples.add(sample);

                Log.d("MyActivity","Just Created : "+sample);

            }
        } catch (IOException e) {
            //Log.wtf("MyActivity","Error Reading Data File on Line"+line,e);
            e.printStackTrace();
        }
    }
}

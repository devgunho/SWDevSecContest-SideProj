package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name, loc, desig;
    Button saveBtn, btnLink, getMAC, regMAC;
    Intent intent;

    private List<BFDataSample> bfSamples = new ArrayList<>();
    private String HashMac="";

    private void readBarrierFreeData() {
        InputStream is = getResources().openRawResource(R.raw.seoul_barrier_free);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line : " + line);
                // Split
                String[] tokens = line.split(",");

                // Read Data
                BFDataSample sample = new BFDataSample();
                sample.setNum(tokens[0]);
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

                Log.d("MyActivity", "Just Created : " + sample);

            }
        } catch (IOException e) {
            //Log.wtf("MyActivity","Error Reading Data File on Line"+line,e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.txtName);
        loc = (EditText) findViewById(R.id.txtLocation);
        desig = (EditText) findViewById(R.id.txtDesignation);
        saveBtn = (Button) findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminid = name.getText().toString();
                String password = loc.getText().toString();
                String hashval = desig.getText().toString();
                DBHandler dbHandler = new DBHandler(MainActivity.this);
                for (int i = 0; i < bfSamples.size(); i++) {
                    dbHandler.insertUserDetails(bfSamples.get(i).getNum(), bfSamples.get(i).getBusinessName(), bfSamples.get(i).getBasicInfo());
                }

                intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            }
        });

        btnLink = (Button) findViewById(R.id.btnLink);

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://onedrive.live.com/?id=597030471D14971B%219496&cid=597030471D14971B"));
                startActivity(linkIntent);
            }
        });

        getMAC = (Button) findViewById(R.id.getMAC);

        getMAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interfaceName = "wlan0";
                try {
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {
                        if (interfaceName != null) {
                            if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                        }
                        byte[] mac = intf.getHardwareAddress();
                        if (mac==null)
                            Log.d("mac == null", "fail!");
                        StringBuilder buf = new StringBuilder();
                        for (int idx=0; idx<mac.length; idx++)
                            buf.append(String.format("%02X:", mac[idx]));
                        if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                        Toast.makeText(getApplicationContext(), buf.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.d("fail", "fail!");
                } // for now eat exceptions
            }
        });

        regMAC = (Button) findViewById(R.id.regMAC);

        regMAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interfaceName = "wlan0";
                if(HashMac=="") {
                    try {
                        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                        for (NetworkInterface intf : interfaces) {
                            if (interfaceName != null) {
                                if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                            }
                            byte[] mac = intf.getHardwareAddress();
                            if (mac == null)
                                Log.d("mac == null", "fail!");
                            StringBuilder buf = new StringBuilder();
                            for (int idx = 0; idx < mac.length; idx++)
                                buf.append(String.format("%02X:", mac[idx]));
                            if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                            Toast.makeText(getApplicationContext(), buf.toString(), Toast.LENGTH_SHORT).show();
                            HashMac = buf.toString();
                        }
                    } catch (Exception ex) {
                        Log.d("fail", "fail!");
                    } // for now eat exceptions
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "The administrator is already registered! : " + HashMac, Toast.LENGTH_SHORT).show();
                }
            }
        });

        readBarrierFreeData();
    }
}

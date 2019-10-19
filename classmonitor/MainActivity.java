package com.rajar.classmonitor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    WifiReceiver wifiReceiver;
    ListAdapter listAdapter;
    ListView wifiList;
    List myWifiList;
    Button auto;
    int[] newDatabase;



    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiList=(ListView)findViewById(R.id.myListView);
        wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiReceiver=new WifiReceiver();


        if(!wifiManager.isWifiEnabled())
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Turning ON WiFi",Toast.LENGTH_SHORT).show();
                }
            }, 5000);
            wifiManager.setWifiEnabled(true);
        }

        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        else{
            scanWifiList();
        }
        auto=findViewById(R.id.autoscan);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            newDatabase = intent.getIntArrayExtra("database");
            for(int item=0;item<5;item++){
                Log.i("database"+item, Integer.toString(newDatabase[item]));
            }
        }
    };

    public void done(View view)
    {
        Intent intent=new Intent(this,CheckActivity.class);
        intent.putExtra("final",newDatabase);
        startActivity(intent);
    }
    private void scanWifiList() {
        wifiManager.startScan();
        myWifiList=wifiManager.getScanResults();
        setAdapter();
    }

    private void setAdapter() {
        listAdapter=new ListAdapter(getApplicationContext(),myWifiList);
        wifiList.setAdapter(listAdapter);
    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void refresh(View view)
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        Toast.makeText(getApplicationContext(),"Refreshing!",Toast.LENGTH_SHORT).show();


    }

    public void autoscan(View view) {
        Toast.makeText(getApplicationContext(), "Autoscanning!", Toast.LENGTH_SHORT).show();
        finish();
        Log.i("Log","working!");
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    auto.performClick();
                }
            }, 5000);

    }

}

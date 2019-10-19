package com.rajar.classmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity {

    int[] attendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Bundle extras=getIntent().getExtras();
        attendList=extras.getIntArray("final");

        String yes="Present";
        String no="False";

        TextView attend0=findViewById(R.id.attend0);
        TextView attend1=findViewById(R.id.attend1);
        TextView attend2=findViewById(R.id.attend2);
        TextView attend3=findViewById(R.id.attend3);
        TextView attend4=findViewById(R.id.attend4);

        if(attendList[0]!=0)
            attend0.setText(yes);
        if(attendList[1]!=0)
            attend1.setText(yes);
        if(attendList[2]!=0)
            attend2.setText(yes);
        if(attendList[3]!=0)
            attend3.setText(yes);
        if(attendList[4]!=0)
            attend4.setText(yes);

    }
}

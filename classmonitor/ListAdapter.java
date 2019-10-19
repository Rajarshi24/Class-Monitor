package com.rajar.classmonitor;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;

public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ScanResult> wifiList;
    StringBuilder Wifinames=new StringBuilder();

    String pathName="/storage/emulated/0/DCIM/Camera";
    String test;

    int z=0;



    String mac1="70:5e:55:e1:38:df";//Kushal Da's MAC address
    String mac2="74:da:da:99:25:55";


    int database[]={0,0,0,0,0};


        public void writeToFile(String data)
    {
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                Environment.DIRECTORY_DCIM + "/Camera/"
                        );
        Log.i("Path",path.toString());
        if(!path.exists())
        {
            path.mkdirs();
        }

        final File file = new File(path, "config.txt");
        try
        {

                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                BufferedWriter bw = new BufferedWriter(myOutWriter);
                bw.newLine();
                bw.write(data);
                Log.i("Test", "Hi4");
                bw.flush();
                bw.close();
                fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

   public String read(String pathName){
    BufferedReader br=null;
    String response=null;

       try {
           String fpath=pathName+"/config.txt";
           Scanner scanner = new Scanner(new File(fpath));
           while (scanner.hasNextLine()) {
               //System.out.println(scanner.nextLine());
               response=scanner.nextLine();
           }
           scanner.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
    Log.i("Response",response);
    return  response;
    }


    public ListAdapter(Context context,List<ScanResult> wifiList)
    {
        this.context=context;
        this.wifiList=wifiList;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
         return 0;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Holder holder;
        View view = convertView;

        if(view==null)
        {
            view=inflater.inflate(R.layout.list_item,null);
            holder=new Holder();
            holder.tvDetails=(TextView)view.findViewById(R.id.txtWifiName);
            view.setTag(holder);
        }
        else
        {
            holder=(Holder)view.getTag();
        }
        holder.tvDetails.setText(wifiList.get(position).SSID+"\n"+"("+wifiList.get(position).BSSID+")");
        Wifinames.append(wifiList.get(position).BSSID+"\n");
        writeToFile(Wifinames.toString());
        Log.i("Logcat",Wifinames.toString());//It gives all the available network names.
        test=read(pathName);
        Log.i("testmac",test);

        if(test.trim().equals(mac1.trim()))
        {
            database[0]=1;
        }
        if(test.trim().equals(mac2.trim()))
        {
            database[1]=2;
        }
        z++;
        if (getCount() == z) {
            Intent intent = new Intent("custom-message");
            intent.putExtra("database", database);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
        return view;
    }

     class Holder{
        TextView tvDetails;
     }
}

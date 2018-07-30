package com.example.demo_ad_sdk;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import zpSDK.zpSDK.*;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static BluetoothAdapter myBluetoothAdapter;
	public String SelectedBDAddress;
	StatusBox statusBox; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!ListBluetoothDevice())finish();
        Button Button1 = (Button) findViewById(R.id.button1);
		statusBox = new StatusBox(this,Button1);
        Button1.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View arg0) {
				Print1(SelectedBDAddress);
			}
        });
    
    }

    public boolean ListBluetoothDevice()
    {
        final List<Map<String,String>> list=new ArrayList<Map<String, String>>(); 
        ListView listView = (ListView) findViewById(R.id.listView1);
        SimpleAdapter m_adapter = new SimpleAdapter( this,list,
		   		android.R.layout.simple_list_item_2,
		   		new String[]{"DeviceName","BDAddress"},
		   		new int[]{android.R.id.text1,android.R.id.text2}
		   		);
        listView.setAdapter(m_adapter);

        if((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter())==null)
        {
     		Toast.makeText(this,"没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
     		return false;
        }

        if(!myBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);    
            startActivityForResult(enableBtIntent, 2);
        }

        Set <BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() <= 0)return false;
        for (BluetoothDevice device : pairedDevices)
        {
        	Map<String,String> map=new HashMap<String, String>();
        	map.put("DeviceName", device.getName()); 
        	map.put("BDAddress", device.getAddress());
        	list.add(map);
        }
        listView.setOnItemClickListener(new ListView.OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				SelectedBDAddress = list.get(arg2).get("BDAddress");
        		if (((ListView)arg0).getTag() != null){
        			((View)((ListView)arg0).getTag()).setBackgroundDrawable(null);
        		}
        		((ListView)arg0).setTag(arg1);
        		arg1.setBackgroundColor(Color.BLUE);
				
			}
        });
        return true;
    }
 
	
		
		public void Print1(String BDAddress) 
		{					
			zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(this);
			zpSDK.connect(BDAddress);

			Resources res=getResources();
			InputStream is=res.openRawResource(R.drawable.abc); 
			BitmapDrawable bmpDraw=new BitmapDrawable(is);
			Bitmap bmp=bmpDraw.getBitmap();
			
  
			zpSDK.pageSetup(568, 650);
	        zpSDK.drawBarCode(8, 540, "12345678901234567", 128, true, 3, 60);
	        zpSDK.drawGraphic(90, 48, 0, 0, bmp);
	        zpSDK.drawQrCode(350, 48, "111111111", 0, 3, 0);
	        zpSDK.drawText(90, 48+100, "400-8800-", 3, 0, 0, false, false);
	        zpSDK.drawText(100, 48+100+56, "株洲      张贺", 4, 0, 0, false, false);
	        zpSDK.drawText(250, 48+100+56+56, "经由  株洲", 2, 0, 0, false, false);
	        
	        zpSDK.drawText(100, 48+100+56+56+80, "2015110101079-01-01   广州", 3, 0, 0, false, false);
	        zpSDK.drawText(100, 48+100+56+56+80+80, "2015-11-01  23:00    卡班", 3, 0, 0, false, false);
	        
	        zpSDK.drawBarCode(124,48+100+56+56+80+80+80 , "12345678901234567", 128, false, 3, 60);
			zpSDK.print(0, 0);
			zpSDK.disconnect();

			


		}

	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

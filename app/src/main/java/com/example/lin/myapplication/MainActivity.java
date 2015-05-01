package com.example.lin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;
//http://www.androidhive.info/2012/01/android-json-parsing-tutorial/

public class MainActivity extends Activity implements ReadJsonAsync.OnRetriveJsonListener {
private String TAGSTR="MainTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadJsonAsync task=new ReadJsonAsync();
        task.setOnRetriveScuessListener(this);
        task.ReadJson("http://192.168.0.129:8080/StudentServlet");
    }

    @Override
    public void ReceiveScuess(String msg) {
        try {

            JSONArray array = new JSONArray(msg);
            if (array.length() > 0) {
                for(Integer i=0;i<array.length();i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Log.i(TAGSTR, "Name:" + obj.getString("name") + ":Address" + obj.getString("address"));
                }
            }
        } catch (Exception E) {

        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

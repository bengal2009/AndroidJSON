package com.example.lin.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//http://android-zhang.iteye.com/blog/1734805
/**
 * Created by blin on 2015/4/15.
 */
public class ReadJsonAsync {
    static private OnRetriveJsonListener mListener;
    private AsyncHttpTask  mTask;
    private Handler mHandler;
    static private String RDSTR;
    public interface OnRetriveJsonListener {
        void ReceiveScuess(String msg);
    }

    public void setOnRetriveScuessListener(OnRetriveJsonListener listener) {
        this.mListener = listener;
    }

    // ?�e
    public void ReadJson(String URLSTR) {
        //TODO �ݧP?��?���I?
        mTask = new AsyncHttpTask(URLSTR);
        mTask.execute();
    }

    // ����
    public void stop() {
        if (mTask != null)
            mTask.cancel(true);
    }
    public static class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        private static final String TAGSTR = "Http Connection";

        private ArrayAdapter arrayAdapter = null;
        private Context mcontext;
        private String urlstr;
        public AsyncHttpTask(String Urlpass){
            this.urlstr=Urlpass;
        }
        /*public AsyncHttpTask(Context mcontext) {
            this.mcontext = mcontext;
        }*/
        @Override
        protected Integer doInBackground(String... params) {
            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost(urlstr);
            Integer result = 0;
// Depends on your web service
            httppost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
//            String result = null;
            try {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
//                result = sb.toString();
                RDSTR=sb.toString();
                result =1;
            } catch (Exception e) {
                // Oops
                result =0;
            }
            finally {
                try{if(inputStream != null)inputStream.close();}catch(Exception squish){ result =0;}
            }
            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            Log.i(TAGSTR,"onPostExecut");

            if(result == 1){
                Log.i(TAGSTR,RDSTR);
                mListener.ReceiveScuess(RDSTR);
//                arrayAdapter = new ArrayAdapter(mcontext, android.R.layout.simple_list_item_1, blogTitles);

//                listView.setAdapter(arrayAdapter);
//                ED1.setText(blogTitles[0]);
//                ED1.setText("OK");
            }else{
                Log.e(TAGSTR, "Failed to fetch data!");
            }
        }
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";
        String result = "";
        Log.i("TAGSTR","convertInputStreamToString");
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }
}

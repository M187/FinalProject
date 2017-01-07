package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by michal.hornak on 1/7/2017.
 */
public class JokeFetcher extends AsyncTask<String, Void, String> {

    public void setCountDownLatch(CountDownLatch mCountDownLatch) {
        this.mCountDownLatch = mCountDownLatch;
    }

    private CountDownLatch mCountDownLatch;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            HttpGet httppost = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                mCountDownLatch.countDown();
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCountDownLatch.countDown();
        return "";
    }

    protected void onPostExecute(Boolean result) {
    }
}

package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.CountDownLatch;


public class MainActivity extends ActionBarActivity {

    private String responseString;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = getRequestQueue();
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

    public void tellJoke(View view) {
        volleyStringRequst("http://192.168.1.100:8080/_ah/api/myApi/v1/joke");
    }

    public void volleyStringRequst(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        //progressDialog.setMessage("Loading...");
        //progressDialog.show();

        StringRequest strReq = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volley", response.toString());
                        responseString = response;
                        //Showing response to world after fetching it.
                        jokeText = response;
                        mCountDownLatch.countDown();
                        Toast.makeText(getBaseContext(), responseString, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mCountDownLatch.countDown();
                        VolleyLog.d("Volley", "Error: " + error.getMessage());
                    }
                });
        // Adding String request to request queue
        this.addToRequestQueue(strReq, REQUEST_TAG);

        //Waiting for response;
        this.waitForResponse();
    }


    CountDownLatch mCountDownLatch = new CountDownLatch(1);
    String jokeText = "default text";

    public void waitForResponse(){

        final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainThreadHandler.post(new Runnable() {
                    public void run(){
                        Intent intent = new Intent(getBaseContext(), JokeActicity.class);
                        intent.putExtra("Joke", jokeText);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getBaseContext().getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}

package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends ActionBarActivity {

    public static final String host = "127.0.0.1";
    public static final String port = "8080";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spinner = (ProgressBar) findViewById(R.id.progressBar1);
        this.spinner.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        this.spinner.setVisibility(View.VISIBLE);
        this.getAndAwaitResponse();
    }

    public void getAndAwaitResponse() {

        final JokeFetcher mJokeFetcher = new JokeFetcher();
        final CountDownLatch mCountDownLatch = new CountDownLatch(1);
        mJokeFetcher.setCountDownLatch(mCountDownLatch);
        mJokeFetcher.execute("http://" + host + ":" + port + "/_ah/api/myApi/v1/joke");

        //This can be moved into Async Task itself...
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
                    public void run() {
                        try {
                            spinner.setVisibility(View.GONE);
                            switch ((String) getResources().getText(R.string.flavor_type)) {
                                case "paid":
                                    Intent intent = new Intent(getBaseContext(), JokeActicity.class);
                                    intent.putExtra("Joke", mJokeFetcher.get());
                                    startActivity(intent);
                                    break;
                                case "free":
                                default:
                                    Toast.makeText(getBaseContext(), mJokeFetcher.get(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e){
                            Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
}

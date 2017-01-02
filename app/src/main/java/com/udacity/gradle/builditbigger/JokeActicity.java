package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by michal.hornak on 1/2/2017.
 */
public class JokeActicity extends Activity {

    private String jokeText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_layout);
        ((TextView)findViewById(R.id.joke_text)).setText(getIntent().getExtras().getString("Joke"));
    }
}

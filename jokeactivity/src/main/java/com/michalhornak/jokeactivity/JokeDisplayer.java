package com.michalhornak.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke_displayer);

        ((TextView)findViewById(R.id.joke_text_view)).setText(getIntent().getExtras().getString("Joke", "No jokes string passed via Intetnt! Pass Joke value via Intent!"));
    }
}

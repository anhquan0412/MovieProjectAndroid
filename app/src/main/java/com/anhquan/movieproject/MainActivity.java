package com.anhquan.movieproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    //API key: 05c704e23132c50fbc4a3606ce907042
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            //ADD A FRAGMENT TO THIS ACTIVITY
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieFragment())
                    .commit();
        }
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png")
//                .placeholder(R.drawable.sample_0)
//                .into(image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

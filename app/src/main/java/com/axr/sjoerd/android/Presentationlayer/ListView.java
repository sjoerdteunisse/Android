package com.axr.sjoerd.android.Presentationlayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.axr.sjoerd.android.Applicationlayer.MenuBarHelper;
import com.axr.sjoerd.android.R;

public class ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        //getSupportActionBar().setTitle("Hi, " + "Name");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        MenuBarHelper helper = new MenuBarHelper(this);
        helper.Handle(item);

        return true;
    }

}

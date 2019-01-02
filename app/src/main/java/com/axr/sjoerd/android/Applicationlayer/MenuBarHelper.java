package com.axr.sjoerd.android.Applicationlayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.axr.sjoerd.android.Presentationlayer.LoginActivity;
import com.axr.sjoerd.android.R;

public class MenuBarHelper {

    private Context context;
    public MenuBarHelper(Context context){
        this.context = context;
    }

    public void Handle(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                preferences.edit().clear().commit();
                Intent listViewIntent = new Intent(context, LoginActivity.class);

                //Clear intent stack
                listViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(listViewIntent);
                break;
        }
    }

}


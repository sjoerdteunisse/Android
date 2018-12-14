package com.axr.sjoerd.android.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.axr.sjoerd.android.Domainlayer.RegisterAccount;
import com.axr.sjoerd.android.R;

import org.json.JSONObject;

public class ApiRequest {

    private Context context;
    private SharedPreferences preferences;
    private final String TAG = this.getClass().getSimpleName();

    public ApiRequest(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void register(RegisterAccount registeringAccount) {

        Log.i(TAG, "Register voley request");

        String body =
                "{\"email\":\"" + registeringAccount.getEmail() + "\"," +
                        "\"password\":\"" + registeringAccount.getPassword() + "\"," +
                        "\"firstname\":\"" + registeringAccount.getFirstName() + "\"," +
                        "\"lastname\":\"" + registeringAccount.getLastName() + "\"}";

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Config.URL_REGISTER,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, response.toString());

                            storeJWTToken(response);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            String name = preferences.getString(Config.TOKEN_NAME, Config.NO_TOKEN);

                            Log.i(TAG, "Register token =  " + name);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            HandleApiError(error);
                        }
                    });

            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);

        } catch (Exception e) {
            Log.i(TAG, "register failed on: " + e);
        }
    }

    public void login(String username, String password) {

        Log.i(TAG, "login");

        String body = "{\"email\":\"" + username + "\",\"password\":\"" + password + "\"}";

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Config.URL_LOGIN,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, response.toString());
                            storeJWTToken(response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            HandleApiError(error);
                        }
                    });

            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);

        } catch (Exception e) {
            Log.i(TAG, "login: " + e);
        }
    }

    private void HandleApiError(VolleyError error) {
        String body;
        String statusCode = String.valueOf(error.networkResponse.statusCode);
        if (error.networkResponse.data != null) {
            try {

                body = new String(error.networkResponse.data, "UTF-8");
                JSONObject obj = new JSONObject(body);
                String errorBody = obj.getString("errorName");
                setStatusMessage(errorBody);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setStatusMessage(String s){
        TextView txtView = (TextView) ((Activity) context).findViewById(R.id.statusMessage);
        if (txtView != null) {
            if (s.startsWith("Duplicate entry"))
                txtView.setText("Account already exists");
            else {
                txtView.setText(s);
            }
        }
    }

    private void storeJWTToken(JSONObject response) {
        try {
            JSONObject c = new JSONObject(response.toString());
            String token = c.getString("token");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Config.TOKEN_NAME, token);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.axr.sjoerd.android.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.axr.sjoerd.android.Domainlayer.RegisterAccount;

import org.json.JSONObject;

public class ApiRequest {

    public final String TAG = this.getClass().getSimpleName();
    private Context context;
    private SharedPreferences preferences;

    public ApiRequest(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void register(RegisterAccount registeringAccount) {

        Log.i(TAG, "login");

        String body = "{\"email\":\"" + registeringAccount.getEmail() + "\"," +
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

                            Log.i(TAG, "onResponse: TOKEN = " + name);
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
                Toast.makeText(context, errorBody, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

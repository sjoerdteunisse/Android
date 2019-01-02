package com.axr.sjoerd.android.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.axr.sjoerd.android.Applicationlayer.AuthorizationCallback;
import com.axr.sjoerd.android.Domainlayer.RegisterAccount;
import com.axr.sjoerd.android.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                            setStatusMessage("Account registered successfully!");
                            ((AuthorizationCallback)context).HandleResponse();
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

                            ((AuthorizationCallback)context).HandleResponse();

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

    public void me(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        final String storedToken = preferences.getString(Config.TOKEN_NAME, null);

        //ERROR ON IS EMPTY.
        if(storedToken == null)
        {
            return;
        }

        if(!storedToken.isEmpty()){

            Log.i(TAG, "me");

            try {
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Config.URL_ME,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                storeJWTToken(response);

                                ((AuthorizationCallback)context).HandleResponse();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                HandleApiError(error);
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("x-access-token", storedToken);
                        return headers;
                    }
                };

                VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);

            } catch (Exception e) {
                Log.i(TAG, "login: " + e);
            }
        }
    }

    private void HandleApiError(VolleyError error) {

        if(error == null)
            return;

        //Timout
        if (error.getClass().equals(TimeoutError.class)) {
            setStatusMessage("Server timeout occurred, try again later.");
            return;
        }

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

    private void setStatusMessage(String statusMessage){
        TextView txtView = (TextView) ((Activity) context).findViewById(R.id.statusMessage);
        if (txtView != null) {
            if (statusMessage.startsWith("Duplicate entry"))
                txtView.setText("Account already exists");
            else {
                txtView.setText(statusMessage);
            }
        }
    }

    private void storeJWTToken(JSONObject jsonObject) {
        try {
            JSONObject tokenObject = new JSONObject(jsonObject.toString());
            String token = tokenObject.getString("token");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Config.TOKEN_NAME, token);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

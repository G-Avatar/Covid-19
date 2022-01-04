package com.example.covid_19;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class CovidApi {
    private final String active_cases = "https://endcov.ph/static/dashboard/js/json/muni_cases.json";
    private final String total_active_confirmed = "https://leads4health.org/l4h/data/extract_data.php?file=11";
    private final String total_death_recovered = "https://leads4health.org/l4h/data/extract_data.php?file=12";
    private Context context;

    public CovidApi(Context context){
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(JSONObject response);
    }

    public interface VolleyResponseListener2{
        void onError(String message);
        void onResponse(String response);
    }

    public void cases(VolleyResponseListener volleyResponseListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, active_cases, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void total_active_confirmed(VolleyResponseListener2 volleyResponseListener){
        StringRequest request = new StringRequest(Request.Method.GET, total_active_confirmed, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volleyResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void total_recover_death(VolleyResponseListener2 volleyResponseListener){
        StringRequest request = new StringRequest(Request.Method.GET, total_death_recovered, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volleyResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}

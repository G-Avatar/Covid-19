package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Permission permission = new Permission();
    private CovidApi covidApi = new CovidApi(this);
    private String[][] active_cases;
    private int total_muni;
    private String[][] dashboard = new String[1][4];
    private SqliDatabase sql = new SqliDatabase(this);

    public String[][] getActive_cases() {
        return active_cases;
    }

    public int getTotal_muni() {
        return total_muni;
    }

    public void setTotal_muni(int total_muni) {
        this.total_muni = total_muni;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        permission.locationPermission(this);
        TextView view = (TextView)findViewById(R.id.test);
        getDashboard();
    }

    public void active(){
        TextView view = (TextView)findViewById(R.id.test);
        covidApi.cases(new CovidApi.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                view.setText("Updating cases failed!");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
//                    System.out.println(response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(0).getJSONObject("properties").getString("Reg_Name"));
//                    System.out.println("Total size: "+response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").length());
//                    System.out.println("Last: "+response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(1625));

                    setTotal_muni(response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").length());

                    active_cases = new String[getTotal_muni()][5];
                    for (int i = 0; i < getTotal_muni(); i++) {
                        active_cases[i][0] = response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(i).getJSONObject("properties").getString("Reg_Name");
                        active_cases[i][1] = response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(i).getJSONObject("properties").getString("Pro_Name");
                        active_cases[i][2] = response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(i).getJSONObject("properties").getString("Mun_Name");
                        active_cases[i][3] = response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(i).getJSONObject("properties").getString("Date");
                        active_cases[i][4] = response.getJSONObject("objects").getJSONObject("muni_cases").getJSONArray("geometries").getJSONObject(i).getJSONObject("properties").getString("active_muni");
                    }
                    boolean b = sql.insertData(getActive_cases());
//                    for (int i = 0; i < active_cases.length; i++) {
//                        System.out.println("["+active_cases[i][0]+","+active_cases[i][1]+","+active_cases[i][2]+","+active_cases[i][3]+","+active_cases[i][4]+"]");
//                    }
                    if (b){
                        view.setText("Done");
                        Intent next = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(next);
                        finish();
                    }else{
                        view.setText("Failed to update active cases");
                    }
                }catch (JSONException e){
                    System.out.println("error:"+e);
                }
            }
        });
    }

    public void getDashboard(){
        getConfirmActive();

    }

    public void getConfirmActive(){
        TextView view = (TextView)findViewById(R.id.test);
        view.setText("Getting Confirmed and Active Cases");
        covidApi.total_active_confirmed(new CovidApi.VolleyResponseListener2() {
            @Override
            public void onError(String message) {
                view.setText("Updating cases failed!");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray convert = new JSONArray(response);
                    dashboard[0][0] = convert.getJSONArray(convert.length()-1).getString(2);
                    dashboard[0][1] = convert.getJSONArray(convert.length()-1).getString(3);
                    view.setText("Getting Recovered and Death Cases");
                    getRecoverDeath();
//                    if (dashboard[0][0] != null && dashboard[0][1] != null && dashboard[0][2] != null && dashboard[0][3] != null){
//                        sql.updateDash(dashboard);
//                        view.setText("Done");
//                        Intent next = new Intent(MainActivity.this, DashboardActivity.class);
//                        startActivity(next);
//                        finish();
//                    }
                } catch (JSONException e) {
                    view.setText("Updating cases failed!");
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getRecoverDeath(){
        TextView view = (TextView)findViewById(R.id.test);
        covidApi.total_recover_death(new CovidApi.VolleyResponseListener2() {
            @Override
            public void onError(String message) {
                view.setText("Updating cases failed!");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray convert = new JSONArray(response);
                    dashboard[0][2] = convert.getJSONArray(convert.length()-1).getString(2);
                    dashboard[0][3] = convert.getJSONArray(convert.length()-1).getString(3);
                    if (dashboard[0][0] != null && dashboard[0][1] != null && dashboard[0][2] != null && dashboard[0][3] != null){
                        sql.updateDash(dashboard);
                        view.setText("Getting Active Cases by Municapal");
                        active();
                    }
                } catch (JSONException e) {
                    view.setText("Updating cases failed!");
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

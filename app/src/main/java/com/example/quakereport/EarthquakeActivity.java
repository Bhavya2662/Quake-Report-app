package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private ArrayList<Earthquake> earthquakes = new ArrayList<>();
    private EarthquakeAdapter adapter;
    private ListView earthquakeListView;
    private TextView textView;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

    public static boolean isNetworkConnected = false;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2&limit=20";


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        progressBar = findViewById(R.id.progressBar);
        earthquakeListView = findViewById(R.id.list);
        textView = findViewById(R.id.textView);
        isConnected(this);

        if(isNetworkConnected ){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null ,this);
        }
        else {
            progressBar.setVisibility(View.GONE);
            textView.setText("No Internet Connection!");
        }





    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private void isConnected(EarthquakeActivity earthquakeActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) earthquakeActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                isNetworkConnected =true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                isNetworkConnected = false;
            }
        });

    }

    private void parseJson(String key){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Earthquake data = gson.fromJson(REQUEST_URL, Earthquake.class);
                        earthquakeListView.setAdapter(new ArrayAdapter<Earthquake>(EarthquakeActivity.this, earthquakes));
                    }
                })
    }
}

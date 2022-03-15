package com.oscar.cargaimagenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        requestQueue = Volley.newRequestQueue(this);

        cargarImagenes(recyclerView);


    }

    private void cargarImagenes(RecyclerView recyclerView) {
        JsonRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://simplifiedcoding.net/demos/view-flipper/heroes.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Type lsAlbumType = new TypeToken<List<HeroeObj>>(){}.getType();

                        List<HeroeObj> listaImg = null;
                        try {
                            listaImg =  gson.fromJson(response.get("heroes").toString(), lsAlbumType);

                        } catch ( JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerHeroes adapter = new RecyclerHeroes(listaImg, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                },
                e -> {
                    Log.d("FatalError", e.getMessage());
                    e.printStackTrace();
                }
        );

        requestQueue.add(jsonRequest);
    }
}
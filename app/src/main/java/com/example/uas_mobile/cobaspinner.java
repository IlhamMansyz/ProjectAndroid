package com.example.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cobaspinner extends AppCompatActivity {
    private Spinner spinner;
    private EditText test;
    private ArrayAdapter<String> adapter;
    private List<String> dataBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobaspinner);
        test = (EditText) findViewById(R.id.uy);
        spinner = findViewById(R.id.spinner);
        dataBarang = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataBarang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String url = "http://192.168.115.41/uas_mobile/select_barang.php"; // Ganti dengan URL API select_barang.php

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("laptop");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("Prosessor");
                                dataBarang.add(nama);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(cobaspinner.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(cobaspinner.this);
        requestQueue.add(jsonObjectRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) parent.getItemAtPosition(position);
                Toast.makeText(cobaspinner.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();

                test.setText(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

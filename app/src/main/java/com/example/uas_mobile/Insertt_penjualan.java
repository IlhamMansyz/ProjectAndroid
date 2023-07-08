package com.example.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Insertt_penjualan extends AppCompatActivity {
    private EditText id;
    private EditText customer;
    private Spinner spinnerBarang;
    private EditText harga;
    private EditText jumlah;
    private Button submitButton;

    private JSONObject jsonObj; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonMahasiswa;
    private JSONObject jsonData;
    private JSONObject jsonObj1; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonMahasiswa1;
    private JSONObject jsonData1;
    private List<String> barangList;
    private ArrayAdapter<String> spinnerAdapter;
    String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertt_penjualan);

        id = findViewById(R.id.editTextId);
        customer = findViewById(R.id.editTextCustomer);
        spinnerBarang = findViewById(R.id.spinnerBarang);
        harga = findViewById(R.id.editTextHarga);
        jumlah = findViewById(R.id.editTextJumlah);
        submitButton = findViewById(R.id.submitButton);
        barangList = new ArrayList<>();


        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, barangList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBarang.setAdapter(spinnerAdapter);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        String url = "http://192.168.115.41/uas_mobile/select_barang.php"; // Ganti dengan URL API select_barang.php

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("laptop");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("Nama");
                                barangList.add(nama);
                            }
                            spinnerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insertt_penjualan.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Insertt_penjualan.this);
        requestQueue.add(jsonObjectRequest);

        spinnerBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (String) parent.getItemAtPosition(position);
                String url = "http://192.168.115.41/uas_mobile/select_barangharga.php"; // Ganti dengan URL API login.php
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    //instance of class JSONObj
                                    jsonObj1 = new JSONObject(response);
                                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                                    jsonMahasiswa1 = jsonObj1.getJSONArray("laptop");
                                    //hitung jumlah baris data
                                    for (int i = 0; i < jsonMahasiswa1.length(); i++)
                                    {
                                        jsonData1 = jsonMahasiswa1.getJSONObject(i);
                                        //tampung data ke dalam variabel
                                        String hrg = String.valueOf(jsonData1.getInt("Harga"));
                                        harga.setText(hrg);
                                    }

                                    //mengirim data adapter utk di tempatkan ke dalam List view menggunakan method setAdapter()
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Insertt_penjualan.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nama", selectedOption);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Insertt_penjualan.this);
                requestQueue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void submitData() {
        String url = "http://192.168.115.41/uas_mobile/insert_penjualan.php"; // Ganti dengan URL API login.php
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Insertt_penjualan.this, "Pesan: " + response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insertt_penjualan.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id.getText().toString().trim());
                params.put("customer", customer.getText().toString().trim());
                params.put("barang", selectedOption);
                params.put("harga", harga.getText().toString().trim());
                params.put("jumlah", jumlah.getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Insertt_penjualan.this);
        requestQueue.add(stringRequest);
        Intent ownerIntent = new Intent(Insertt_penjualan.this, tampildata2.class);
        startActivity(ownerIntent);
        finish();
        }
    }

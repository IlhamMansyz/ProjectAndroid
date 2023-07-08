package com.example.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tampildata2 extends AppCompatActivity {
    private Button btnTambah;
    private String URL = "http://192.168.115.41/uas_mobile/select_penjualan.php";
    private Spinner spinnerBulan;
    private ListView listViewBarang;
    private ArrayAdapter<String> listViewAdapter;


    StringRequest stringRequest;
    RequestQueue requestQueue;

    //deklarasi variable untuk Json
    private JSONObject jsonObj; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonMahasiswa;
    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampildata2);
        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ownerIntent = new Intent(tampildata2.this, Insertt_penjualan.class);
                startActivity(ownerIntent);
                finish();
            }
        });
        spinnerBulan = findViewById(R.id.spinnerBulan);
        listViewBarang = findViewById(R.id.listViewBarang);

        // Set data untuk Spinner
        List<String> bulanList = new ArrayList<>();
        bulanList.add("Pilih");
        bulanList.add("default");
        bulanList.add("1");
        bulanList.add("2");
        bulanList.add("3");
        bulanList.add("4");
        bulanList.add("5");
        bulanList.add("6");
        bulanList.add("7");
        bulanList.add("8");
        bulanList.add("9");
        bulanList.add("10");
        bulanList.add("11");
        bulanList.add("12");

        // Tambahkan nilai bulan lainnya ke dalam list

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bulanList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulan.setAdapter(spinnerAdapter);
        spinnerBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBulan = parent.getItemAtPosition(position).toString();
                Toast.makeText(tampildata2.this, "Bulan dipilih: " + selectedBulan, Toast.LENGTH_SHORT).show();
                if (selectedBulan.equals("default")){
                    listViewAdapter.clear();
                    listViewAdapter.notifyDataSetChanged();
                    listViewAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                    stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //selalu mulai dengan try...catch...
                            try {
                                //instance of class JSONObj
                                jsonObj = new JSONObject(response);
                                //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                                jsonMahasiswa = jsonObj.getJSONArray("penjualan");
                                //hitung jumlah baris data
                                for (int i = 0; i < jsonMahasiswa.length(); i++)
                                {
                                    jsonData = jsonMahasiswa.getJSONObject(i);
                                    //tampung data ke dalam variabel
                                    String nim = jsonData.getString("ID");


                                    //membuat data adapter menggunakan method add()
                                    listViewAdapter.add("NIM = " + nim);
                                }
                                //mengirim data adapter utk di tempatkan ke dalam List view menggunakan method setAdapter()
                                listViewBarang.setAdapter(listViewAdapter);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    })
                    {
                    };
                    //request ke Volley
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else if (selectedBulan.contains("1") ||
                        selectedBulan.contains("2") ||
                        selectedBulan.contains("3") ||
                        selectedBulan.contains("4") ||
                        selectedBulan.contains("5") ||
                        selectedBulan.contains("6") ||
                        selectedBulan.contains("7") ||
                        selectedBulan.contains("8") ||
                        selectedBulan.contains("9") ||
                        selectedBulan.contains("10") ||
                        selectedBulan.contains("11") ||
                        selectedBulan.contains("12")) {

                    listViewAdapter.clear();
                    listViewAdapter.notifyDataSetChanged();

                    String URL = "http://192.168.115.41/uas_mobile/select_penjualan2.php";
                    listViewAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                    stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //selalu mulai dengan try...catch...
                            try {
                                //instance of class JSONObj
                                jsonObj = new JSONObject(response);
                                //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                                jsonMahasiswa = jsonObj.getJSONArray("penjualan");
                                //hitung jumlah baris data
                                for (int i = 0; i < jsonMahasiswa.length(); i++)
                                {
                                    jsonData = jsonMahasiswa.getJSONObject(i);
                                    //tampung data ke dalam variabel
                                    String nim = jsonData.getString("ID");

                                    //membuat data adapter menggunakan method add()
                                    listViewAdapter.add("NIM = " + nim);
                                }
                                //mengirim data adapter utk di tempatkan ke dalam List view menggunakan method setAdapter()
                                listViewBarang.setAdapter(listViewAdapter);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("tanggal", selectedBulan);
                            return params;
                        }
                    };
                    //request ke Volley
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada tindakan khusus yang dilakukan jika tidak ada item yang dipilih pada Spinner
                
            }
        });


        listViewAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //selalu mulai dengan try...catch...
                try {
                    //instance of class JSONObj
                    jsonObj = new JSONObject(response);
                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                    jsonMahasiswa = jsonObj.getJSONArray("penjualan");
                    //hitung jumlah baris data
                    for (int i = 0; i < jsonMahasiswa.length(); i++)
                    {
                        jsonData = jsonMahasiswa.getJSONObject(i);
                        //tampung data ke dalam variabel
                        String nim = jsonData.getString("ID");


                        //membuat data adapter menggunakan method add()
                        listViewAdapter.add("NIM = " + nim);
                    }
                    //mengirim data adapter utk di tempatkan ke dalam List view menggunakan method setAdapter()
                    listViewBarang.setAdapter(listViewAdapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {

        };
        //request ke Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
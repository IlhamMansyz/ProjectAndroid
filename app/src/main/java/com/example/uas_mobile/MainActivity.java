package com.example.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    //deklarasi variabel
    private TextView code_txtStatus;
    private ListView code_lvMahasiswa;

    //deklarasi variabel tambahan
    //private String URL = "http://192.168.120.95/_edo/mobile2/select.php";
    private String URL = "http://192.168.115.41/uas_mobile/select_barang.php";
    //Catatan: URL di atas tolong disesuaikan dengan IP4 kalian, cek di Command Prompt, ketik ipconfig

    //Stringrequest salah satu library volley utk menangkap data
    StringRequest stringRequest;
    RequestQueue requestQueue;

    //deklarasi variable untuk Json
    private JSONObject jsonObj; //digunakan untuk proses pengambilan data JSon
    private JSONArray jsonMahasiswa;
    private JSONObject jsonData;

    //arrayadapter digunakan untuk menampung data dalam array
    private ArrayAdapter adapterMahasiswa;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    //variabel temporary
    String nim, nama; //variabel ini digunakan untuk menampung isi dari nim dan nama
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String url = "http://192.168.115.41/uas_mobile/login.php"; // Ganti dengan URL API login.php


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    //instance of class JSONObj
                                    jsonObj = new JSONObject(response);
                                    //instance of class JSONObj. Isi parameter berdasarkan dari nama array di JSON
                                    jsonMahasiswa = jsonObj.getJSONArray("karyawan");
                                    //hitung jumlah baris data
                                    for (int i = 0; i < jsonMahasiswa.length(); i++)
                                    {
                                        jsonData = jsonMahasiswa.getJSONObject(i);
                                        //tampung data ke dalam variabel
                                        nim = jsonData.getString("jabatan");


                                        //Inten tergantung jabatan
                                        if (nim.equals("Owner")){
                                            Intent ownerIntent = new Intent(MainActivity.this, OwnerActivity.class);
                                            startActivity(ownerIntent);
                                            finish();
                                        }else if (nim.equals("penjualan")){
                                            Intent ownerIntent = new Intent(MainActivity.this, tampildata2.class);
                                            startActivity(ownerIntent);
                                            finish();
                                        }




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
                                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("password", password);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);

            }

        });
    }
    }

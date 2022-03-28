package com.example.bancolombiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bancolombiaapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private String username;
    //private String image;
    //private String status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        mainBinding.btnContinuarArriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PinImgActivity.class);
                startActivity(intent);
            }
        });


        mainBinding.txtNoCliente.setPaintFlags(mainBinding.txtNoCliente.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mainBinding.etIngresarUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    mainBinding.btnContinuar.setBackgroundColor(Color.GRAY);
                    return;
                } else {
                    mainBinding.btnContinuar.setBackgroundColor(Color.YELLOW);
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    mainBinding.btnContinuar.setBackgroundColor(Color.GRAY);
                    return;
                } else {
                    mainBinding.btnContinuar.setBackgroundColor(Color.YELLOW);
                    return;
                }
            }
        });

        //login
    }

    public void userValidation(View view) {

        username = mainBinding.etIngresarUsuario.getText().toString();
        //final TextView textView = (TextView) findViewById(R.id.text);
        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.3:8080/appbancolombia/auth/pruebaLogn.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VOLLEY",response);
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String  image = jsonObject.optString("image","null");
                            String status = jsonObject.optString("status","ok");

                            if(status.equals("bad")){
                                Toast.makeText(getApplicationContext(),
                                        "Failure Login: "+status, Toast.LENGTH_SHORT).show();
                            }else if(status.equals("ok")){
                                Toast.makeText(getApplicationContext(),
                                        "Success Login: "+image, Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getApplicationContext(),PinImgActivity.class);
                                intent.putExtra("image",image);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error JSON"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Toast.makeText(getApplicationContext(), "error"+error.getMessage().toString(
                ), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("username",username);
                return data;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

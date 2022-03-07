package com.example.bancolombiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.bancolombiaapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        mainBinding.txtNoCliente.setPaintFlags(mainBinding.txtNoCliente.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        mainBinding.etIngresarUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    mainBinding.btnContinuar.setBackgroundColor(Color.GRAY);
                    return;
                }else{
                    mainBinding.btnContinuar.setBackgroundColor(Color.YELLOW);
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")){
                    mainBinding.btnContinuar.setBackgroundColor(Color.GRAY);
                    return;
                }else{
                    mainBinding.btnContinuar.setBackgroundColor(Color.YELLOW);
                    return;
                }
            }
        });
    }
    public void goToPinImg(View view){
        Intent intent = new Intent(this,PinImgActivity.class);
        startActivity(intent);
    }
}
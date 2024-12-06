package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication.Chat.ChatActivity;
import com.example.myapplication.Diseno_tarjeta.TarjetasActivity;

public class Entrar extends AppCompatActivity {

    private Button btnConocerGente, btnChatear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eleccion_entrar);

        // Inicializar los botones
        btnConocerGente = findViewById(R.id.btnConocerGente);
//        btnChatear = findViewById(R.id.btnChatear);

        // Acción para el botón "Conocer gente" (lleva a TarjetasActivity)
        btnConocerGente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entrar.this, TarjetasActivity.class);
                startActivity(intent);
            }
        });

//        Acción para el botón "Chatear" (lleva a ChatActivity)
//        btnChatear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Entrar.this, ChatActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}

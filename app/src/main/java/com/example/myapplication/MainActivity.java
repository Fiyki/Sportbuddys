package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Diseno_tarjeta.TarjetasActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primera);
        db = FirebaseFirestore.getInstance();

        // Inicializar los botones
        Button entrarButton = findViewById(R.id.Entrar);
        Button registrarseButton = findViewById(R.id.registrarse);
        // Inicializar Firebase Firestore
        // Configurar el listener para el botón de entrar
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar TarjetasActivity cuando se haga clic en "Entrar"
                Intent intent = new Intent(MainActivity.this, Entrar.class);
                startActivity(intent);
            }
        });

        // Configurar el listener para el botón de registrarse
        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de registro
                Intent intent = new Intent(MainActivity.this, Registrar.class);
                startActivity(intent);
            }
        });
    }
}

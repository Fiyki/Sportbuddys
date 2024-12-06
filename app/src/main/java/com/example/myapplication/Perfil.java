package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Diseno_tarjeta.TarjetasActivity;

public class Perfil extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para abrir la galería

    private EditText nombreEditText;
    private EditText edadEditText;
    private EditText movilEditText;
    private EditText descripcionEditText;
    private Button confirmarButton;
    private Button seleccionarImagenButton;
    private ImageView profileImageView;
    private Uri selectedImageUri;  // URI de la imagen seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        // Inicializar vistas
        nombreEditText = findViewById(R.id.nombre);
        edadEditText = findViewById(R.id.edad);
        movilEditText = findViewById(R.id.movil);
        descripcionEditText = findViewById(R.id.descripcion);
        confirmarButton = findViewById(R.id.confirmar);
        seleccionarImagenButton = findViewById(R.id.seleccionar_imagen);
        profileImageView = findViewById(R.id.profileImage);

        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            String edad = intent.getStringExtra("edad");
            String movil = intent.getStringExtra("movil");

            // Establecer los datos en los EditText correspondientes
            nombreEditText.setText(nombre);
            edadEditText.setText(edad);
            movilEditText.setText(movil);
        }

        // Configurar el listener para el botón de seleccionar imagen
        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Configurar el listener para el botón de confirmar
        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarDatos();
            }
        });
    }

    /**
     * Metodo para abrir la galería y seleccionar una imagen.
     */
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    /**
     * Este metodo se llama cuando la galería ha terminado y el usuario seleccionó una imagen.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // Obtener la URI de la imagen seleccionada
            profileImageView.setImageURI(selectedImageUri); // Establecer la imagen en el ImageView
        }
    }

    /**
     * Metodo para confirmar los datos ingresados por el usuario.
     */
    private void confirmarDatos() {
        String nombre = nombreEditText.getText().toString().trim();
        String edad = edadEditText.getText().toString().trim();
        String movil = movilEditText.getText().toString().trim();
        String descripcion = descripcionEditText.getText().toString().trim();

        // Verificar si todos los campos están completos
        if (nombre.isEmpty() || edad.isEmpty() || movil.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos confirmados", Toast.LENGTH_SHORT).show();
        }

        // Redirigir a la siguiente actividad
        Intent intent = new Intent(Perfil.this, TarjetasActivity.class);
        startActivity(intent);
    }
}

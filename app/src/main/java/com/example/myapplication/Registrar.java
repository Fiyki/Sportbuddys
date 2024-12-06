package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registrar extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nombre, contraseña, confirmarContraseña, email, edad;
    private ImageButton togglePasswordButton, togglePasswordConfirmButton;
    private Button registrarButton;
    private RadioGroup radioGroup;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        // Inicializa FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Vincula los EditText con los campos de la interfaz
        nombre = findViewById(R.id.nombre);
        contraseña = findViewById(R.id.contraseña);
        confirmarContraseña = findViewById(R.id.confirmar_contraseña);
        email = findViewById(R.id.email);
        edad = findViewById(R.id.edad);
        radioGroup = findViewById(R.id.radioGroup);
        registrarButton = findViewById(R.id.registrar);
        togglePasswordButton = findViewById(R.id.togglePassword);
        togglePasswordConfirmButton = findViewById(R.id.togglePassword2);

        // Alternar visibilidad de contraseña
        togglePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(contraseña, togglePasswordButton);
            }
        });

        togglePasswordConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(confirmarContraseña, togglePasswordConfirmButton);
            }
        });

        // Configura el listener para el botón de registrar
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    // Obtiene los valores de los campos
                    String nombreText = nombre.getText().toString();
                    String contraseñaText = contraseña.getText().toString();
                    String emailText = email.getText().toString();
                    String edadText = edad.getText().toString();
                    String generoSeleccionado = obtenerGeneroSeleccionado();

                    // Registra al usuario en Firebase
                    registrarUsuario(emailText, contraseñaText);
                }
            }
        });
    }

    private void registrarUsuario(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Si el registro es exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Registrar.this, "Registro exitoso: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        // Crea el Intent para ir a la actividad Perfil
                        Intent intent = new Intent(Registrar.this, Perfil.class);
                        intent.putExtra("nombre", nombre.getText().toString());
                        intent.putExtra("contraseña", contraseña.getText().toString());
                        intent.putExtra("email", email);
                        intent.putExtra("edad", edad.getText().toString());
                        intent.putExtra("genero", obtenerGeneroSeleccionado());

                        // Inicia la actividad Perfil
                        startActivity(intent);
                        finish();  // Finaliza esta actividad para que no se pueda volver atrás
                    } else {
                        // Si el registro falla
                        Toast.makeText(Registrar.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validarCampos() {
        // Verifica si las contraseñas coinciden y los campos están llenos
        String password = contraseña.getText().toString();
        String confirmPassword = confirmarContraseña.getText().toString();

        if (TextUtils.isEmpty(nombre.getText()) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(edad.getText())) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void togglePasswordVisibility(EditText passwordField, ImageButton toggleButton) {
        if (isPasswordVisible) {
            passwordField.setInputType(129);
            toggleButton.setImageResource(android.R.drawable.ic_menu_view);
        } else {
            passwordField.setInputType(144);
            toggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordField.setSelection(passwordField.length());
    }

    private String obtenerGeneroSeleccionado() {
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == R.id.radioHombre) {
            return "Hombre";
        } else if (id == R.id.radioMujer) {
            return "Mujer";
        }
        return "";
    }
}

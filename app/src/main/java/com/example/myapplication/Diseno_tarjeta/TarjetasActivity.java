package com.example.myapplication.Diseno_tarjeta;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class TarjetasActivity extends AppCompatActivity implements TarjetaAdapter.OnButtonClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private TarjetaAdapter tarjetaAdapter;
    private List<Tarjeta> tarjetas;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);

        // Verificar permisos de lectura y escritura de contactos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Si no tiene permisos, solicitarlos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CODE);
        }

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializar lista vacía de tarjetas
        tarjetas = new ArrayList<>();

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Configuración del adaptador
        tarjetaAdapter = new TarjetaAdapter(tarjetas, this);
        recyclerView.setAdapter(tarjetaAdapter);

        // Obtener datos de Firebase
        obtenerDatosDesdeFirebase();
    }

    private void obtenerDatosDesdeFirebase() {
        db.collection("Usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot documentSnapshots = task.getResult();
                        int[] imagenes = {
                                R.drawable.corredor,
                                R.drawable.abuelofutbol,
                                R.drawable.yogaa,
                                R.drawable.baloncestista,
                                R.drawable.ciclista,
                                R.drawable.tenistaa,
                                R.drawable.hockeya
                        };
                        int index = 0;

                        // Iterar sobre los documentos y crear tarjetas
                        for (QueryDocumentSnapshot document : documentSnapshots) {
                            String nombre = document.getString("nombre");
                            long edadLong = document.getLong("edad");
                            String descripcion = document.getString("descripcion");
                            String telefono = document.getString("telefono");

                            // Asignar imagen cíclicamente
                            int imagenResId = imagenes[index % imagenes.length];
                            tarjetas.add(new Tarjeta(nombre, (int) edadLong, descripcion, imagenResId));
                            index++;
                        }

                        tarjetaAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("TarjetasActivity", "Error al obtener los datos.", task.getException());
                        Toast.makeText(this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onLikeClick(int position) {
        Tarjeta tarjeta = tarjetas.get(position);
        String nombreUsuario = tarjeta.getNombre();

        db.collection("Usuarios")
                .whereEqualTo("nombre", nombreUsuario)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot snapshots = task.getResult();
                        if (!snapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : snapshots) {
                                String telefono = document.getString("telefono");
                                if (telefono != null && !telefono.isEmpty()) {
                                    guardarContacto(nombreUsuario, telefono);
                                } else {
                                    Toast.makeText(TarjetasActivity.this, "Número no disponible.", Toast.LENGTH_SHORT).show();
                                }
                                break; // Solo usar el primer resultado
                            }
                        } else {
                            Toast.makeText(TarjetasActivity.this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("TarjetasActivity", "Error al buscar teléfono.", task.getException());
                        Toast.makeText(this, "Error al buscar teléfono.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Eliminar la tarjeta después del like
        tarjetas.remove(position);
        tarjetaAdapter.notifyItemRemoved(position);
    }

    private void guardarContacto(String nombre, String telefono) {
        // Verificar si el contacto ya existe antes de agregarlo
        if (contactoYaExiste(telefono)) {
            Toast.makeText(this, "El contacto ya está guardado.", Toast.LENGTH_SHORT).show();
            return; // Salir del metodo si el contacto ya existe
        }

        ContentValues values = new ContentValues();

        // Usa "" (cadena vacía) en lugar de null
        values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "");
        values.put(ContactsContract.RawContacts.ACCOUNT_NAME, "");

        // Concatenar "sportbuddys" al nombre del contacto
        String nombreConSportbuddys = nombre + " sportbuddys";

        // Inserta el contacto en RawContacts
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);

        // Inserta el nombre del contacto
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nombreConSportbuddys);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        // Inserta el número de teléfono
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, telefono);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        Toast.makeText(this, "Contacto guardado correctamente.", Toast.LENGTH_SHORT).show();
    }

    // Metodo para verificar si el contacto ya existe en los contactos
    private boolean contactoYaExiste(String telefono) {
        // Buscar si ya existe un contacto con ese número
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",
                new String[]{telefono},
                null);

        // Si hay resultados, el contacto ya existe
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // El contacto ya existe
        }

        // No hay resultados, el contacto no existe
        if (cursor != null) {
            cursor.close();
        }

        return false;
    }




    @Override
    public void onDislikeClick(int position) {
        // Mostrar mensaje para el dislike
        Toast.makeText(this, "Dislike a: " + tarjetas.get(position).getNombre(), Toast.LENGTH_SHORT).show();

        // Eliminar la tarjeta actual
        tarjetas.remove(position);
        tarjetaAdapter.notifyItemRemoved(position);
    }

    // Manejar la respuesta de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el permiso es concedido, puedes proceder con las operaciones que necesitan acceso a los contactos
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            } else {
                // Si el permiso es denegado, muestra un mensaje informativo
                Toast.makeText(this, "Permisos denegados. No puedes guardar contactos.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

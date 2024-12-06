//package com.example.myapplication.Chat;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.R;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChatActivity extends AppCompatActivity {
//    private EditText editTextMensaje;
//    private Button buttonEnviar;
//    private RecyclerView recyclerView;
//    private AdaptadorMensaje adaptadorMensaje;
//    private List<Mensaje> listaMensajes;
//
//    // Inicializamos Firebase Realtime Database
//    private FirebaseDatabase database;
//    private DatabaseReference chatroomRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        // Inicializamos Firebase
//        database = FirebaseDatabase.getInstance();
//        chatroomRef = database.getReference("chatrooms").child("user1_user2").child("messages");
//
//        // Inicialización de vistas
//        editTextMensaje = findViewById(R.id.editTextMensaje);
//        buttonEnviar = findViewById(R.id.buttonEnviar);
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        listaMensajes = new ArrayList<>();
//        adaptadorMensaje = new AdaptadorMensaje(listaMensajes);
//        recyclerView.setAdapter(adaptadorMensaje);
//
//        // Acción al hacer clic en el botón de enviar mensaje
//        buttonEnviar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String texto = editTextMensaje.getText().toString();
//                if (!texto.isEmpty()) {
//                    enviarMensaje(texto);
//                } else {
//                    Toast.makeText(ChatActivity.this, "Por favor ingresa un mensaje", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Obtener mensajes desde Firebase
//        obtenerMensajes();
//    }
//
//    // Enviar mensaje a Firebase Realtime Database
//    private void enviarMensaje(String texto) {
//        String userId = "user1";  // Obtener el userId desde FirebaseAuth si es necesario
//        long timestamp = System.currentTimeMillis();
//
//        Mensaje nuevoMensaje = new Mensaje(userId, texto, timestamp);
//
//        // Crear una referencia única para el mensaje
//        String mensajeId = chatroomRef.push().getKey();
//
//        if (mensajeId != null) {
//            chatroomRef.child(mensajeId).setValue(nuevoMensaje);
//            editTextMensaje.setText("");  // Limpiar el campo de texto después de enviar
//        }
//    }
//
//    // Obtener los mensajes desde Firebase
//    private void obtenerMensajes() {
//        chatroomRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listaMensajes.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Mensaje mensaje = snapshot.getValue(Mensaje.class);
//                    listaMensajes.add(mensaje);
//                }
//                adaptadorMensaje.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(ChatActivity.this, "Error al obtener mensajes", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

package com.example.myapplication.Diseno_tarjeta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    private List<Tarjeta> tarjetas;
    private OnButtonClickListener listener;

    // Constructor modificado para aceptar el listener
    public TarjetaAdapter(List<Tarjeta> tarjetas, OnButtonClickListener listener) {
        this.tarjetas = tarjetas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TarjetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de cada tarjeta (tarjeta.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        return new TarjetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarjetaViewHolder holder, int position) {
        // Obtener la tarjeta en la posición actual
        Tarjeta tarjeta = tarjetas.get(position);

        // Asignar los valores de la tarjeta a los componentes de la vista
        holder.nombre.setText(tarjeta.getNombre());
        holder.edad.setText(String.valueOf(tarjeta.getEdad()));
        holder.descripcion.setText(tarjeta.getDescripcion());
        holder.imagen.setImageResource(tarjeta.getImagenResId());

        // Lógica para manejar los clics en los botones
        holder.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLikeClick(position); // Llamar al listener para manejar el like
                }
            }
        });

        holder.buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDislikeClick(position); // Llamar al listener para manejar el dislike
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    // Vista de cada tarjeta
    static class TarjetaViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, edad, descripcion;
        ImageView imagen;
        ImageButton buttonLike, buttonDislike;  // Botones de la tarjeta

        public TarjetaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Referencias a los elementos de la tarjeta
            nombre = itemView.findViewById(R.id.textName);
            edad = itemView.findViewById(R.id.textAge);
            descripcion = itemView.findViewById(R.id.textDescription);
            imagen = itemView.findViewById(R.id.imageProfile);
            buttonLike = itemView.findViewById(R.id.buttonLike);  // Botón Like
            buttonDislike = itemView.findViewById(R.id.buttonDislike);  // Botón Dislike
        }
    }

    // Metodo para avanzar a la siguiente tarjeta
    private void avanzarSiguienteTarjeta() {
        if (tarjetas.size() > 0) {
            tarjetas.remove(0); // Eliminar la tarjeta actual (esto solo elimina la primera de la lista)
            notifyDataSetChanged(); // Actualizar el RecyclerView
        }
    }

    // Interface para manejar los clics en los botones
    public interface OnButtonClickListener {
        void onLikeClick(int position);  // Metodo para manejar el "Like"
        void onDislikeClick(int position);  // Metodo para manejar el "Dislike"
    }
}
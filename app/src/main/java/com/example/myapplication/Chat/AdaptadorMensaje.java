//package com.example.myapplication.Chat;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.R;
//
//import java.util.List;
//
//public class AdaptadorMensaje extends RecyclerView.Adapter<AdaptadorMensaje.MensajeViewHolder> {
//    private List<Mensaje> listaMensajes;
//
//    public AdaptadorMensaje(List<Mensaje> listaMensajes) {
//        this.listaMensajes = listaMensajes;
//    }
//
//    @Override
//    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
//        return new MensajeViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(MensajeViewHolder holder, int position) {
//        Mensaje mensaje = listaMensajes.get(position);
//        holder.textoMensaje.setText(mensaje.getTexto());
//    }
//
//    @Override
//    public int getItemCount() {
//        return listaMensajes.size();
//    }
//
//    public static class MensajeViewHolder extends RecyclerView.ViewHolder {
//        public TextView textoMensaje;
//
//        public MensajeViewHolder(View itemView) {
//            super(itemView);
//            textoMensaje = itemView.findViewById(R.id.textoMensaje);
//        }
//    }
//}

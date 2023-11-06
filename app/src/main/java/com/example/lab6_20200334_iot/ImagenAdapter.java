package com.example.lab6_20200334_iot;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder>{
    private ArrayList<Uri> listaImagenes;
    private Context context;

    private static final int MAX_IMAGES = 15;


    public ImagenAdapter(Context context) {
        this.context = context;
        listaImagenes = new ArrayList<>();
    }

    public void addImage(Uri imageUri) {
        //listaImagenes.add(imageUri);
        //notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado

        if (listaImagenes.size() < MAX_IMAGES) {
            listaImagenes.add(imageUri);
            notifyItemInserted(listaImagenes.size() - 1);
        } else {
            // Muestra un mensaje o realiza alguna acción para informar al usuario que se alcanzó el límite.
            Toast.makeText(context, "Se ha alcanzado el límite de imágenes (15)", Toast.LENGTH_SHORT).show();
        }
       }


    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.irv_images, parent, false);
        return new ImagenViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        Uri imageUri = listaImagenes.get(position);
        // Carga y muestra la imagen en ImageView en el ViewHolder
        Glide.with(context)
                .load(imageUri)
                .into(holder.imageView);

        ImageView close = holder.itemView.findViewById(R.id.close);
        close.setImageResource(R.drawable.baseline_close_24);

        // Configura un click listener en el botón de eliminación
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Elimina la imagen cuando se hace clic en el botón de eliminación
                removeImage(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaImagenes.size();

    }



    public class ImagenViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        ImageView close;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewImagen);
            close = itemView.findViewById(R.id.close);
        }
    }

    public void removeImage(int position) {
        listaImagenes.remove(position);
        notifyItemRemoved(position);
    }


}

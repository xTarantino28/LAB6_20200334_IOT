package com.example.lab6_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.lab6_20200334_iot.databinding.ActivityMainBinding;
import com.example.lab6_20200334_iot.databinding.ActivityMemoryClassicBinding;

public class MemoryClassicActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ActivityMemoryClassicBinding binding;
    private RecyclerView recyclerView;
    private ImagenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoryClassicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.rvImagenes;
        adapter = new ImagenAdapter(this);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);


        binding.buttonAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            // Aquí puedes obtener la imagen y agregarla a tu RecyclerView
            adapter.addImage(imageUri);
        }
    }

}
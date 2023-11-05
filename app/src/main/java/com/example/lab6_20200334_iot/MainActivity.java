package com.example.lab6_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.example.lab6_20200334_iot.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    public static int LEVEL=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LEVEL = 3;
        this.LEVEL = chooseRandomSize();
        binding.puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                startActivity(intent);
            }
        });

        binding.memoryclassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoryClassicActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.LEVEL = chooseRandomSize();
    }

    public static int chooseRandomSize() {
        // Crear una instancia de la clase Random
        Random random = new Random();

        // Crear un array con los números 3, 4 y 5
        int[] numbers = {3, 4, 5};

        // Elegir aleatoriamente un índice del array
        int randomIndex = random.nextInt(numbers.length);

        // Devolver el número correspondiente al índice elegido
        return numbers[randomIndex];
    }
    /*public void puzzle8(View view){
        this.LEVEL = 3;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void puzzle15(View view){
        this.LEVEL = 4;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void puzzle24(View view){
        this.LEVEL = 5;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void quit(View view){
        System.exit(0);
    }*/
}
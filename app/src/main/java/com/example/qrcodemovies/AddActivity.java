package com.example.qrcodemovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findViewById(R.id.addButton).setOnClickListener(view -> {
            String director = ((EditText)findViewById(R.id.director)).getText().toString();
            int time = Integer.parseInt(((EditText)findViewById(R.id.time)).getText().toString());
            int year = Integer.parseInt(((EditText)findViewById(R.id.year)).getText().toString());
            int rating = Integer.parseInt(((EditText)findViewById(R.id.rating)).getText().toString());
            String category = ((EditText)findViewById(R.id.category)).getText().toString();
            Movie movie = new Movie(-1, time, year, rating, category, director);
            ListActivity.movieService.crate(movie).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(AddActivity.this, ListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, "Sikertelen hozzáadás", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable throwable) {
                    Toast.makeText(AddActivity.this, "Sikertelen hozzáadás", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
package com.example.qrcodemovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private List<Movie> items;
    public static MovieService movieService;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getStringExtra("url") != null || movieService != null) {
            String url = getIntent().getStringExtra("url");
            MovieClient.setBaseUrl(url);
        }
        movieService = MovieClient.getInstance().create(MovieService.class);

        findViewById(R.id.addButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });

        listView = findViewById(R.id.list);
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            new AlertDialog.Builder(ListActivity.this)
                    .setTitle("Törlés")
                    .setMessage("Biztosan törli az elemet?")
                    .setPositiveButton("Igen", (dialog, which) -> {
                        movieService.delete(items.get(i).getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    items.remove(i);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(ListActivity.this, "Sikeres törlés", Toast.LENGTH_SHORT).show();
                                } else Toast.makeText(ListActivity.this, "Sikertelen törlés", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(ListActivity.this, "Sikertelen törlés", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).show();
            return false;
        });

        movieService.getAll().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    items = response.body().stream().filter(e -> e.getRating() > 3).collect(Collectors.toList());
                    adapter = new ListAdapter(items, ListActivity.this);
                    listView.setAdapter(adapter);
                } else {
                    new AlertDialog.Builder(ListActivity.this)
                            .setTitle("Hiba!")
                            .setMessage("Nem sikerült lekérni az adatokat.")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                new AlertDialog.Builder(ListActivity.this)
                        .setTitle("Hiba!")
                        .setMessage(throwable.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
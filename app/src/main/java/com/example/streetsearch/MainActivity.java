package com.example.streetsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.streetsearch.adapter.SearchAdapter;
import com.example.streetsearch.model.NominatimResult;
import com.example.streetsearch.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edtQuery;
    Button btnSearch;
    ProgressBar progress;
    RecyclerView rvResults;
    SearchAdapter adapter;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        edtQuery = findViewById(R.id.edtQuery);
        btnSearch = findViewById(R.id.btnSearch);
        progress = findViewById(R.id.progress);
        rvResults = findViewById(R.id.rvResults);

        adapter = new SearchAdapter(item -> {
            // abrir detalhes
            Intent it = new Intent(MainActivity.this, DetailsActivity.class);
            it.putExtra("display_name", item.getDisplay_name());
            it.putExtra("lat", item.getLat());
            it.putExtra("lon", item.getLon());
            // também passe o mapa address se quiser (serializar)
            startActivity(it);
        });

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> doSearch());
        edtQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) { doSearch(); return true; }
            return false;
        });
    }

    private void doSearch() {
        String q = edtQuery.getText().toString().trim();
        if (q.length() < 2) {
            Toast.makeText(this, "Digite pelo menos 2 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setVisibility(ProgressBar.VISIBLE);
        Call<List<NominatimResult>> call = RetrofitClient.getNominatimService()
                .search(q, "json", 1, 15);

        call.enqueue(new Callback<List<NominatimResult>>() {
            @Override
            public void onResponse(Call<List<NominatimResult>> call, Response<List<NominatimResult>> response) {
                progress.setVisibility(ProgressBar.GONE);
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MainActivity.this, "Erro na busca", Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.setItems(response.body());
            }

            @Override
            public void onFailure(Call<List<NominatimResult>> call, Throwable t) {
                progress.setVisibility(ProgressBar.GONE);
                Toast.makeText(MainActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.streetsearch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageView ivMap;
    TextView tvName, tvCoords;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_details);

        ivMap = findViewById(R.id.ivMap);
        tvName = findViewById(R.id.tvName);
        tvCoords = findViewById(R.id.tvCoords);

        String display = getIntent().getStringExtra("display_name");
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");

        tvName.setText(display);
        tvCoords.setText("Lat: " + lat + "  Lon: " + lon);

        // monta URL do static map OSM (marca o ponto)
        // exemplo: https://staticmap.openstreetmap.de/staticmap.php?center=LAT,LON&zoom=14&size=865x512&markers=LAT,LON,red-pushpin
        String mapUrl = "https://staticmap.openstreetmap.de/staticmap.php?center="
                + lat + "," + lon + "&zoom=14&size=800x400&markers=" + lat + "," + lon + ",red-pushpin";

        Picasso.get().load(mapUrl).into(ivMap);
    }
}

package kr.ac.kopo.myhometime;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Button timetableButton = findViewById(R.id.timetableButton);
        Button mealButton = findViewById(R.id.mealButton);
        Button lottoButton = findViewById(R.id.lottoButton);

        timetableButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TimetableActivity.class);
            startActivity(intent);
        });

        mealButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MealActivity.class);
            startActivity(intent);
        });

        lottoButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LottoActivity.class);
            startActivity(intent);
        });
    }
}
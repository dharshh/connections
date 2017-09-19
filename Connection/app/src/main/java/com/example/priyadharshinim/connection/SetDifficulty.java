package com.example.priyadharshinim.connection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SetDifficulty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_difficulty);

        findViewById(R.id.easy_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent i = new Intent(SetDifficulty.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("difficulty", "easy");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.medium_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent i = new Intent(SetDifficulty.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("difficulty", "medium");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.complex_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent i = new Intent(SetDifficulty.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("difficulty", "difficult");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}

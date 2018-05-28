package com.chrissetiana.querypage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchText = findViewById(R.id.search_box);
        TextView searchURL = findViewById(R.id.search_url);
        TextView searchResults = findViewById(R.id.search_results);
    }
}

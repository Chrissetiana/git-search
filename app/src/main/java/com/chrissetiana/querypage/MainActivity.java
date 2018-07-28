package com.chrissetiana.querypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    private TextView searchURL;
    private TextView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.search_query);
        searchURL = findViewById(R.id.search_url);
        searchResults = findViewById(R.id.search_results);
    }

    void searchQuery() {
        String githubQuery = searchText.getText().toString();
        URL githubUrl = NetworkUtils.buildUrl(githubQuery);
        searchURL.setText(githubUrl.toString());
        String githubResults;
        try {
            githubResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);
            searchResults.setText(githubResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_search) {
            Context context = MainActivity.this;
            String message = "Search clicked";
            searchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

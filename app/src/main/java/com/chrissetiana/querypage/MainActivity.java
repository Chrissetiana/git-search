package com.chrissetiana.querypage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    private TextView searchURL;
    private TextView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.search_box);
        searchURL = findViewById(R.id.search_url);
        searchResults = findViewById(R.id.search_results);
    }

    void makeGithubearchQuery() {
        String githubQuery = searchText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        searchURL.setText(githubSearchUrl.toString());
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
            makeGithubearchQuery();
        }
        return super.onOptionsItemSelected(item);
    }
}

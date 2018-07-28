package com.chrissetiana.querypage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText textQuery;
    private TextView textUrl;
    private TextView textResults;
    private TextView textEmpty;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuery = findViewById(R.id.search_query);
        textUrl = findViewById(R.id.search_url);
        textResults = findViewById(R.id.search_results);
        textEmpty = findViewById(R.id.search_err);
        progressBar = findViewById(R.id.progress_bar);
    }

    void searchQuery() {
        String githubQuery = textQuery.getText().toString();
        URL githubUrl = NetworkUtils.buildUrl(githubQuery);
        textUrl.setText(githubUrl.toString());
        new GithubQueryTask().execute(githubUrl);
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
            searchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showJsonData() {
        textEmpty.setVisibility(View.INVISIBLE);
        textResults.setVisibility(View.VISIBLE);
    }

    private void showErrorDisplay() {
        textEmpty.setVisibility(View.VISIBLE);
        textResults.setVisibility(View.INVISIBLE);
    }

    private class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubResults = null;
            try {
                githubResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                textResults.setText(githubResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubResults;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")) {
                textResults.setText(s);
            } else {
                showErrorDisplay();
            }
        }
    }
}

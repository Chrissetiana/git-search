package com.chrissetiana.gitsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<String> {

    private static final String SEARCH_URL = "query";
    private static final int LOADER_ID = 1;
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

        if (savedInstanceState != null) {
            String url = savedInstanceState.getString(SEARCH_URL);
            textUrl.setText(url);
        }

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String url = textUrl.getText().toString().trim();
        outState.putString(SEARCH_URL, url);
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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                String urlString = args.getString(SEARCH_URL);

                if (urlString == null || TextUtils.isEmpty(urlString)) {
                    return null;
                }

                try {
                    URL url = new URL(urlString);
                    return NetworkUtils.buildHttp(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data == null) {
            showErrorDisplay();
        } else {
            textResults.setText(data);
            showJsonData();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private void searchQuery() {
        String githubQuery = textQuery.getText().toString().trim();

        if (TextUtils.isEmpty(githubQuery)) {
            textUrl.setText(R.string.no_query);
            return;
        }

        URL githubUrl = NetworkUtils.buildUrl(githubQuery);
        textUrl.setText(githubUrl.toString());

        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_URL, githubUrl.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubLoader = loaderManager.getLoader(LOADER_ID);
        if (githubLoader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }
    }

    private void showJsonData() {
        textEmpty.setVisibility(View.INVISIBLE);
        textResults.setVisibility(View.VISIBLE);
    }

    private void showErrorDisplay() {
        textEmpty.setVisibility(View.VISIBLE);
        textResults.setVisibility(View.INVISIBLE);
    }
}

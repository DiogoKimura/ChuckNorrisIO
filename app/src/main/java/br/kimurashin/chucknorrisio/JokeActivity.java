package br.kimurashin.chucknorrisio;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import br.kimurashin.chucknorrisio.datasource.JokeRemoteDataSource;
import br.kimurashin.chucknorrisio.model.Joke;
import br.kimurashin.chucknorrisio.presentation.JokePresenter;

public class JokeActivity extends AppCompatActivity {

    static final String CATEGORY_KEY = "CATEGORY_KEY";
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String category = getIntent().getExtras().getString(CATEGORY_KEY);
        Log.i("TESTE", category);

        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        JokeRemoteDataSource dataSource = new JokeRemoteDataSource();
        JokePresenter presenter = new JokePresenter(this, dataSource);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            presenter.requestAll(category);
        });
        presenter.requestAll(category);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    public void showProgressBar() {
        if (progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.loading));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
        }
        progress.show();
    }

    public void hideProgressBar() {
        if (progress != null){
            progress.hide();
        }
    }

    public void showFailure(String message) {
        Toast.makeText(this, message    , Toast.LENGTH_SHORT).show();
    }

    public void showJoke(Joke joke){
        TextView txtView = findViewById(R.id.txt_joke);
        txtView.setText(joke.getJokeMessage());
        ImageView iv = findViewById(R.id.img_icon);
        Picasso.get().load(joke.getJokeImage()).into(iv);
    }


}
package br.kimurashin.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import br.kimurashin.chucknorrisio.model.Joke;
import br.kimurashin.chucknorrisio.presentation.JokePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRemoteDataSource {

    public interface ListCategoriesCallback {
        void onSuccess (Joke joke);

        void onError (String message);

        void onComplete ();
    }

    public void findAll(String category, JokeRemoteDataSource.ListCategoriesCallback callback){
        HttpClient.retrofit().create(ChuckNorrisAPI.class)
                .findRandomBy(category)
                .enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        if (response.isSuccessful())callback.onSuccess(response.body());
                        callback.onComplete();
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        callback.onError(t.getMessage());
                        callback.onComplete();
                    }
                });
        //new JokeRemoteDataSource.JokeTask(category, callback).execute();
    }

    private static class JokeTask extends AsyncTask<Void, Void, Joke> {
        private final JokeRemoteDataSource.ListCategoriesCallback callback;
        private String errorMessage;
        private String category;

        private JokeTask(String category, JokeRemoteDataSource.ListCategoriesCallback callback) {
            this.callback = callback;
            this.category = category;
        }

        // Executado na async task
        @Override
        protected Joke doInBackground(Void... voids) {
            String icon = "";
            String value = "";

            HttpsURLConnection urlConnection = null;
            try {
                URL url = new URL(EndPoint.GET_JOKE + category);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setReadTimeout(2000);
                urlConnection.setConnectTimeout(2000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode > 400) {
                    throw new IOException("Erro na comunicação do servidor");
                }
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                JsonReader jsonReader = new JsonReader(new InputStreamReader(in));
                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("icon_url")) {
                        icon = jsonReader.nextString();
                    } else if (key.equals("value")) {
                        value = jsonReader.nextString();
                    } else {
                        jsonReader.skipValue();
                    }

                }
                jsonReader.endObject();


            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }

            return new Joke(value, icon);
        }

        // Executado na main thread
        @Override
        protected void onPostExecute(Joke joke) {
            if (errorMessage != null){
                Log.i("TESTE", errorMessage);
                callback.onError(errorMessage);
            }else {
                callback.onSuccess(joke);
            }
            callback.onComplete();
        }
    }
}

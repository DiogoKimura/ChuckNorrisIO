package br.kimurashin.chucknorrisio.presentation;

import br.kimurashin.chucknorrisio.JokeActivity;
import br.kimurashin.chucknorrisio.datasource.JokeRemoteDataSource;
import br.kimurashin.chucknorrisio.model.Joke;

public class JokePresenter implements JokeRemoteDataSource.ListCategoriesCallback{
    private final JokeActivity view;
    private final JokeRemoteDataSource dataSource;

    public JokePresenter(JokeActivity jokeActivity, JokeRemoteDataSource dataSource) {
        this.dataSource = dataSource;
        this.view = jokeActivity;
    }

    public void requestAll(String category){
        // chamar um servidor HTTP ???
        this.view.showProgressBar();
        this.dataSource.findAll(category, this);
    }

    @Override
    public void onSuccess(Joke joke){
        view.showJoke(joke);
    }
    @Override
    public void onError(String message){
        this.view.showFailure(message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }


}

package br.kimurashin.chucknorrisio.presentation;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import br.kimurashin.chucknorrisio.Colors;
import br.kimurashin.chucknorrisio.MainActivity;
import br.kimurashin.chucknorrisio.datasource.CategoryRemoteDataSource;
import br.kimurashin.chucknorrisio.model.CategoryItem;

public class CategoryPresenter implements CategoryRemoteDataSource.ListCategoriesCallback{
    private final MainActivity view;
    private final CategoryRemoteDataSource dataSource;

    public CategoryPresenter(MainActivity mainActivity, CategoryRemoteDataSource dataSource) {
        this.dataSource = dataSource;
        this.view = mainActivity;
    }

    public void requestAll(){
        // chamar um servidor HTTP ???
        this.view.showProgressBar();
        this.dataSource.findAll(this);
    }

    @Override
    public void onSuccess(List<String> response){
        List<CategoryItem> categoryItems = new ArrayList<>();
        for (String val : response){
            categoryItems.add(new CategoryItem(val, Colors.randomColor()));
        }
        view.showCategories(categoryItems);
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

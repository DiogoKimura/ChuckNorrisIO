package br.kimurashin.chucknorrisio.model;

import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("value")
    private final String jokeMessage;
    @SerializedName("icon_url")
    private final String jokeImage;

    public Joke(String jokeMessage, String jokeImage){
        this.jokeMessage = jokeMessage;
        this.jokeImage = jokeImage;
    }

    public String getJokeMessage() {
        return jokeMessage;
    }

    public String getJokeImage() {
        return jokeImage;
    }

}

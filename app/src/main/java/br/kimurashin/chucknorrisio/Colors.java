package br.kimurashin.chucknorrisio;

import android.graphics.Color;

import java.util.Random;

public class Colors {

    public static int randomColor(){
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.argb(0x80, r, g, b);
    }
}

package com.word.braingame;

import android.app.Application;
import android.graphics.Typeface;

import java.util.List;


public class Configs extends Application {



    // SET THE MAXIMUM TIME OF A ROUND
    public static float roundTime = 15;



    // SET THE PROGRESS THAT WILL BE TAKEN OFF FROM THE CURRENT PROGRESS EACH TIME YOU GUESS A WORD (NOTE: it must never be equal or more than 100, otherwise it'll reset the progressBar)
    public static int bonusProgress = 10;



    // Set fonts
    public static Typeface juneGull;



    // Array of circle shapes from the drawable folder
    public static  int[] circlesArray = new int[] {
            R.drawable.circle_corner_orange,
            R.drawable.circle_corner_blue,
            R.drawable.circle_corner_dark_purple,
            R.drawable.circle_corner_green,
            R.drawable.circle_corner_purple,
    };



    @Override
    public void onCreate() {
        super.onCreate();

        // Init custom font
        // (the font files are into "app/src/main/assets/font" folder)
        juneGull  = Typeface.createFromAsset(getAssets(),"font/junegull.ttf");


    }// end onCreate()





    /*********** DO NOT EDIT THE CODE BELOW! *************/
    public static int bestScore;
    public static int score;
    public static List<String> stringsArray;



}// @end

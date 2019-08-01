package com.word.braingame;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class GameOver extends AppCompatActivity {

    /* Views */
    TextView possibleWordTxt, scoreTxt, wordsCountTxt;


    /* Variables */
    MarshMallowPermission mmp = new MarshMallowPermission(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // Init views
        possibleWordTxt = (TextView)findViewById(R.id.goPossibleWordTxt);
        possibleWordTxt.setTypeface(Configs.juneGull);
        scoreTxt = (TextView)findViewById(R.id.goScoreTxt);
        scoreTxt.setTypeface(Configs.juneGull);
        wordsCountTxt = (TextView)findViewById(R.id.goWordsCountTxt);


        // Show score
        scoreTxt.setText(String.valueOf(Configs.score));


        // Get the word you could have made
        possibleWordTxt.setText(Configs.stringsArray.get(0));

        // Get the other words you could have made
        if (Configs.stringsArray.size() > 1) {
            wordsCountTxt.setText( String.valueOf(Configs.stringsArray.size()-1) );
        } else {
            wordsCountTxt.setText("0");
        }






        // MARK: - PLAY AGAIN BUTTON ------------------------------------
        Button paButt = (Button)findViewById(R.id.goPlayAgain);
        paButt.setTypeface(Configs.juneGull);
        paButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
        }});



        // MARK: - HOME BUTTON ------------------------------------
        Button hButt = (Button)findViewById(R.id.goHomeButt);
        hButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             startActivity(new Intent(GameOver.this, Home.class));
        }});



        // MARK: - SHARE CURRENT SCORE BUTTON ------------------------------------
        Button shareButt = (Button)findViewById(R.id.goShareButt);
        shareButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mmp.checkPermissionForWriteExternalStorage()) {
                    mmp.requestPermissionForWriteExternalStorage();
                } else {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    Uri uri = getImageUri(GameOver.this, bm);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);

                    String playStoreLink = "https://play.google.com/store/apps/details?id=" + GameOver.this.getPackageName();

                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareGameOver1) + " " + String.valueOf(Configs.score) + " " + getString(R.string.shareGameOver2) + " " + playStoreLink);
                    startActivity(Intent.createChooser(intent, "Share on..."));
                }
            }});

    }// end onCreate()



    // Method to get URI of a stored image
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "image", null);
        return Uri.parse(path);
    }



}// @end

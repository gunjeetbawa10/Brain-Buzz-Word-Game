package com.word.braingame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;


public class Home extends AppCompatActivity {

    /* Views */
    TextView bestTxt;


    /* Variables */
    SharedPreferences prefs;
    MarshMallowPermission mmp = new MarshMallowPermission(this);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView text =(TextView) findViewById(R.id.textView5);
        text.setMovementMethod(LinkMovementMethod.getInstance());


        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Init views
        bestTxt = (TextView)findViewById(R.id.hBestTxt);
        bestTxt.setTypeface(Configs.juneGull);



        // Get Best Score
        prefs = PreferenceManager.getDefaultSharedPreferences(Home.this);
        Configs.bestScore = prefs.getInt("bestScore", Configs.bestScore);
        bestTxt.setText(String.valueOf(Configs.bestScore));





        // MARK: - PLAY BUTTON ------------------------------------
        Button playButt = (Button)findViewById(R.id.hPlayButt);
        playButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(Home.this, GameBoard.class));
        }});





        // MARK: - SHARE BEST SCORE BUTTON ------------------------------------
        Button shareButt = (Button)findViewById(R.id.hShareButt);
        shareButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if (!mmp.checkPermissionForWriteExternalStorage()) {
                  mmp.requestPermissionForWriteExternalStorage();
              } else {
                  Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                  Uri uri = getImageUri(Home.this, bm);
                  Intent intent = new Intent(Intent.ACTION_SEND);
                  intent.setType("image/jpeg");
                  intent.putExtra(Intent.EXTRA_STREAM, uri);

                  String playStoreLink = "https://play.google.com/store/apps/details?id=" + Home.this.getPackageName();

                  intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share1) + " " + String.valueOf(Configs.bestScore) + "\n" + getString(R.string.share2) + " " + playStoreLink);
                  startActivity(Intent.createChooser(intent, "Share on..."));
              }
         }});
         //help
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,help.class));
            }
        });
        //Win
        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,win.class));
            }
        });



    }// end onCreate()






    // Method to get URI of a stored image
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "image", null);
        return Uri.parse(path);
    }


}// @end

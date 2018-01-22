package com.theRedCan.justin.dawae;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    ArrayList<MediaPlayer> mPlayerArr = new ArrayList<>();

    public void buttonTapped(View view){

        int id = view.getId();
        String ourId = view.getResources().getResourceEntryName(id);

        System.out.println(mPlayerArr.size());

        // Stops sounds

        if (ourId.equals("stop")) {
            while (mPlayerArr.size() > 0) {
                MediaPlayer mPlayer = mPlayerArr.get(0);

                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayerArr.remove(mPlayer);
                }
            }
            return;
        }

        //Play sounds

        int resourceId = getResources().getIdentifier(ourId,"raw", "com.theRedCan.justin.dawae");
        Uri mp3URI = Uri.parse("android.resource://com.theRedCan.justin.dawae/" + resourceId);

        MediaPlayer mPlayer = MediaPlayer.create(this, mp3URI);
        mPlayerArr.add(mPlayer);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                mPlayerArr.remove(mediaPlayer);
            }
        });
        mPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // AdMob
        MobileAds.initialize(this,"ca-app-pub-1750535467764713~4949840904");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("A4D0704C9E48682E1E7A19AE6B98FD7E")
//                .build();
        mAdView.loadAd(adRequest);
    }
}

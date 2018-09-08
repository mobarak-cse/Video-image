package com.tariqeeesciencelab.blogapp;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class SolveUserView extends AppCompatActivity {

    private TextView titleviewid,dateviewid,udateviewid,desviewid;

    private FirebaseFirestore db;
    private AdView mAdView;
    private SolveDatabase product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_user_view);

        product = (SolveDatabase) getIntent().getSerializableExtra("Question_Video_Solve");
        db = FirebaseFirestore.getInstance();

        titleviewid = findViewById(R.id.titleview);
        dateviewid = findViewById(R.id.dateview);
        desviewid = findViewById(R.id.desview);
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);




        titleviewid.setText(product.getTitle());

        dateviewid.setText("Upload Date : "+product.getDate());
        desviewid.setText(product.getDescription());

        jzVideoPlayerStandard.setUp(product.getVideo(),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                product.getTitle());

        ImageView imageView = findViewById(R.id.image_view);

        Picasso.get().load(product.getImage()).into(imageView);




    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}


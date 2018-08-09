package com.exmaple.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MovieDetailActivity extends AppCompatActivity {
    Toast mToast=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent=getIntent();
        int message=-1;
        int defValue=-1;
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            message=intent.getIntExtra(Intent.EXTRA_TEXT,defValue);
        }
        if(mToast!=null) {
            mToast.cancel();
        }
        mToast=Toast.makeText(this,String.valueOf(message),Toast.LENGTH_LONG);
        mToast.show();
    }
}

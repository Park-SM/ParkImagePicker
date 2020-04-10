package com.smparkworld.parkimagepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivSelectedImage = findViewById(R.id.ivActivityMain_selectedImage);
        findViewById(R.id.btnActivityMain_callImagePicker).setOnClickListener(this);
        findViewById(R.id.btnActivityMain_clearCache).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnActivityMain_callImagePicker:
                new ParkImagePicker(this)
                        .setOnSelectedListener(new ParkImagePicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(String uri) {
                                Toast.makeText(MainActivity.this, "URI: " + uri, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnSelectedImageView(ivSelectedImage)
                        .setNumOfColumns(4)
                        .setTitle("이미지선택")
                        .show();
                break;

            case R.id.btnActivityMain_clearCache:
                // Call when you need it.
                ParkImagePicker.clearCache(this);
                break;
        }
    }
}

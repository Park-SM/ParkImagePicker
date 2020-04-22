package com.smparkworld.parkimagepickerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smparkworld.parkimagepicker.ParkImagePicker;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivSelectedImage = findViewById(R.id.ivActivityMain_selectedImage);
        findViewById(R.id.btnActivityMain_callSinglePicker).setOnClickListener(this);
        findViewById(R.id.btnActivityMain_callMultiPicker).setOnClickListener(this);
        findViewById(R.id.btnActivityMain_clearCache).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnActivityMain_callSinglePicker:
                ParkImagePicker.create(this)
                        .setOnSelectedListener(new ParkImagePicker.OnSingleSelectedListener() {
                            @Override
                            public void onImageSelected(String uri) {
                                Glide.with(ivSelectedImage).load(uri).into(ivSelectedImage);
                                Toast.makeText(MainActivity.this, "Selected image uri: " + uri, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNumOfColumns(3)
                        .setTitle("이미지선택")
                        .start();
                break;

            case R.id.btnActivityMain_callMultiPicker:
                ParkImagePicker.create(this)
                        .setOnSelectedListener(new ParkImagePicker.OnMultiSelectedListener() {
                            @Override
                            public void onImageSelected(List<String> uri) {
                                if (uri.size() == 0) return;

                                Glide.with(ivSelectedImage).load(uri.get(0)).into(ivSelectedImage);
                                Toast.makeText(MainActivity.this, "Number of selected images: " + uri.size(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNumOfColumns(3)
                        .setTitle("이미지선택")
                        .start();
                break;

            case R.id.btnActivityMain_clearCache:
                // Call when you need it.
                ParkImagePicker.clearCache(this);
                break;
        }
    }
}

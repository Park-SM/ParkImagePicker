package com.smparkworld.parkimagepicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.smparkworld.parkimagepicker.presenter.ParkImagePickerPresenter;

import java.io.File;
import java.util.List;

public class ParkImagePicker extends AppCompatActivity implements View.OnClickListener  {

    public static final int REQUEST_CAMERA = 999;

    private ParkImagePickerPresenter presenter;

    // Option variables..
    private static Context mContext;
    private static OnSingleSelectedListener mSingleListener;
    private static OnMultiSelectedListener mMultiListener;
    private static boolean isSingleMode;
    private static int mNumOfColumns = 3;
    private static int mTitleBackColor;
    private static int mTitleFontColor;
    private static String mTitleText;
    private static Drawable mTakePictureBtnIcon;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_NoTitleBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_image_picker);
        presenter = new ParkImagePickerPresenter(this, mSingleListener, mMultiListener);

        RecyclerView rvContainer = findViewById(R.id.rvContainer);
        ImageButton btnDone = findViewById(R.id.btnDone);

        findViewById(R.id.btnClose).setOnClickListener(this);
        findViewById(R.id.btnTaskPicture).setOnClickListener(this);

        if (isSingleMode)
            btnDone.setVisibility(View.GONE);
        else
            btnDone.setOnClickListener(this);

        if (!presenter.loadImages(rvContainer, mNumOfColumns))
            Log.v("ParkImagePicker error!", "Failed to load images from device");

        if (mTitleBackColor != 0)
            findViewById(R.id.llTitleBack).setBackgroundColor(mTitleBackColor);
        if (mTitleFontColor != 0)
            ((TextView)findViewById(R.id.tvTitle)).setTextColor(mTitleFontColor);
        if (mTitleText != null)
            ((TextView)findViewById(R.id.tvTitle)).setText(mTitleText);
        if (mTakePictureBtnIcon != null)
            ((ImageButton)findViewById(R.id.btnTaskPicture)).setImageDrawable(mTakePictureBtnIcon);

        overridePendingTransition(R.anim.begin, R.anim.holding);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CAMERA)
            presenter.takePictureResult(resultCode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.holding, R.anim.end);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnClose) {
            onBackPressed();

        } else if (id == R.id.btnTaskPicture) {
            presenter.takePicture(REQUEST_CAMERA);

        } else if (id == R.id.btnDone) {
            presenter.completeMultiMode();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // option methods../////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public interface OnSingleSelectedListener {
        void onImageSelected(@NonNull String uri);
    }

    public interface OnMultiSelectedListener {
        void onImageSelected(@NonNull List<String> uri);
    }

    public static ParkImagePicker create(Context mContext) {
        ParkImagePicker.mContext = mContext;
        return new ParkImagePicker();
    }

    public ParkImagePicker setOnSelectedListener(OnSingleSelectedListener listener) {
        mSingleListener = listener;
        mMultiListener = null;
        isSingleMode = true;
        return this;
    }

    public ParkImagePicker setOnSelectedListener(OnMultiSelectedListener listener) {
        mSingleListener = null;
        mMultiListener = listener;
        isSingleMode = false;
        return this;
    }

    public ParkImagePicker setTakePictureBtn(boolean isShow) {
        if (isShow)
            findViewById(R.id.btnTaskPicture).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.btnTaskPicture).setVisibility(View.GONE);

        return this;
    }

    public ParkImagePicker setNumOfColumns(int column) {
        mNumOfColumns = (column <= 0) ? 1 : column;
        return this;
    }

    public ParkImagePicker setTitleBackgroundColor(int colorId) {
        mTitleBackColor = mContext.getResources().getColor(colorId);
        return this;
    }

    public ParkImagePicker setTitleFontColor(int colorId) {
        mTitleFontColor = mContext.getResources().getColor(colorId);
        return this;
    }

    public ParkImagePicker setTitle(String title) {
        mTitleText = title;
        return this;
    }

    public ParkImagePicker setTaskPictureBtnIcon(int drawableId) {
        mTakePictureBtnIcon = mContext.getResources().getDrawable(drawableId);
        return this;
    }

    public void start() {
        mContext.startActivity(new Intent(mContext, this.getClass()));
    }

    public static void clearCache(Context context) {
        File cache = context.getExternalCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                deleteDir(new File(appDir, s));
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                Log.d("ParkImagePicker result!", "File: " + children[i] + " DELETED");
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
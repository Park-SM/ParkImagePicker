package com.smparkworld.parkimagepicker.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smparkworld.parkimagepicker.adapter.ParkImagePickerAdapter;
import com.smparkworld.parkimagepicker.domain.Image;
import com.smparkworld.parkimagepicker.ParkImagePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ParkImagePickerPresenter {

    public static final int PMSS_READ_EXT_STORAGE = 5000;
    public static final int PMSS_WRITE_EXT_STORAGE = 5001;
    public static final int PMSS_CAMERA = 5002;

    private Context mContext;
    private ParkImagePicker.OnSingleSelectedListener mSingleListener;
    private ParkImagePicker.OnMultiSelectedListener mMultiListener;
    private ArrayList<Image> mSelectedURIList;
    private String mImagePathForActionView;
    private Uri mTargetUri;

    public ParkImagePickerPresenter(Context mContext,
                                    ParkImagePicker.OnSingleSelectedListener mSingleListener,
                                    ParkImagePicker.OnMultiSelectedListener mMultiListener) {
        this.mContext = mContext;
        this.mSingleListener = mSingleListener;
        this.mMultiListener = mMultiListener;
        mSelectedURIList = new ArrayList<Image>();
    }

    public boolean loadImages(RecyclerView rvContainer, int numOfColumns) {
        if (!requestPermission()) {
            Log.v("ParkImagePicker error!", "Permission denied: READ_EXTERNAL_STORAGE or WRITE_EXTERNAL_STORAGE or CAMERA");
            return false;
        }

        rvContainer.setLayoutManager(new GridLayoutManager(mContext, numOfColumns));
        rvContainer.setAdapter(new ParkImagePickerAdapter(mContext, mSelectedURIList, mSingleListener));
        return true;
    }

    public void completeMultiMode() {
        if (mMultiListener != null) {
            ArrayList<String> returnList = new ArrayList<String>();
            for (int i = 0; i < mSelectedURIList.size(); i++) {
                returnList.add(mSelectedURIList.get(i).getUri());
            }
            mMultiListener.onImageSelected(returnList);
        }

        ((ParkImagePicker)mContext).finish();
    }

    public boolean requestPermission() {
        boolean ret = true;
        // android.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)mContext), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ret = false;
            } else {
                ActivityCompat.requestPermissions(((Activity)mContext), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PMSS_READ_EXT_STORAGE);
            }
        }
        // android.permission.WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)mContext), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ret = false;
            } else {
                ActivityCompat.requestPermissions(((Activity)mContext), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PMSS_WRITE_EXT_STORAGE);
            }
        }
        // android.permission.CAMERA
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)mContext), Manifest.permission.CAMERA)) {
                ret = false;
            } else {
                ActivityCompat.requestPermissions(((Activity)mContext), new String[] {Manifest.permission.CAMERA}, PMSS_CAMERA);
            }
        }
        return ret;
    }

    public void takePictureResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            if (mSingleListener != null) mSingleListener.onImageSelected(mImagePathForActionView);
            if (mMultiListener != null) {
                ArrayList<String> returnList = new ArrayList<String>();
                returnList.add(mImagePathForActionView);
                mMultiListener.onImageSelected(returnList);
            }

            ((ParkImagePicker) mContext).finish();
        }
    }

    public boolean takePicture(int requestCode) {
        try {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(mContext.getPackageManager()) == null) {
                Log.v("ParkImagePicker error!", "resolveActivity method return null.");
                return false;
            }

            File tempFile = createTempImage();
            if (tempFile == null) throw new IOException();

            String storageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(storageState)) {
                mTargetUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", tempFile);
            } else {
                Log.v("ParkImagePicker error!", "SD card cannot be used");
                return false;
            }

            i.putExtra(MediaStore.EXTRA_OUTPUT, mTargetUri);
            ((ParkImagePicker)mContext).startActivityForResult(i, requestCode);

        } catch (IOException e) {
            Log.v("ParkImagePicker error!", "Failed to create temp image file.");
        }
        return true;
    }

    public File createTempImage() {
        try {
            // Create an image file name
            String imageFileName = new SimpleDateFormat("yyyyMMdd_Hms").format(new Date());
            File storageDir = mContext.getExternalCacheDir();

            File tempFile = File.createTempFile(
                    imageFileName,    // prefix
                    ".jpg",     // suffix
                    storageDir        // storage directory
            );

            // Path for use with ACTION_VIEW intent.
            mImagePathForActionView = tempFile.getAbsolutePath();
            return tempFile;

        } catch(IOException e) {
            Log.v("ParkImagePicker error!", "Failed to create temp image.");
            return null;
        }
    }

}

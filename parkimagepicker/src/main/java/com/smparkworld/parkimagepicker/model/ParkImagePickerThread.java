package com.smparkworld.parkimagepicker.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.smparkworld.parkimagepicker.domain.Image;

import java.util.ArrayList;

public abstract class ParkImagePickerThread extends AsyncTask<Void, Void, Boolean> {

    public static final int LOAD_ONCE = 100;

    private Cursor mCursor;
    private ArrayList<Image> mModelList;

    public ParkImagePickerThread(Cursor mCursor, ArrayList<Image> mModelList) {
        this.mCursor = mCursor;
        this.mModelList = mModelList;
    }

    @Override
    protected Boolean doInBackground(Void... args) {

        int fieldIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        for (int i = 0; i < LOAD_ONCE; i++) {
            if (mCursor.moveToNext())
                mModelList.add(new Image(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mCursor.getString(fieldIndex)).toString()));
            else return true;
        }
        return false;
    }

    @Override
    protected abstract void onPostExecute(Boolean isEnd);
}

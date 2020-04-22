package com.smparkworld.parkimagepicker.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smparkworld.parkimagepicker.R;
import com.smparkworld.parkimagepicker.domain.Image;
import com.smparkworld.parkimagepicker.model.ParkImagePickerThread;
import com.smparkworld.parkimagepicker.ParkImagePicker;

import java.util.ArrayList;

public class ParkImagePickerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Cursor mCursor;
    private int mNumOfItems;
    private ArrayList<Image> mModelList;
    private ArrayList<Image> mSelectedURIList;
    private ParkImagePicker.OnSingleSelectedListener mListener;
    private boolean isLoading;

    public ParkImagePickerAdapter(Context mContext, ArrayList<Image> mSelectedURIList,
                                  ParkImagePicker.OnSingleSelectedListener mListener) {
        this.mContext = mContext;
        this.mModelList = new ArrayList<Image>();
        this.mSelectedURIList = mSelectedURIList;
        this.mListener = mListener;

        mCursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        );

        // Check if the cursor is invalid.
        if (mCursor == null || !mCursor.moveToFirst())
            Log.v("ParkImagePicker error!", "Failed to create cursor.");
        else
            loadImage();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_selected_index, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ImageView ivImage = ((ViewHolder)holder).imageView;
        final LinearLayout llSelectedLayer = ((ViewHolder)holder).selectedLayer;
        final TextView tvSelectedIndex = ((ViewHolder)holder).selectedIndex;

        final Image image = mModelList.get(position);
        final String uri = image.getUri();

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image image = mModelList.get(position);
                if (mListener != null) {        // When is single mode.
                    mListener.onImageSelected(uri);
                    ((ParkImagePicker)mContext).finish();

                } else {                        // When is multi mode.
                    if (!image.isSelected()) {
                        mSelectedURIList.add(image);
                        image.setSelectedIndex(mSelectedURIList.size());
                        setVisibleSelection(tvSelectedIndex, llSelectedLayer, image);
                    } else {
                        mSelectedURIList.remove(image);
                        image.setSelected(false);
                        setVisibleSelection(tvSelectedIndex, llSelectedLayer, image);
                        sortSelectedList();
                    }
                }
            }
        });
        setVisibleSelection(tvSelectedIndex, llSelectedLayer, image);

        Glide.with(ivImage)
                .load(uri)
                .priority(Priority.HIGH)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(200, 200)
                .into(ivImage);

        if (position > mNumOfItems * 0.8) loadImage();
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    private void setVisibleSelection(TextView tvSelectedIndex, LinearLayout llSelectedLayer, Image image) {
        if (image.isSelected()) {
            tvSelectedIndex.setVisibility(View.VISIBLE);
            llSelectedLayer.setVisibility(View.VISIBLE);
            tvSelectedIndex.setText(Integer.toString(image.getSelectedIndex()));
        } else {
            tvSelectedIndex.setVisibility(View.GONE);
            llSelectedLayer.setVisibility(View.GONE);
        }
    }

    private void sortSelectedList() {
        for (int i = 0; i < mSelectedURIList.size(); i++) {
            Image image = mSelectedURIList.get(i);
            image.setSelectedIndex(i + 1);
        }

        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public LinearLayout selectedLayer;
        public TextView selectedIndex;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.ivImage);
            selectedLayer = v.findViewById(R.id.llSelectedLayer);
            selectedIndex = v.findViewById(R.id.tvSelectedIndex);

            int fontColor = ((ParkImagePicker)mContext).getTitleFontColor();
            int backColor = ((ParkImagePicker)mContext).getTitleBackColor();

            if (fontColor != 0)
                selectedIndex.setTextColor(fontColor);
            if (backColor != 0)
                selectedIndex.setBackgroundColor(backColor);
        }
    }

    private void loadImage() {
        if (isLoading || mCursor == null) return;
        isLoading = true;

        new ParkImagePickerThread(mCursor, mModelList) {
            @Override
            protected void onPostExecute(Boolean isEnd) {
                mNumOfItems = mModelList.size();
                notifyDataSetChanged();

                if (isEnd) mCursor = null;
                isLoading = false;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
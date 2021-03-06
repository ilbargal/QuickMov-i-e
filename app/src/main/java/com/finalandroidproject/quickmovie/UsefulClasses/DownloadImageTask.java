package com.finalandroidproject.quickmovie.UsefulClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;

    public DownloadImageTask(ImageView bmImage, ProgressBar progressBar)
    {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        this.progressBar.setVisibility(View.GONE);
        bmImage.setVisibility(View.VISIBLE);
        bmImage.setImageBitmap(result);
    }
}

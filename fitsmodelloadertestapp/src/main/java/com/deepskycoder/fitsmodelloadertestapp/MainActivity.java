package com.deepskycoder.fitsmodelloadertestapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;

/**
 * Test of custom Glide module to load Fits files
 * */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);

        try {

            InputStream input = getResources().openRawResource(R.raw.crab_float32);
            System.out.println("Available bytes in the file: " + input.available());

/*          //Load bitmap the old fashion way

            MyImageBuilder imageBuilder = null;
            try {
                imageBuilder  = FitDecoder.decode(input);
                if (imageBuilder != null) {
                    System.out.println("ImageBuilder created");
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

            int[] data = imageBuilder.getData();
            int width = imageBuilder.getWidth();
            int height = imageBuilder.getHeight();

            Bitmap bmp = Bitmap.createBitmap(data,
                    width,
                    height,
                    Bitmap.Config.ARGB_8888
                    );

            imageView.setImageBitmap(bmp);
*/

 /*           //Use Glide - Glide uses a custom loader and
            //Datafetcher for Fits files
            Glide.with(this)
                    .asBitmap()
                    .load(input)
                    .into(imageView);
*/
          //Yet another way
            Glide.with(this)
                .asBitmap()
                .load(input)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(bitmap);
                        imageView.buildDrawingCache();
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) { }
                });


        // Close the input stream
        //input.close();


        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
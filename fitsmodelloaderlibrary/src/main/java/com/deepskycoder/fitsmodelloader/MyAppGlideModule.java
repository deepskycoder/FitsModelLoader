package com.deepskycoder.fitsmodelloader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;

import java.io.InputStream;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.prepend(InputStream.class, Bitmap.class, new FitsModelLoaderFactory());
    }
}

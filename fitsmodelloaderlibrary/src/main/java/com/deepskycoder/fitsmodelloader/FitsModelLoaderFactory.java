package com.deepskycoder.fitsmodelloader;

import android.graphics.Bitmap;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

public class FitsModelLoaderFactory implements ModelLoaderFactory<InputStream, Bitmap> {
    @Override
    public ModelLoader<InputStream, Bitmap> build(MultiModelLoaderFactory multifactory) {
        return new FitsModelLoader();
    }

    @Override
    public void teardown() {
        // Do nothing.
    }
}

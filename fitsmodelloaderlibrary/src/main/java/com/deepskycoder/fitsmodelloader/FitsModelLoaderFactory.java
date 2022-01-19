package com.deepskycoder.fitsmodelloader;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

public class FitsModelLoaderFactory implements ModelLoaderFactory<Uri, Bitmap> {

    private final ContentResolver resolver;

    public FitsModelLoaderFactory(ContentResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public ModelLoader<Uri, Bitmap> build(MultiModelLoaderFactory multifactory) {
        return new FitsModelLoader(resolver);
    }

    @Override
    public void teardown() {
        // Do nothing.
    }
}

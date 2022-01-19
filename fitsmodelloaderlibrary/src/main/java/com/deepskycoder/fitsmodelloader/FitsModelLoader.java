package com.deepskycoder.fitsmodelloader;

import static org.apache.commons.imaging.common.BinaryFunctions.readByte;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;

/**
 * Loads a Tiff image file.
 */


public class FitsModelLoader implements ModelLoader<Uri, Bitmap>{

    //private final ModelLoader<InputStream, Bitmap> fitsloader;
    private final ContentResolver resolver;

    // Public API.
    @SuppressWarnings("WeakerAccess")
    public FitsModelLoader(ContentResolver resolver) {
        this.resolver = resolver;
        //this.fitsloader = fitsloader;
    }

    @Nullable
    @Override
    public LoadData<Bitmap> buildLoadData(Uri uri, int width, int height, Options options) {
        InputStream model = null;
        try {
            model = resolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new LoadData<Bitmap>(new ObjectKey(model), new FitsDataFetcher(model, width, height));
    }

    @Override
    public boolean handles(Uri uri) {
        return true;
    }

}

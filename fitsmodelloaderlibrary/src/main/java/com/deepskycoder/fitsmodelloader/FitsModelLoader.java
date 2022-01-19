package com.deepskycoder.fitsmodelloader;

import static org.apache.commons.imaging.common.BinaryFunctions.readByte;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;

/**
 * Loads a Tiff image file.
 */


public class FitsModelLoader implements ModelLoader<InputStream, Bitmap>{

    @Nullable
    @Override
    public LoadData<Bitmap> buildLoadData(InputStream model, int width, int height, Options options) {
        return new LoadData<Bitmap>(new ObjectKey(model), new FitsDataFetcher(model, width, height));
    }

    @Override
    public boolean handles(InputStream model) {
        return true;
    }

}

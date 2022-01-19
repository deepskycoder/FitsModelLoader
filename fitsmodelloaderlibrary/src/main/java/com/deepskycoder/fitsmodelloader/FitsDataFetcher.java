package com.deepskycoder.fitsmodelloader;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;

import org.apache.commons.imaging.ImageReadException;

import nom.tam.fits.FitsException;


public class FitsDataFetcher implements DataFetcher<Bitmap>{

    private final InputStream inputStream;
    private int data_width;
    private int data_height;
    FitsDataFetcher(InputStream inputStream, int width, int height) {

        this.inputStream = inputStream;
        this.data_width = width;
        this.data_height = height;
    }

    @Override
    public void loadData(Priority priority, DataCallback<? super Bitmap> callback) {

        MyImageBuilder ib = null;
        try {
           ib  = FitsDecoder.decode(inputStream);
        } catch (ImageReadException e) {
            e.printStackTrace();
            System.out.println("FitLoader DataFetcher failed!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FitLoader DataFetcher failed!");
        } catch (FitsException e) {
            e.printStackTrace();
        }

        if (ib != null) {
            System.out.println("FitLoader DataFetcher retrieved data!");
            int[] samples = ib.getData();
            data_width = ib.getWidth();
            data_height = ib.getHeight();
            //byte[] data = ByteConversions.toBytes(samples, ByteOrder.LITTLE_ENDIAN);
            //ByteBuffer byteBuffer = ByteBuffer.wrap(data);

            Bitmap bmp = Bitmap.createBitmap(samples,
                    data_width,
                    data_height,
                    Bitmap.Config.ARGB_8888
            );
            callback.onDataReady(bmp);
         } else {
            callback.onLoadFailed(new IOException("Forced Glide failure. Can't load image"));
        }
    }

    @Override
    public void cleanup() {
        // Must close and cleanup InputStream!
        //inputStream.close();
    }

    @Override
    public void cancel() {
        // Intentionally empty.
    }

    @NonNull
    @Override
    public Class<Bitmap> getDataClass() {
        return Bitmap.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}

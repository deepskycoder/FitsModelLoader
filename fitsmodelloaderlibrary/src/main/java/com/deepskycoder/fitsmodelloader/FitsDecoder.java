package com.deepskycoder.fitsmodelloader;

import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.HashMap;

import android.content.ContentResolver;
import android.net.Uri;

import org.apache.commons.imaging.ImageReadException;


import static nom.tam.fits.header.Standard.NAXIS;
import static nom.tam.fits.header.Standard.NAXISn;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.Header;
import nom.tam.fits.ImageData;
import nom.tam.fits.ImageHDU;
import nom.tam.util.SafeClose;

/**
 * Provides a example application showing how to access metadata and imagery
 * from TIFF files using the low-level access routines. This approach is
 * especially useful if the TIFF file includes multiple images.
 */
public class FitsDecoder {

    public static MyImageBuilder decode(final InputStream target)
            throws FitsException, ImageReadException, IOException {

        PrintStream ps = System.out;
        MyImageBuilder imageBuilder = null;

        final HashMap<String, Object> params = new HashMap<>();

        Fits f = null;

        try {

            f = new Fits(target);
            ImageHDU hdu =  (ImageHDU) f.getHDU(0);
            Header hdr = hdu.getHeader();
            boolean hasAlpha = false;
            int n = hdr.getIntValue(NAXIS);
            if (n !=3) {
                System.out.println("Fit file is not an RGB image");
                throw new FitsException("Fit file is not an RGB image");
            }
            int width = hdr.getIntValue(NAXISn.n(1));
            int height = hdr.getIntValue(NAXISn.n(2));

            imageBuilder = new MyImageBuilder(width, height, hasAlpha);
            if (-32 == hdu.getBitPix()) {
                System.out.println("Fit file contains single precision float data");
                // float data in range [0.,1.0]
                // convert to [0,255]
                ImageData imageData = (ImageData) hdu.getData();
                float[][][] data = (float[][][]) imageData.getData();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int r = (int) (data[0][j][i] * 255);
                        int g = (int) (data[1][j][i] * 255);
                        int b = (int) (data[2][j][i] * 255);
                        int a = 0xff;
                        int rgb = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                        imageBuilder.setRGB(j, i, rgb);
                    }
                }
            } else if (8 == hdu.getBitPix() ) {
                System.out.println("Fit file contains byte size 8-bit data");
                ImageData imageData = (ImageData) hdu.getData();
                byte[][][] data = (byte[][][]) imageData.getData();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {

                        int r = (int) (data[0][j][i] & 0xff);
                        int g = (int) (data[1][j][i] & 0xff);
                        int b = (int) (data[2][j][i] & 0xff);
                        int a = 0xff;
                        int rgb = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                        imageBuilder.setRGB(j, i, rgb);
                    }
                }
            } else if (16 == hdu.getBitPix() ) {
                System.out.println("Fit file contains integer 16-bit data");
                // data in range [-Short.MAX_VALUE, Short.MAX_VALUE]
                // signed two's complement short
                // convert to [0,255]
                ImageData imageData = (ImageData) hdu.getData();
                short[][][] data = (short[][][]) imageData.getData();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {

                        short s = (short)((data[0][j][i] + Short.MAX_VALUE)/2);
                        float sf = (float)s/Short.MAX_VALUE; //sF in range [0,1]
                        int r = (int)(sf * 255);
                        r = Math.min(Math.max(r,0), 255);

                        s = (short)((data[1][j][i] + Short.MAX_VALUE)/2);
                        sf = (float)s/Short.MAX_VALUE;
                        int g = (int)(sf * 255);
                        g = Math.min(Math.max(g,0), 255);

                        s = (short)((data[2][j][i] + Short.MAX_VALUE)/2);
                        sf = (float)s/Short.MAX_VALUE; //sF in range [0,1]
                        int b = (int)(sf * 255);
                        b = Math.min(Math.max(b,0), 255);

                        int a = 0xff;
                        int rgb = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                        imageBuilder.setRGB(j, i, rgb);
                    }
                }
            } else if (32 == hdu.getBitPix() ) {
                System.out.println("Fit file contains integer 32-bit data");
                // data will be signed integer, twos complement in range [-Integer.MAX_VALUE , Integer.MAX_VALUE]
                // convert to [0,255]
                ImageData imageData = (ImageData) hdu.getData();
                int[][][] data = (int[][][]) imageData.getData();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {

                        float s = (float)data[0][j][i]/Integer.MAX_VALUE; //[-1,1]
                        s = (s + 1)/2; //s in range [0,1]
                        int r = (int)(s * 255); //[0,255]
                        r = Math.min(Math.max(r,0), 255);

                        s = (float)data[1][j][i]/Integer.MAX_VALUE;
                        s = (s + 1)/2; //s in range [0,1]
                        int g = (int)(s * 255);
                        g = Math.min(Math.max(g,0), 255);

                        s = (float)data[2][j][i]/Integer.MAX_VALUE;
                        s = (s + 1)/2; //s in range [0,1]
                        int b = (int)(s * 255);
                        g = Math.min(Math.max(b,0), 255);

                        int a = 0xff;
                        int rgb = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                        imageBuilder.setRGB(j, i, rgb);
                    }
                }
            } else {
                System.out.println("Fit file data type not supported");
                throw new FitsException("Fit file data type not supported");
            }
        } catch (FitsException e) {
            e.getStackTrace();
        }
        finally {
                SafeClose.close(f);
        }

        return imageBuilder;

    }
}

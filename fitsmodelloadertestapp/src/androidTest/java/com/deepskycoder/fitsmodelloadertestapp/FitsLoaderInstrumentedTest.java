package com.deepskycoder.fitsmodelloadertestapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.Header;
import nom.tam.fits.ImageHDU;
import static nom.tam.fits.header.Standard.NAXIS;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FitsLoaderInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.deepskycoder.fitsmodelloadertestapp", appContext.getPackageName());
    }

    @Test
    public void open_Stream() {
        // open FITS using InputStream
        try {
            //Context ctx = InstrumentationRegistry.getInstrumentation().getContext();
            InputStream inputstream = getClass().getResourceAsStream("crab_float32.fit");
            Fits using_inputstream = new Fits(inputstream);
            assert(using_inputstream != null);
            ImageHDU hdu =  (ImageHDU) using_inputstream.getHDU(0);
            assert(hdu != null);
            Header hdr = hdu.getHeader();
            assert(hdr !=null);
            int n = hdr.getIntValue(NAXIS);
            assertEquals(3, n);
            System.out.println("Test open_Stream: NAXIS = 3");
            int i = hdu.getBitPix();
            assertEquals(-32, i);
            System.out.println("Test open_Stream: BITPIX = -32");
            using_inputstream.close();
            inputstream.close();
        } catch (FitsException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    @Test
    public void open_Url() {
        // open FITS using url

        Context ctx = InstrumentationRegistry.getInstrumentation().getContext();
        URL url = getClass().getResource("crab_float32.fit");

        if (url !=null) {
            try {
                Fits using_url = new Fits(url);
                assert(using_url != null);
                ImageHDU hdu =  (ImageHDU) using_url.getHDU(0);
                assert(hdu != null);
                Header hdr = hdu.getHeader();
                assert(hdr != null);
                int n = hdr.getIntValue(NAXIS);
                assertEquals(3, n);
                System.out.println("Test open_Url: NAXIS = 3");
                int i = hdu.getBitPix();
                assertEquals(-32, i);
                System.out.println("Test open_Url: BITPIX = -32");
                using_url.close();
            } catch (FitsException e) {
                e.getStackTrace();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }
}
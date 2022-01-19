com.deepskycoder.FitsModelLoader

Custom Glide Module to load Fits image files.

An Android java library to extend Glide. 
It loads Fits image files 
with the following data formats:

32 bits float
8 bits  integer (unsigned byte)
16 bits integer (signed short)
32 bits integer (signed integer)

Decoder converts the data into Bitmap ARGB_8888
for use by Glide to display into an ImageView.

Dependencies:
https://github.com/nom-tam-fits

Easy to use. Just do this:

InputStream input = getResources().openRawResource(R.raw.crab_nebula);
Glide.with(this)
     .asBitmap()
     .load(input)
     .into(imageView);

Substitute your own InputStream pointing to a Fits file.

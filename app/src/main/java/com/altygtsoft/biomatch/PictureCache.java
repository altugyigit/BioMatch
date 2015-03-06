package com.altygtsoft.biomatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Kerem on 6.3.2015.
 */
public class PictureCache extends TakePic3Activity {

        private byte[] byteArrayTac;
        private byte[] byteArrayCanak;
        private byte[] byteArrayYaprak;

        public TakePic3Activity takePic3Activity = new TakePic3Activity();


    public byte[] getByteArrayTac() {
        return byteArrayTac;
    }

    public void setByteArrayTac(byte[] byteArrayTac) {
        this.byteArrayTac = byteArrayTac;
    }

    public byte[] getByteArrayCanak() {
        return byteArrayCanak;
    }

    public void setByteArrayCanak(byte[] byteArrayCanak) {
        this.byteArrayCanak = byteArrayCanak;
    }

    public byte[] getByteArrayYaprak() {
        return byteArrayYaprak;
    }

    public void setByteArrayYaprak(byte[] byteArrayYaprak) {
        this.byteArrayYaprak = byteArrayYaprak;
    }
}

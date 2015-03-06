package com.altygtsoft.biomatch;

public class PictureCache extends TakePictureActivity {

        private byte[] byteArrayTac;
        private byte[] byteArrayCanak;
        private byte[] byteArrayYaprak;

        public TakePictureActivity takePictureActivity = new TakePictureActivity();


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

/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

/*
    This code is taken from Rafael Sanches' blog.
    http://blog.rafaelsanches.com/2011/01/29/upload-using-multipart-post-using-httpclient-in-android/
*/

package com.jdhui.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

import com.jdhui.uitls.SystemInfo;

class ProgressMultipartEntity extends SimpleMultipartEntity {

    private OnProgressChangeListener listener = null;
    private long totalSize = 100;
    private String fileName = "";

    public void setListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }

    public void addPart(final String key, final String fileName, final InputStream fin, String type,
                    final boolean isLast) {
        writeBoundary();
        try {
            type = "Content-Type: " + type + "\r\n";
            out.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n")
                            .getBytes());
            out.write(type.getBytes());
            //out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
            out.write(("\r\n").getBytes());
            this.totalSize = fin.available();
            this.fileName = fileName;
            //            long transferred = 0;
            final byte[] tmp = new byte[4096];
            int l = 0;
            while ((l = fin.read(tmp)) != -1) {
                out.write(tmp, 0, l);
                //                if (listener != null)
                //                    transferred += l;
                //                listener.onProgressChanged(transferred, size, fileName);
            }
            out.write(("\r\n").getBytes());

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
        writeLastBoundaryIfNeeds();
        new CountingOutputStream(outstream).write(out.toByteArray());
    }

    private class CountingOutputStream extends FilterOutputStream {

        private long transferred;

        public CountingOutputStream(final OutputStream out) {

            super(out);
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {

            out.write(b, off, len);
            this.transferred += len;
            if (SystemInfo.DEBUG) {
                Log.e("mutipart entity:", "transferred:" + transferred);
            }
            listener.onProgressChanged(transferred, totalSize, fileName);
        }

        public void write(int b) throws IOException {

            out.write(b);
            this.transferred++;
            listener.onProgressChanged(transferred, totalSize, fileName);
        }
    }

}
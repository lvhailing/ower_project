package com.jdhui.compatibility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PatchBufferedInputStream extends BufferedInputStream {

    public PatchBufferedInputStream(InputStream in) {
        super(in);
    }

    public PatchBufferedInputStream(InputStream in, int size) {
        super(in, size);
    }

    /**
     * Resets this stream to the last marked location.
     *
     * @throws IOException
     *             if this stream is closed, no mark has been set or the mark is
     *             no longer valid because more than {@code readlimit} bytes
     *             have been read since setting the mark.
     * @see #mark(int)
     */
    @Override
    public synchronized void reset() throws IOException {
        if (buf == null) {
            throw new IOException("Stream is closed");
        }
        if (-1 == markpos) {
            InputStream localIn = in;
            if (localIn instanceof FileInputStream) {
                try {
                    ((FileInputStream) localIn).getChannel().position(0);
                    pos = markpos = 0;
                    count = 0;
                    return;
                } catch (Exception e) {
                    throw new IOException("Mark has been invalidated.");
                }
            } else {
                throw new IOException("Mark has been invalidated.");
            }
        }
        pos = markpos;
    }

}

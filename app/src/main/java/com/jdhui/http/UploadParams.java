package com.jdhui.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;

/**
 * A collection of string request parameters or files to send along with
 * requests made from an {@link AsyncHttpClient} instance.
 * <p>
 * For example:
 * <p>
 * <pre>
 * RequestParams params = new RequestParams();
 * params.put("username", "james");
 * params.put("password", "123456");
 * params.put("email", "my&#064;email.com");
 * params.put("profile_picture", new File("pic.jpg")); // Upload a File
 * params.put("profile_picture2", someInputStream); // Upload an InputStream
 * params.put("profile_picture3", new ByteArrayInputStream(someBytes)); // Upload some bytes
 *
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.post("http://myendpoint.com", params, responseHandler);
 * </pre>
 */
public class UploadParams extends RequestParams {

    /**
      * Returns an HttpEntity containing all request parameters
      */
    public HttpEntity getEntity() {
        HttpEntity entity = null;

        if (!fileParams.isEmpty()) {
            ProgressMultipartEntity multipartEntity = new ProgressMultipartEntity();

            if (listener != null) {
                multipartEntity.setListener(listener);
            }
            // Add string params
            for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
                multipartEntity.addPart(entry.getKey(), entry.getValue());
            }

            // Add dupe params
            for (ConcurrentHashMap.Entry<String, ArrayList<String>> entry : urlParamsWithArray.entrySet()) {
                ArrayList<String> values = entry.getValue();
                for (String value : values) {
                    multipartEntity.addPart(entry.getKey(), value);
                }
            }

            // Add file params
            int currentIndex = 0;
            int lastIndex = fileParams.entrySet().size() - 1;
            for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
                FileWrapper file = entry.getValue();
                if (file.inputStream != null) {
                    boolean isLast = currentIndex == lastIndex;
                    if (file.contentType != null) {
                        multipartEntity.addPart(entry.getKey(), file.fileName, file.inputStream, file.contentType,
                                        isLast);
                    } else {
                        multipartEntity.addPart(entry.getKey(), file.fileName, file.inputStream, isLast);
                    }
                }
                currentIndex++;
            }

            entity = multipartEntity;
        } else {
            try {
                entity = new UrlEncodedFormEntity(getParamsList(), ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return entity;
    }

    private OnProgressChangeListener listener = null;

    public void setListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }
}

package com.jdhui.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

import android.os.Message;

public class HttpEntityResponseHandler extends AsyncHttpResponseHandler {
    public HttpEntityResponseHandler() {
        super();
    }

    /**
     * Fired when a request returns successfully and contains a json object
     * at the base of the response string. Override to handle in your
     * own code.
     * @param response the parsed json object found in the server response (if any)
     */
    public void onSuccess(HttpEntity response) {
    }


    /**
     * Fired when a request returns successfully and contains a json object
     * at the base of the response string. Override to handle in your
     * own code.
     * @param statusCode the status code of the response
     * @param response the parsed json object found in the server response (if any)
     */
    public void onSuccess(int statusCode, HttpEntity response) {
        onSuccess(response);
    }

    /**
     * Fired when a request returns successfully and contains a json array
     * at the base of the response string. Override to handle in your
     * own code.
     * @param statusCode the status code of the response
     * @param headers the headers of the HTTP response
     * @param response the parsed json array found in the server response (if any)
     */
    public void onSuccess(int statusCode, Header[] headers, HttpEntity entity) {
        onSuccess(statusCode, entity);
    }

    public void onFailure(Throwable e, HttpEntity entity) {
    }


    protected void handleSuccessMessage(int statusCode, Header[] headers, HttpEntity entity) {
        onSuccess(statusCode, headers, entity);
    }

    protected void handleFailureMessage(Throwable e, HttpEntity entity) {
        onFailure(e, entity);
    }
    
    protected void sendSuccessMessage(int statusCode, Header[] headers, HttpEntity entity) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{Integer.valueOf(statusCode), headers, entity}));
    }

    protected void sendFailureMessage(Throwable e, HttpEntity entity) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{e, entity}));
    }
    
    protected void sendFailureMessage(Throwable e, String entity) {
    	sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{e, null}));
    }
 // Methods which emulate android's Handler and Message methods
    @Override
    protected void handleMessage(Message msg) {
        Object[] response;
        switch(msg.what) {
            case SUCCESS_MESSAGE:
                response = (Object[])msg.obj;
                handleSuccessMessage(((Integer) response[0]).intValue() , (Header[]) response[1],(HttpEntity) response[2]);
                break;
            case FAILURE_MESSAGE:
                response = (Object[])msg.obj;
                handleFailureMessage((Throwable)response[0], (HttpEntity) response[1]);
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }
    
    // Interface to AsyncHttpRequest
    @Override
    void sendResponseMessage(HttpResponse response) {
        StatusLine status = response.getStatusLine();
        Header[] contentTypeHeaders = response.getHeaders("Content-Type");
        HttpEntity entity = null;
        if (contentTypeHeaders.length != 1) {
            //malformed/ambiguous HTTP Header, ABORT!
            sendFailureMessage(new HttpResponseException(status.getStatusCode(),
                            "None, or more than one, Content-Type Header found!"), entity);
            return;
        }
        try {
            if(status.getStatusCode() >= 300) {
                sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), entity);
            } else {
                entity = response.getEntity();
                if(entity != null) {
                    sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), entity);
                } else {
                    sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), entity);
                }
            }
            
        } catch (Exception e) {
            sendFailureMessage(e, (byte[]) null);
        }

    }
}

package com.brein.engine;

import android.util.Log;

import com.brein.api.BreinActivity;
import com.brein.api.BreinBase;
import com.brein.api.BreinLookup;
import com.brein.api.Breinify;
import com.brein.api.ICallback;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.util.BreinUtil;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * could be the jersey rest com.brein.engine implementation
 */
public class HttpUrlRestEngine implements IRestEngine {

    private static final String TAG = "HttpUrlRestEngine";

    /**
     * constant for post method
     */
    private static final String POST_METHOD = "POST";

    /**
     * invokes the post request. Needs to run a thread.
     *
     * @param breinActivity data
     */
    @Override
    public void doRequest(final BreinActivity breinActivity) {

        // validate the input objects
        BreinUtil.validate(breinActivity);

        final String fullUrl = BreinUtil.getFullyQualifiedUrl(breinActivity);
        final String requestBody = BreinUtil.getRequestBody(breinActivity);
        Log.d(TAG, "Request is: " + requestBody);
        final int connectionTimeout = (int) Breinify.getConfig().getConnectionTimeout();
        final int readTimeout = (int) Breinify.getConfig().getSocketTimeout();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL url = new URL(fullUrl);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectionTimeout);
                    conn.setRequestMethod(POST_METHOD);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    Log.d(TAG, "Outputstream is: " + conn.getOutputStream());
                    final PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.print(requestBody);
                    out.close();

                    conn.connect();
                    final int response = conn.getResponseCode();
                    Log.d(TAG, "response is: " + response);

                } catch (final Exception e) {
                    Log.d(TAG, "HttpUrlRestEngine exception is: " + e);
                }

            }
        }).start();
    }

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) {

        // validate the input objects
        BreinUtil.validate(breinLookup);

        final String fullUrl = BreinUtil.getFullyQualifiedUrl(breinLookup);
        final String requestBody = BreinUtil.getRequestBody(breinLookup);
        final int connectionTimeout = (int) Breinify.getConfig().getConnectionTimeout();
        final int readTimeout = (int) Breinify.getConfig().getSocketTimeout();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL url = new URL(fullUrl);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectionTimeout);
                    conn.setRequestMethod(POST_METHOD);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    Log.d(TAG, "Outputstream is: " + conn.getOutputStream());
                    final PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.print(requestBody);
                    out.close();

                    conn.connect();
                    final int response = conn.getResponseCode();

                    if (response == HttpURLConnection.HTTP_OK) {
                        final StringBuilder sb = new StringBuilder();
                        final InputStream mInputStream = conn.getInputStream();

                        int i;
                        while ((i = mInputStream.read()) != -1) {
                            sb.append((char) i);
                        }
                    } else {
                        Log.d(TAG, "doLookup - exception");
                    }

                } catch (final Exception e) {
                    Log.d(TAG, "doLookup - exception");
                }

            }
        }).start();

        return null;
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    @Override
    public IRestEngine getRestEngine(BreinEngineType engine) {
        return null;
    }

    @Override
    public BreinEngineType getRestEngineType(BreinEngineType engine) {
        return null;
    }

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void invokeRequest(final BreinConfig config, final BreinBase data, final ICallback<BreinResult> callback) {

        // validate the input objects
        BreinUtil.validate(data);

        final String fullUrl = BreinUtil.getFullyQualifiedUrl(data);
        final String requestBody = BreinUtil.getRequestBody(data);
        Log.d(TAG, "InvokeRequest - request is:  " + requestBody);
        final int connectionTimeout = (int) Breinify.getConfig().getConnectionTimeout();
        final int readTimeout = (int) Breinify.getConfig().getSocketTimeout();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    final URL url = new URL(fullUrl);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectionTimeout);
                    conn.setRequestMethod(POST_METHOD);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");

                    final byte[] data = requestBody.getBytes();
                    conn.setFixedLengthStreamingMode(data.length);
                    conn.getOutputStream().write(data);
                    conn.getOutputStream().flush();

                    final int response = conn.getResponseCode();
                    Log.d(TAG, "InvokeRequest - response is:  " + conn.getResponseMessage());
                    BreinResult breinResponse = null;
                    if (response == HttpURLConnection.HTTP_OK) {
                        final StringBuilder jsonResponse = new StringBuilder();
                        final InputStream mInputStream = conn.getInputStream();

                        int i;
                        while ((i = mInputStream.read()) != -1) {
                            jsonResponse.append((char) i);
                        }

                        final Map<String, Object> mapResponse = new Gson().fromJson(jsonResponse.toString(), Map.class);
                        breinResponse = new BreinResult(mapResponse);
                    }

                    conn.disconnect();

                    if (callback != null) {
                        callback.callback(breinResponse);
                    }
                } catch (final Exception e) {

                    Log.d(TAG, "HttpUrlRestEngine exception is: " + e);
                    // throw new BreinException("REST rest call exception");
                }
            }
        }).start();
    }
}

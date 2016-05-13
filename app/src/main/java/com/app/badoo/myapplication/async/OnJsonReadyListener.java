package com.app.badoo.myapplication.async;

import org.json.JSONArray;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public interface OnJsonReadyListener {
    /**
     * This method is called on the background thread.
     * @param jsonObject
     */
    void onJsonReady(JSONArray jsonObject);

    /**
     * This method is called on the main thread.
     * @param jsonObject
     */
    void onPostExecute(JSONArray jsonObject);
}

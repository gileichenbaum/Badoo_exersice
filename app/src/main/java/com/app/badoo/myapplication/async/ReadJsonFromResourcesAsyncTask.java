package com.app.badoo.myapplication.async;

import android.content.Context;
import android.os.AsyncTask;

import com.app.badoo.myapplication.utils.JsonUtils;

import org.json.JSONArray;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class ReadJsonFromResourcesAsyncTask extends AsyncTask<Context, Integer, JSONArray> {

    private final OnJsonReadyListener mOnJsonReadyListener;
    private final int mJsonResourceId;

    public ReadJsonFromResourcesAsyncTask(final int jsonResourceId, final OnJsonReadyListener jsonReadyListener) {
        mOnJsonReadyListener = jsonReadyListener;
        mJsonResourceId = jsonResourceId;
    }

    @Override
    protected JSONArray doInBackground(final Context... params) {
        final JSONArray jsonObject = JsonUtils.readJsonFromResources(params[0], mJsonResourceId);

        if (mOnJsonReadyListener != null) {
            mOnJsonReadyListener.onJsonReady(jsonObject);
        }
        return jsonObject;
    }

    protected void onPostExecute(final JSONArray jsonObject) {
        super.onPostExecute(jsonObject);

        if (mOnJsonReadyListener != null) {
            mOnJsonReadyListener.onPostExecute(jsonObject);
        }
    }
}



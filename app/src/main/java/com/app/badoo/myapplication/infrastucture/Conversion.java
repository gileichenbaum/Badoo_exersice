package com.app.badoo.myapplication.infrastucture;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.MissingFormatArgumentException;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class Conversion {

    private final static String JSON_KEY_FROM = "from";//:"USD"
    private final static String JSON_KEY_RATE = "rate";//:"0.77"
    private final static String JSON_KEY_TO = "to";//"GBP"

    public final String mFrom;
    public final String mTo;
    public final double mRate;

    public Conversion(final String from, final String to, final double rate) {
        mFrom = from;
        mTo = to;
        mRate = rate;
    }

    public static Conversion fromJson(final JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new NullPointerException("Can't make conversion object from null json object");
        }

        final String from = jsonObject.optString(JSON_KEY_FROM,null);
        if (TextUtils.isEmpty(from)) {
            throw new MissingFormatArgumentException("Can't make conversion, json has missing [from] field");
        }

        final String to = jsonObject.optString(JSON_KEY_TO,null);
        if (TextUtils.isEmpty(to)) {
            throw new MissingFormatArgumentException("Can't make conversion, json has missing [to] field");
        }

        final Double rate = jsonObject.optDouble(JSON_KEY_RATE,-1);
        if (rate < 0) {
            throw new MissingFormatArgumentException("Can't make conversion, json has missing [rate] field");
        }

        return new Conversion(from,to,rate);
    }
}

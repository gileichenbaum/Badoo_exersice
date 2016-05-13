package com.app.badoo.myapplication.infrastucture;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class ConversionList {

    private static final String TAG = ConversionList.class.getSimpleName();
    private final HashMap<String,Double> mConvertToMap = new HashMap<>();
    private final String mFrom;

    public ConversionList(final String from) {
        mFrom = from;
        mConvertToMap.put(from,1.0);
    }

    public void add(final Conversion conversion) {
        mConvertToMap.put(conversion.mTo,conversion.mRate);
    }

    public HashMap<String,Double> getConversions() {
        return mConvertToMap;
    }

    /**
     * converts the value using the rate.
     * @param to the currency to convert to
     * @param valueToConvert value to convert
     * @return the converted value or the default value if not found.
     */
    public double convert(final String to, final double valueToConvert) {
        final Double rate = mConvertToMap.get(to);
        if (rate == null) {
            final Map.Entry<String, Double> firstEntry = mConvertToMap.entrySet().iterator().next();
            Log.i(TAG,"no value for " + to + " in [" + mFrom + "] rates , returning conversion for " + firstEntry.getKey());
            return firstEntry.getValue() * valueToConvert;
        }
        return valueToConvert * rate;
    }
}

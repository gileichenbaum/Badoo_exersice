package com.app.badoo.myapplication.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.app.badoo.myapplication.infrastucture.Conversion;
import com.app.badoo.myapplication.infrastucture.ConversionList;
import com.app.badoo.myapplication.infrastucture.Sku;
import com.app.badoo.myapplication.infrastucture.Transaction;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static JSONArray readJsonFromResources(final Context context, int resourceId) {

        final InputStream is = context.getResources().openRawResource(resourceId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Log.e(TAG, "Fatal error while reading input", e);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                Log.e(TAG, "Error while closing connection input", e);
            }
        }

        if (writer == null) return null;

        final String jsonString = writer.toString();

        if (!TextUtils.isEmpty(jsonString)) {
            try {
                final JSONArray json = new JSONArray(jsonString);
                return json;
            } catch (Exception e) {
                Log.i(TAG, "Fatal error converting string to json");
                Log.i(TAG, jsonString, e);
            }
        }

        return null;
    }

    public static Map<String, Sku> getTransactionsMap(final JSONArray jsonArray, final Map<String, ConversionList> conversionMap, final String conversionCurrency) {

        final Map<String, Sku> skuTransactions = new TreeMap<>();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    final Transaction transaction = Transaction.fromJson(jsonArray.getJSONObject(i));

                    final ConversionList conversionList = null == conversionMap ? null : conversionMap.get(transaction.mCurrency);
                    if (conversionList != null) {
                        transaction.setConvertedAmount(conversionList.convert(conversionCurrency, transaction.mAmount));
                    }

                    Sku sku = skuTransactions.get(transaction.mSku);

                    if (sku == null) {
                        sku = new Sku(transaction.mSku);
                    }

                    sku.add(transaction);
                    skuTransactions.put(transaction.mSku, sku);
                } catch (Exception e) {
                    Log.i(TAG, "error making transaction item", e);
                }
            }
        }
        return skuTransactions;
    }

    public static Map<String, ConversionList> parseRatesJson(final JSONArray jsonArray) {

        final Map<String, ConversionList> conversionMap = new HashMap<>();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    final Conversion conversion = Conversion.fromJson(jsonArray.getJSONObject(i));

                    ConversionList conversionList = conversionMap.get(conversion.mFrom);

                    if (conversionList == null) {
                        conversionList = new ConversionList(conversion.mFrom);
                    }

                    conversionList.add(conversion);
                    conversionMap.put(conversion.mFrom, conversionList);
                } catch (Exception e) {
                    Log.i(TAG, "error making conversion item", e);
                }
            }
        }

        return conversionMap;
    }
}

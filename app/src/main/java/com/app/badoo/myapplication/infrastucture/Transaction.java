package com.app.badoo.myapplication.infrastucture;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.MissingFormatArgumentException;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class Transaction implements Parcelable{


    public final static String JSON_KEY_SKU = "sku";
    public final static String JSON_KEY_AMOUNT = "amount";
    public final static String JSON_KEY_CURRENCY = "currency";

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public final String mSku;
    public final double mAmount;
    public final String mCurrency;
    double mConvertedAmount;

    public Transaction(final String sku, final double amount, final String currency) {
        mSku = sku;
        mAmount = amount;
        mCurrency = currency;
    }

    protected Transaction(Parcel in) {
        mSku = in.readString();
        mAmount = in.readDouble();
        mCurrency = in.readString();
        mConvertedAmount = in.readDouble();
    }

    public static Transaction fromJson(final JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new NullPointerException("Can't make transaction object from null json object");
        }

        final String sku = jsonObject.optString(JSON_KEY_SKU,null);
        if (TextUtils.isEmpty(sku)) {
            throw new MissingFormatArgumentException("Can't make transaction object with no sku");
        }
        return new Transaction(sku,jsonObject.optDouble(JSON_KEY_AMOUNT,0),jsonObject.optString(JSON_KEY_CURRENCY,null));
    }

    @Override
    public String toString() {
        return "sku=" + mSku + ", amount=" + mAmount + ", currency=" + mCurrency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mSku);
        dest.writeDouble(mAmount);
        dest.writeString(mCurrency);
        dest.writeDouble(mConvertedAmount);
    }

    public void setConvertedAmount(final double convertedAmount) {
        mConvertedAmount = convertedAmount;
    }

    public double getConvertedAmount() {
        return mConvertedAmount;
    }
}

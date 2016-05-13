package com.app.badoo.myapplication.infrastucture;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class Sku implements Parcelable{


    public static final Creator<Sku> CREATOR = new Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel in) {
            return new Sku(in);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };

    public final String mSku;
    public final ArrayList<Transaction> mTransactions = new ArrayList<Transaction>();
    private double mTotalTransactionSum;

    public Sku(final String sku) {
        mSku = sku;
    }

    protected Sku(Parcel in) {
        mSku = in.readString();
        mTotalTransactionSum = in.readDouble();
        in.readTypedList(mTransactions,Transaction.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mSku);
        dest.writeDouble(mTotalTransactionSum);
        dest.writeTypedList(mTransactions);
    }

    public void add(final Transaction transaction) {
        mTransactions.add(transaction);
        mTotalTransactionSum =+ transaction.mConvertedAmount;
    }

    public int getTransactionCount() {
        return mTransactions.size();
    }
}

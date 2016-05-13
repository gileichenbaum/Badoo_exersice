package com.app.badoo.myapplication.ui.adapters;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class SkuTransactionsListAdapter extends TransactionsListAdapter {

    public static final DecimalFormat sGBPDecimalFormat =  new DecimalFormat("£#.00");
    private static final NumberFormat sDecimalFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private double[] mAmounts;
    private double[] mConvertedAmounts;
    private String[] mCurrencies;

    public SkuTransactionsListAdapter(final Context context) {
        super(context);
    }

    @Override
    public Object getItem(final int position) {
        return null;
    }

    @Override
    public int getCount() {
        return null == mAmounts ? 0 : mAmounts.length;
    }


    @Override
    protected void bindView(final int position, final TransactionsListAdapter.TransactionCountViewHolder viewHolder) {
        sDecimalFormat.setCurrency(Currency.getInstance(mCurrencies[position]));
        viewHolder.setup(sDecimalFormat.format(mAmounts[position]),sGBPDecimalFormat.format(mConvertedAmounts[position]));
    }

    public void setSkuMap(final double[] skuStrings, final double[] skuTotalTransactions, final String[] currencies) {
        mAmounts = skuStrings;
        mConvertedAmounts = skuTotalTransactions;
        mCurrencies = currencies;
        notifyDataSetChanged();
    }
}

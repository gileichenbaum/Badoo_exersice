package com.app.badoo.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.app.badoo.myapplication.R;
import com.app.badoo.myapplication.infrastucture.Sku;
import com.app.badoo.myapplication.infrastucture.Transaction;
import com.app.badoo.myapplication.ui.adapters.SkuTransactionsListAdapter;

import java.util.ArrayList;

public class DetailsActivityFragment extends Fragment {

    private Sku mSku;
    private TextView mTextViewTotalTransactionAmount;
    private ListView mListViewTransactions;
    private SkuTransactionsListAdapter mListAdapter;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_details, container, false);
        mTextViewTotalTransactionAmount = (TextView) contentView.findViewById(R.id.detail_screen_sku_transaction_sum);
        mListViewTransactions = (ListView) contentView.findViewById(R.id.detail_screen_sku_transactions_list_view);

        mListAdapter = new SkuTransactionsListAdapter(getContext());
        mListViewTransactions.setAdapter(mListAdapter);
        return contentView;
    }

    public void setSku(final Sku sku) {
        mSku = sku;

        final ArrayList<Transaction> transactions = sku.mTransactions;
        final int size = transactions.size();
        final double[] amounts = new double[size];
        final double[] convertedAmounts = new double[size];
        final String[] currencies = new String[size];

        double total = 0;

        for (int i = 0; i < size; i++) {
            final Transaction transaction = transactions.get(i);
            amounts[i] = transaction.mAmount;
            convertedAmounts[i] = transaction.getConvertedAmount();
            currencies[i] = transaction.mCurrency;
            total += convertedAmounts[i];
        }

        mListAdapter.setSkuMap(amounts, convertedAmounts, currencies);

        mTextViewTotalTransactionAmount.setText(getContext().getString(R.string.total_transactions,SkuTransactionsListAdapter.sGBPDecimalFormat.format(total)));
    }
}

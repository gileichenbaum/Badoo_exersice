package com.app.badoo.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.badoo.myapplication.R;

/**
 * Created by GIL on 13/05/2016 for My Application.
 */
public class TransactionsListAdapter extends ArrayAdapter {

    protected final LayoutInflater mLayoutInflater;
    protected String[] mSkuStrings;
    private int[] mSkuTotalTransactions;

    public TransactionsListAdapter(final Context context) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sku_item_layout,parent,false);
        }

        TransactionCountViewHolder viewHolder = (TransactionCountViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new TransactionCountViewHolder(convertView);
        }

        bindView(position, viewHolder);

        return convertView;
    }

    protected void bindView(final int position, final TransactionCountViewHolder viewHolder) {
        viewHolder.setup(mSkuStrings[position],viewHolder.mSubtitleText.getContext().getResources().getQuantityString(R.plurals.transactions, mSkuTotalTransactions[position],  mSkuTotalTransactions[position]));
    }

    public void setSkuMap(final String[] skuStrings,final int[] skuTotalTransactions) {
        mSkuStrings = skuStrings;
        mSkuTotalTransactions = skuTotalTransactions;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(final int position) {
        return null == mSkuStrings ? null : mSkuStrings[position];
    }

    @Override
    public int getCount() {
        return null == mSkuStrings ? 0 : mSkuStrings.length;
    }

    public class TransactionCountViewHolder {
        private final TextView mTitleText;
        private final TextView mSubtitleText;

        public TransactionCountViewHolder(final View view) {
            mTitleText = (TextView) view.findViewById(R.id.list_row_title);
            mSubtitleText = (TextView) view.findViewById(R.id.list_row_subtitle);
        }

        public void setup(final String title,final String subtitle) {
            mTitleText.setText(title);
            mSubtitleText.setText(subtitle);
        }
    }
}

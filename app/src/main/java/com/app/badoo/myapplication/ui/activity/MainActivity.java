package com.app.badoo.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.badoo.myapplication.R;
import com.app.badoo.myapplication.async.OnJsonReadyListener;
import com.app.badoo.myapplication.async.ReadJsonFromResourcesAsyncTask;
import com.app.badoo.myapplication.infrastucture.ConversionList;
import com.app.badoo.myapplication.infrastucture.Sku;
import com.app.badoo.myapplication.ui.adapters.TransactionsListAdapter;
import com.app.badoo.myapplication.utils.JsonUtils;

import org.json.JSONArray;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    public static final String BUNDLE_KEY_SKU = "skulist";
    private final static String GBP = "GBP";
    private static final String SAVED_INSTANCE_TRANSACTIONS = "transactions";

    private ListView mListViewTransactions;
    private TransactionsListAdapter mListViewAdapter;
    private ReadJsonFromResourcesAsyncTask mTransactionsTask;
    private ReadJsonFromResourcesAsyncTask mRatesTask;
    private Map<String, Sku> mSkuMap;
    private Map<String, ConversionList> mConversionsMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_TRANSACTIONS)) {

            mSkuMap = new TreeMap<>();

            final Bundle transactions = savedInstanceState.getBundle(SAVED_INSTANCE_TRANSACTIONS);

            for (String sku : transactions.keySet()) {
                mSkuMap.put(sku, (Sku) transactions.getParcelable(sku));
            }

            setupUI();

        } else {

            mRatesTask = new ReadJsonFromResourcesAsyncTask(R.raw.rates, new OnJsonReadyListener() {
                @Override
                public void onJsonReady(final JSONArray jsonArray) {
                    mConversionsMap = JsonUtils.parseRatesJson(jsonArray);
                    getTransactionsAsync();
                }

                @Override
                public void onPostExecute(final JSONArray jsonArray) {
                }
            });

            AsyncTaskCompat.executeParallel(mRatesTask, this);
        }

        mListViewTransactions = (ListView) findViewById(R.id.transactions_list_view);
        mListViewAdapter = new TransactionsListAdapter(this);
        mListViewTransactions.setAdapter(mListViewAdapter);

        mListViewTransactions.setOnItemClickListener(this);

        setTitle(getString(R.string.activity_main_title));
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        final Bundle transactions = new Bundle();
        for (Map.Entry<String, Sku> entry : mSkuMap.entrySet()) {
            transactions.putParcelable(entry.getKey(),entry.getValue());
        }
        outState.putBundle(SAVED_INSTANCE_TRANSACTIONS,transactions);
    }

    private void getTransactionsAsync() {
        if (mTransactionsTask == null) {
            mTransactionsTask = new ReadJsonFromResourcesAsyncTask(R.raw.transactions, new OnJsonReadyListener() {
                @Override
                public void onJsonReady(final JSONArray jsonArray) {
                }

                @Override
                public void onPostExecute(final JSONArray jsonArray) {
                    parseSkuJson(jsonArray);
                }
            });
            AsyncTaskCompat.executeParallel(mTransactionsTask, this);
        }
    }

    private void parseSkuJson(final JSONArray jsonArray) {
        mSkuMap = JsonUtils.getTransactionsMap(jsonArray, mConversionsMap, GBP);
        setupUI();
    }

    private void setupUI() {

        if (mSkuMap == null) {
            mListViewAdapter.setSkuMap(null, null);
            return;
        }

        final int size = mSkuMap.size();
        final String[] skus = new String[size];
        final int[] skuTransactionsCount = new int[size];
        final Iterator<Sku> iterator = mSkuMap.values().iterator();

        for (int i = 0; i < size; i++) {
            final Sku entry = iterator.next();
            skus[i] = entry.mSku;
            skuTransactionsCount[i] = entry.getTransactionCount();
        }

        mListViewAdapter.setSkuMap(skus, skuTransactionsCount);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        final String sku = (String) parent.getAdapter().getItem(position);
        final Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(BUNDLE_KEY_SKU, mSkuMap.get(sku));
        parent.getContext().startActivity(intent);
    }
}

package com.app.badoo.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.badoo.myapplication.R;
import com.app.badoo.myapplication.infrastucture.Sku;
import com.app.badoo.myapplication.ui.fragment.DetailsActivityFragment;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        if (intent != null && intent.hasExtra(MainActivity.BUNDLE_KEY_SKU)) {
            final Sku sku = intent.getParcelableExtra(MainActivity.BUNDLE_KEY_SKU);

            final DetailsActivityFragment detailsActivityFragment = (DetailsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            detailsActivityFragment.setSku(sku);

            setTitle(getString(R.string.details_fragment_title, sku.mSku));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

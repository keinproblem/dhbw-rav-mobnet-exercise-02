package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MainActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiManager wifiManager;
    private WifiInfoDataListener wifiInfoDataListener = new WifiInfoDataListener();
    private boolean isBroadcastReceiverRegistered = false;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                //WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                if (wifiManager != null)
                    wifiInfoDataListener.propagate(WifiInfoData.fromWifiInfo(wifiManager.getConnectionInfo()));
            }
        }
    };
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        this.intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        this.intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        this.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //initial load
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!this.isBroadcastReceiverRegistered) {
            this.isBroadcastReceiverRegistered = true;
            Log.d("mainActivity", "onResume: registering receiver");
            this.registerReceiver(this.broadcastReceiver, this.intentFilter);
        }

        wifiInfoDataListener.propagate(WifiInfoData.fromWifiInfo(wifiManager.getConnectionInfo()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isBroadcastReceiverRegistered) {
            Log.d("mainActivity", "onPause: unregistering receiver");
            this.isBroadcastReceiverRegistered = false;
            this.unregisterReceiver(this.broadcastReceiver);
        }
    }

    public WifiInfoDataListener getWifiInfoDataListener() {
        return wifiInfoDataListener;
    }

    private static final class WifiInfoDataListener extends ConcurrentLinkedQueue<Updatable<WifiInfoData>> {
        public void propagate(final WifiInfoData wifiInfoData) {
            Log.d("", "propagate: " + this.size() + " of " + this.toString());
            if (wifiInfoData == null)
                throw new IllegalArgumentException("wifiInfoData was null.");
            for (Updatable<WifiInfoData> u : this) {
                if (u != null)
                    u.call(wifiInfoData);
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public MainActivity mainActivity;

        public SectionsPagerAdapter(FragmentManager fm, final MainActivity mainActivity) {
            super(fm);
            this.mainActivity = mainActivity;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            WifiInfoFragment wifiInfoData = WifiInfoFragment.newInstance();
            this.mainActivity.getWifiInfoDataListener().add(wifiInfoData);
            return wifiInfoData;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}

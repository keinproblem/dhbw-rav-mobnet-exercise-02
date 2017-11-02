package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiManager wifiManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                String wifiState = "Unavailable";
                String fiveGhzSupport = "Unknown";
                switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        wifiState = "Wifi Enabled";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            fiveGhzSupport = wifiManager.is5GHzBandSupported() ? "Yes" : "No";
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        wifiState = "Wifi Enabling";
                        //TODO; check if available here
                        //wifiInfo = wifiManager.getConnectionInfo();

                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        wifiState = "Wifi Disabling";

                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        wifiState = "Wifi Disabled";

                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        wifiState = "Wifi Unknown";
                        break;
                }
                propagateWifiInfoData(WifiInfoData.fromWifiInfo(wifiManager.getConnectionInfo(), wifiState, fiveGhzSupport), WifiInfoFragment.CUSTOM_WIFI_DATA_BROADCAST_OPERATION, WifiInfoFragment.CUSTOM_WIFI_DATA_BROADCAST_OPERATION_MESSAGE);
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
        Log.d("mainActivity", "onResume: registering receiver");
        this.registerReceiver(this.broadcastReceiver, this.intentFilter);
    }

    private void propagateWifiInfoData(final WifiInfoData wifiInfoData, final String operationName, final String operationMessageName) {
        final Intent updateWifiViewIntent = new Intent(WifiInfoFragment.CUSTOM_WIFI_DATA_BROADCAST_OPERATION);
        updateWifiViewIntent.putExtra(WifiInfoFragment.CUSTOM_WIFI_DATA_BROADCAST_OPERATION_MESSAGE, wifiInfoData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateWifiViewIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mainActivity", "onPause: unregistering receiver");
        this.unregisterReceiver(this.broadcastReceiver);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final short PAGE_COUNT = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Toast.makeText(MainActivity.this, "Current pos: " + position, Toast.LENGTH_SHORT).show();
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                WifiInfoFragment wifiInfoData = WifiInfoFragment.newInstance();
                return wifiInfoData;
            }
            if (position == 1) {
                WifiBroadcastChatFragment wifiBroadcastChatFragment = WifiBroadcastChatFragment.newInstance();
                return wifiBroadcastChatFragment;
            }
            return WifiInfoFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return PAGE_COUNT;
        }
    }
}

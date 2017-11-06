package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Noli on 31/10/2017.
 */

public class WifiInfoFragment extends Fragment {
    public static final String CUSTOM_WIFI_DATA_BROADCAST_OPERATION = "wifi_data_update_broadcast_operation";
    public static final String CUSTOM_WIFI_DATA_BROADCAST_OPERATION_MESSAGE = CUSTOM_WIFI_DATA_BROADCAST_OPERATION + "_message";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    private TextView wifiStateTextView,
            fiveGhzSupportTextView,
            linkSpeedTextView,
            frequencyTextView,
            ipAddrTextView,
            bssidTextView,
            ssidTextView,
            apMacTextView,
            rssidTextView;

    private WifiInfoData wifiInfoData = null;

    private boolean isInitialized = false;

    private String wifiState = "Unknown";
    private String fiveGhzSupport = "Unknown";
    private WifiManager wifiManager = null;
    private IntentFilter intentFilter = null;

    private BroadcastReceiver wifiStateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()) || WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction())) {
                Toast.makeText(context, "frag: Received Broadcast " + intent.getAction(), Toast.LENGTH_SHORT).show();

                switch (wifiManager.getWifiState()) {
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
                updateTextViews(WifiInfoData.fromWifiInfo(wifiManager.getConnectionInfo(), wifiState, fiveGhzSupport));
            }
        }
    };
    public static WifiInfoFragment newInstance() {
        WifiInfoFragment fragment = new WifiInfoFragment();
        return fragment;
    }

    private void loadTextViewReferences(final View rootView) {
        this.wifiStateTextView = rootView.findViewById(R.id.wifiStateTextView);
        this.fiveGhzSupportTextView = rootView.findViewById(R.id.fiveGhzSupportTextView);
        this.linkSpeedTextView = rootView.findViewById(R.id.linkSpeedTextView);
        this.frequencyTextView = rootView.findViewById(R.id.frequencyTextView);
        this.ipAddrTextView = rootView.findViewById(R.id.ipAddrTextView);
        this.bssidTextView = rootView.findViewById(R.id.bssidTextView);
        this.ssidTextView = rootView.findViewById(R.id.ssidTextView);
        this.apMacTextView = rootView.findViewById(R.id.apMacTextView);
        this.rssidTextView = rootView.findViewById(R.id.rssidTextView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        this.loadTextViewReferences(rootView);

        this.isInitialized = true;
        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        //LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(this.updateViewBroadcastReceiver, new IntentFilter(CUSTOM_WIFI_DATA_BROADCAST_OPERATION));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getActivity().getApplicationContext().registerReceiver(wifiStateBroadcastReceiver, this.intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void updateTextViews(final WifiInfoData wifiInfoData) {
        Log.d("", "updateTextViews: updating");
        this.wifiStateTextView.setText(wifiInfoData.getWifiState());
        this.fiveGhzSupportTextView.setText(wifiInfoData.getWifiFiveGhzSupport());
        this.linkSpeedTextView.setText(wifiInfoData.getWifiLinkSpeed());
        this.frequencyTextView.setText(wifiInfoData.getWifiCurrentFrequency());
        this.ipAddrTextView.setText(wifiInfoData.getWifiCurrentIpAddress());
        this.bssidTextView.setText(wifiInfoData.getWifiBSSID());
        this.ssidTextView.setText(wifiInfoData.getWifiSSID());
        this.apMacTextView.setText(wifiInfoData.getWifiMAC());
        this.rssidTextView.setText(wifiInfoData.getWifiRSSID());
    }

    @Override
    public void onStop() {
        super.onStop();
        //LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(this.updateViewBroadcastReceiver);
        this.getActivity().getApplicationContext().unregisterReceiver(wifiStateBroadcastReceiver);
    }
}
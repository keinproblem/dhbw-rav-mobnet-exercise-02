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

/**
 * Created by Noli on 31/10/2017.
 */

public class WifiInfoFragment extends Fragment {
    private static final String TAG = WifiInfoFragment.class.getSimpleName();

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

    private IntentFilter intentFilter = null;
    private WifiManager wifiManager = null;

    private boolean isUiReady = false;


    private String wifiState = "Unknown";
    private String fiveGhzSupport = "Unknown";


    private final BroadcastReceiver wifiStateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "onReceive: received broadcast");
                    if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()) ||
                            WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction()) ||
                            WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                        Log.i(TAG, String.format("onReceive: intent Action: [%s}", intent.getAction()));
                        if (wifiManager == null) {
                            Log.d(TAG, "onReceive: wifiManager was null. The WifiManager might not be referenced yet.");
                            return;
                        }
                        switch (wifiManager.getWifiState()) {
                            case WifiManager.WIFI_STATE_ENABLED:
                                wifiState = getResources().getString(R.string.wifi_state_enabled);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    fiveGhzSupport = wifiManager.is5GHzBandSupported() ? "Yes" : "No";
                                }
                                break;
                            case WifiManager.WIFI_STATE_ENABLING:
                                wifiState = getResources().getString(R.string.wifi_state_enabling);
                                break;
                            case WifiManager.WIFI_STATE_DISABLING:
                                wifiState = getResources().getString(R.string.wifi_state_disabling);

                                break;
                            case WifiManager.WIFI_STATE_DISABLED:
                                wifiState = getResources().getString(R.string.wifi_state_disabled);
                                break;
                            case WifiManager.WIFI_STATE_UNKNOWN:
                                wifiState = getResources().getString(R.string.wifi_state_unknown);
                                break;
                        }
                        final WifiInfoData wifiInfoData = new WifiInfoData(wifiManager.getConnectionInfo(), wifiState, fiveGhzSupport);
                        Log.d(TAG, String.format("onReceive: wifiInfoData: [%s]", wifiInfoData));
                        updateTextViews(wifiInfoData);
                    }
                }
            });
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
        this.apMacTextView = rootView.findViewById(R.id.adapterMacTextView);
        this.rssidTextView = rootView.findViewById(R.id.rssiTextView);

        this.isUiReady = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        this.loadTextViewReferences(rootView);

        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        this.intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resuming");
        this.getActivity().getApplicationContext().registerReceiver(this.wifiStateBroadcastReceiver, this.intentFilter);
        Log.d(TAG, String.format("onStop: registerReceiver [%s], [%s]", this.wifiStateBroadcastReceiver.toString(), this.intentFilter.toString()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: pausing");
        //LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(this.updateViewBroadcastReceiver);
        Log.d(TAG, String.format("onStop: unregisterReceiver [%s]", this.wifiStateBroadcastReceiver.toString()));
        this.getActivity().getApplicationContext().unregisterReceiver(this.wifiStateBroadcastReceiver);
    }

    private void updateTextViews(final WifiInfoData wifiInfoData) {
        if (this.isUiReady) {
            Log.d(TAG, "updateTextViews: updating Ui Components");
            this.wifiStateTextView.setText(wifiInfoData.getWifiState());
            this.fiveGhzSupportTextView.setText(wifiInfoData.getWifiFiveGhzSupport());
            this.linkSpeedTextView.setText(wifiInfoData.getWifiLinkSpeed());
            this.frequencyTextView.setText(wifiInfoData.getWifiCurrentFrequency());
            this.ipAddrTextView.setText(wifiInfoData.getWifiCurrentIpAddress());
            this.bssidTextView.setText(wifiInfoData.getWifiBSSID());
            this.ssidTextView.setText(wifiInfoData.getWifiSSID());
            this.apMacTextView.setText(wifiInfoData.getWifiAdapterMac());
            this.rssidTextView.setText(wifiInfoData.getWifiRssi());
        } else {
            Log.d(TAG, "updateTextViews: Ui Update request while not ready. Request will be ignored.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
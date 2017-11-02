package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private TextView linkSpeedTextView;
    private BroadcastReceiver updateViewBroadcastReceiver;
    private boolean isInitialized = false;

    public WifiInfoFragment() {
        this.updateViewBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isInitialized) {
                    WifiInfoData wifiInfoData = null;
                    try {
                        wifiInfoData = (WifiInfoData) intent.getExtras().get(CUSTOM_WIFI_DATA_BROADCAST_OPERATION_MESSAGE);
                    } catch (ClassCastException cce) {
                        //FIXME insert TAG
                        Log.w("", "onReceive: ", cce);
                    }

                    if (wifiInfoData == null)
                        return;
                    updateTextViews(wifiInfoData);
                }
            }
        };
    }

    public static WifiInfoFragment newInstance() {
        WifiInfoFragment fragment = new WifiInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.linkSpeedTextView = rootView.findViewById(R.id.textView16);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(
                this.updateViewBroadcastReceiver, new IntentFilter("custom-event-name"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(
                this.updateViewBroadcastReceiver);
    }

    private void updateTextViews(final WifiInfoData wifiInfoData) {
        Log.d("", "updateTextViews: updating");
        this.linkSpeedTextView.setText(wifiInfoData.getWifiLinkSpeed());
    }
}
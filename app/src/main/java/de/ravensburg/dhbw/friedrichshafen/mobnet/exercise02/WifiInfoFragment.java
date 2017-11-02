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

public class WifiInfoFragment extends Fragment implements Updatable<WifiInfoData> {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    private TextView linkSpeedTextView;

    public static WifiInfoFragment newInstance() {
        WifiInfoFragment fragment = new WifiInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.linkSpeedTextView = rootView.findViewById(R.id.textView16);

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(new BroadcastReceiver() {
                                                                                  @Override
                                                                                  public void onReceive(Context context, Intent intent) {
                                                                                      updateTextViews((WifiInfoData) intent.getExtras().get("aaa"));
                                                                                  }
                                                                              },
                new IntentFilter("my-event"));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateTextViews(final WifiInfoData wifiInfoData) {
        Log.d("", "updateTextViews: updating");
        this.linkSpeedTextView.setText(wifiInfoData.getWifiLinkSpeed());
    }

    @Override
    public void call(final WifiInfoData wifiInfoData) {
        this.updateTextViews(wifiInfoData);
    }
}
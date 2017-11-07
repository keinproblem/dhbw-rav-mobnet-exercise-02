package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.net.wifi.WifiInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.format.Formatter;

import java.io.Serializable;

/**
 * Created by Noli on 31/10/2017.
 */

public class WifiInfoData implements Serializable {
    private static final String UNAVAILABLE_DATA_MESSAGE = "Unavailable";

    private final String wifiState;
    private final String wifiFiveGhzSupport;
    private final String wifiLinkSpeed;
    private final String wifiCurrentFrequency;
    private final String wifiCurrentIpAddress;
    private final String wifiBSSID;
    private final String wifiSSID;
    private final String wifiAdapterMac;
    private final String wifiRssi;

    public WifiInfoData(@NonNull final WifiInfo wifiInfo, @NonNull final String wifiState, @NonNull String wifiFiveGhzSupport) {
        this.wifiState = wifiState;
        this.wifiFiveGhzSupport = wifiFiveGhzSupport;
        this.wifiLinkSpeed = String.valueOf(wifiInfo.getLinkSpeed()) + ' ' + WifiInfo.LINK_SPEED_UNITS;
        this.wifiBSSID = String.valueOf(wifiInfo.getBSSID());
        //Received signal strength indication
        this.wifiRssi = String.valueOf(wifiInfo.getRssi());
        this.wifiSSID = String.valueOf(wifiInfo.getSSID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.wifiCurrentFrequency = String.valueOf(wifiInfo.getFrequency());
        } else {
            this.wifiCurrentFrequency = UNAVAILABLE_DATA_MESSAGE;
        }
        this.wifiCurrentIpAddress = Formatter.formatIpAddress(wifiInfo.getIpAddress());
        this.wifiAdapterMac = String.valueOf(wifiInfo.getMacAddress());
    }


    public static String getUnavailableDataMessage() {
        return UNAVAILABLE_DATA_MESSAGE;
    }


    public String getWifiState() {
        return wifiState;
    }

    public String getWifiFiveGhzSupport() {
        return wifiFiveGhzSupport;
    }

    public String getWifiLinkSpeed() {
        return wifiLinkSpeed;
    }

    public String getWifiCurrentFrequency() {
        return wifiCurrentFrequency;
    }

    public String getWifiCurrentIpAddress() {
        return wifiCurrentIpAddress;
    }

    public String getWifiBSSID() {
        return wifiBSSID;
    }

    public String getWifiRssi() {
        return wifiRssi;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public String getWifiAdapterMac() {
        return wifiAdapterMac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifiInfoData that = (WifiInfoData) o;

        if (!getWifiState().equals(that.getWifiState())) return false;
        if (!getWifiFiveGhzSupport().equals(that.getWifiFiveGhzSupport())) return false;
        if (!getWifiLinkSpeed().equals(that.getWifiLinkSpeed())) return false;
        if (!getWifiCurrentFrequency().equals(that.getWifiCurrentFrequency())) return false;
        if (!getWifiCurrentIpAddress().equals(that.getWifiCurrentIpAddress())) return false;
        if (!getWifiBSSID().equals(that.getWifiBSSID())) return false;
        if (!getWifiSSID().equals(that.getWifiSSID())) return false;
        if (!getWifiAdapterMac().equals(that.getWifiAdapterMac())) return false;
        return getWifiRssi().equals(that.getWifiRssi());
    }

    @Override
    public int hashCode() {
        int result = getWifiState().hashCode();
        result = 31 * result + getWifiFiveGhzSupport().hashCode();
        result = 31 * result + getWifiLinkSpeed().hashCode();
        result = 31 * result + getWifiCurrentFrequency().hashCode();
        result = 31 * result + getWifiCurrentIpAddress().hashCode();
        result = 31 * result + getWifiBSSID().hashCode();
        result = 31 * result + getWifiSSID().hashCode();
        result = 31 * result + getWifiAdapterMac().hashCode();
        result = 31 * result + getWifiRssi().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WifiInfoData{" +
                "wifiState='" + wifiState + '\'' +
                ", wifiFiveGhzSupport='" + wifiFiveGhzSupport + '\'' +
                ", wifiLinkSpeed='" + wifiLinkSpeed + '\'' +
                ", wifiCurrentFrequency='" + wifiCurrentFrequency + '\'' +
                ", wifiCurrentIpAddress='" + wifiCurrentIpAddress + '\'' +
                ", wifiBSSID='" + wifiBSSID + '\'' +
                ", wifiSSID='" + wifiSSID + '\'' +
                ", wifiAdapterMac='" + wifiAdapterMac + '\'' +
                ", wifiRssi='" + wifiRssi + '\'' +
                '}';
    }
}


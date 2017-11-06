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

    private transient final StringBuilder stringBuilder = new StringBuilder();
    private String wifiState;
    private String wifiFiveGhzSupport;
    private String wifiLinkSpeed;
    private String wifiCurrentFrequency;
    private String wifiCurrentIpAddress;
    private String wifiBSSID;
    private String wifiSSID;
    private String wifiAdapterMac;
    private String wifiRssi;



    public static WifiInfoData fromWifiInfo(@NonNull final WifiInfo wifiInfo, @NonNull final String wifiState, @NonNull String wifiFiveGhzSupport) {
        WifiInfoData wifiInfoData = new WifiInfoData();
        wifiInfoData.setWifiLinkSpeed(String.valueOf(wifiInfo.getLinkSpeed()) + Character.SPACE_SEPARATOR + WifiInfo.LINK_SPEED_UNITS);
        wifiInfoData.setWifiBSSID(String.valueOf(wifiInfo.getBSSID()));
        //Received signal strength indication
        wifiInfoData.setWifiRssi(String.valueOf(wifiInfo.getRssi()));
        wifiInfoData.setWifiSSID(String.valueOf(wifiInfo.getSSID()));
        wifiInfoData.setWifiState(wifiState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wifiInfoData.setWifiCurrentFrequency(String.valueOf(wifiInfo.getFrequency()));
        } else {
            wifiInfoData.setWifiCurrentFrequency(UNAVAILABLE_DATA_MESSAGE);
        }
        wifiInfoData.setWifiCurrentIpAddress(Formatter.formatIpAddress(wifiInfo.getIpAddress()));
        wifiInfoData.setWifiAdapterMac(String.valueOf(wifiInfo.getMacAddress()));
        wifiInfoData.setWifiFiveGhzSupport(wifiFiveGhzSupport);
        return wifiInfoData;
    }


    public static String getUnavailableDataMessage() {
        return UNAVAILABLE_DATA_MESSAGE;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public String getWifiState() {
        return wifiState;
    }

    public WifiInfoData setWifiState(String wifiState) {
        this.wifiState = wifiState;
        return this;
    }

    public String getWifiFiveGhzSupport() {
        return wifiFiveGhzSupport;
    }

    public WifiInfoData setWifiFiveGhzSupport(String wifiFiveGhzSupport) {
        this.wifiFiveGhzSupport = wifiFiveGhzSupport;
        return this;
    }

    public String getWifiLinkSpeed() {
        return wifiLinkSpeed;
    }

    public WifiInfoData setWifiLinkSpeed(String wifiLinkSpeed) {
        this.wifiLinkSpeed = wifiLinkSpeed;
        return this;
    }

    public String getWifiCurrentFrequency() {
        return wifiCurrentFrequency;
    }

    public WifiInfoData setWifiCurrentFrequency(String wifiCurrentFrequency) {
        this.wifiCurrentFrequency = wifiCurrentFrequency;
        return this;
    }

    public String getWifiCurrentIpAddress() {
        return wifiCurrentIpAddress;
    }

    public WifiInfoData setWifiCurrentIpAddress(String wifiCurrentIpAddress) {
        this.wifiCurrentIpAddress = wifiCurrentIpAddress;
        return this;
    }

    public String getWifiBSSID() {
        return wifiBSSID;
    }

    public WifiInfoData setWifiBSSID(String wifiBSSID) {
        this.wifiBSSID = wifiBSSID;
        return this;
    }

    public String getWifiRssi() {
        return wifiRssi;
    }

    public WifiInfoData setWifiRssi(String wifiRssi) {
        this.wifiRssi = wifiRssi;
        return this;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public WifiInfoData setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
        return this;
    }

    public String getWifiAdapterMac() {
        return wifiAdapterMac;
    }

    public WifiInfoData setWifiAdapterMac(String wifiAdapterMac) {
        this.wifiAdapterMac = wifiAdapterMac;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifiInfoData that = (WifiInfoData) o;

        if (getStringBuilder() != null ? !getStringBuilder().equals(that.getStringBuilder()) : that.getStringBuilder() != null)
            return false;
        if (!getWifiState().equals(that.getWifiState())) return false;
        if (getWifiFiveGhzSupport() != null ? !getWifiFiveGhzSupport().equals(that.getWifiFiveGhzSupport()) : that.getWifiFiveGhzSupport() != null)
            return false;
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
        int result = getStringBuilder() != null ? getStringBuilder().hashCode() : 0;
        result = 31 * result + getWifiState().hashCode();
        result = 31 * result + (getWifiFiveGhzSupport() != null ? getWifiFiveGhzSupport().hashCode() : 0);
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
                "stringBuilder=" + stringBuilder +
                ", wifiState='" + wifiState + '\'' +
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


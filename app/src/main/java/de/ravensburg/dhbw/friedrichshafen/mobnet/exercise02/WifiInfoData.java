package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;

import android.net.wifi.WifiInfo;

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
    private String wifiRSSID;
    private String wifiSSID;
    private String wifiMAC;


    public static WifiInfoData fromWifiInfo(final WifiInfo wifiInfo) {
        WifiInfoData wifiInfoData = new WifiInfoData();
        wifiInfoData.setWifiLinkSpeed(String.valueOf(wifiInfo.getLinkSpeed()) + Character.SPACE_SEPARATOR + WifiInfo.LINK_SPEED_UNITS);
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

    public String getWifiRSSID() {
        return wifiRSSID;
    }

    public WifiInfoData setWifiRSSID(String wifiRSSID) {
        this.wifiRSSID = wifiRSSID;
        return this;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public WifiInfoData setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
        return this;
    }

    public String getWifiMAC() {
        return wifiMAC;
    }

    public WifiInfoData setWifiMAC(String wifiMAC) {
        this.wifiMAC = wifiMAC;
        return this;
    }
}


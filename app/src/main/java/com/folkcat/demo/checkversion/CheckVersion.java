package com.folkcat.demo.checkversion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tamas on 2016/9/27.
 */
public class CheckVersion {
    @SerializedName("VersionCode")
    private int serverVersionCode;
    @Expose
    private int localVersionCode;
    @SerializedName("NotSupportBefore")
    private int notSupportBefore;
    @SerializedName("VersionName")
    private String versionName;
    @SerializedName("NewFeather")
    private String newFeather;
    @SerializedName("Url")
    private String url;

    public int getServerVersionCode() {
        return serverVersionCode;
    }

    public void setServerVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
    }

    public int getLocalVersionCode() {
        return localVersionCode;
    }

    public void setLocalVersionCode(int localVersionCode) {
        this.localVersionCode = localVersionCode;
    }

    public int getNotSupportBefore() {
        return notSupportBefore;
    }

    public void setNotSupportBefore(int notSupportBefore) {
        this.notSupportBefore = notSupportBefore;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getNewFeather() {
        return newFeather;
    }

    public void setNewFeather(String newFeather) {
        this.newFeather = newFeather;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

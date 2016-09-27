package com.folkcat.demo.checkversion;

/**
 * Created by Tamas on 2016/9/27.
 */
public class CheckVersion {
    private int serverVersionCode;
    private int localVersionCode;
    private int notSupportBefore;
    private String versionName;
    private String newFeather;
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

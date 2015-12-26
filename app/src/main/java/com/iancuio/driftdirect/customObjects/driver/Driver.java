package com.iancuio.driftdirect.customObjects.driver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/19/2015.
 */
public class Driver implements Serializable {

    private String driverName;
    private int driverPicture;
    private String driverFanPageUrl;
    private String driverAge;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverPicture() {
        return driverPicture;
    }

    public void setDriverPicture(int driverPicture) {
        this.driverPicture = driverPicture;
    }

    public String getDriverFanPageUrl() {
        return driverFanPageUrl;
    }

    public void setDriverFanPageUrl(String driverFanPageUrl) {
        this.driverFanPageUrl = driverFanPageUrl;
    }

    public String getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(String driverAge) {
        this.driverAge = driverAge;
    }
}

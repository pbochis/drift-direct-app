package com.iancuio.driftdirect.customObjects.driver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/20/2015.
 */
public class DriverBiography implements Serializable {
    private String driverCountry;
    private String driverFlagUrl;
    private String driverTeam;
    private String driverDriftingFrom;
    private String driverPortfolio;
    private String driverDescription;

    public String getDriverCountry() {
        return driverCountry;
    }

    public void setDriverCountry(String driverCountry) {
        this.driverCountry = driverCountry;
    }

    public String getDriverFlagUrl() {
        return driverFlagUrl;
    }

    public void setDriverFlagUrl(String driverFlagUrl) {
        this.driverFlagUrl = driverFlagUrl;
    }

    public String getDriverTeam() {
        return driverTeam;
    }

    public void setDriverTeam(String driverTeam) {
        this.driverTeam = driverTeam;
    }

    public String getDriverDriftingFrom() {
        return driverDriftingFrom;
    }

    public void setDriverDriftingFrom(String driverDriftingFrom) {
        this.driverDriftingFrom = driverDriftingFrom;
    }

    public String getDriverPortfolio() {
        return driverPortfolio;
    }

    public void setDriverPortfolio(String driverPortfolio) {
        this.driverPortfolio = driverPortfolio;
    }

    public String getDriverDescription() {
        return driverDescription;
    }

    public void setDriverDescription(String driverDescription) {
        this.driverDescription = driverDescription;
    }
}

package com.iancuio.driftdirect.customObjects.driver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/20/2015.
 */
public class DriverRankings implements Serializable {

    private int driverRanking;
    private int driverRoundNumber;
    private int driverRoundPoints;
    private int driverTotalPoints;
    private int driverLocation;
    private int driverRoundDate;

    public int getDriverRanking() {
        return driverRanking;
    }

    public void setDriverRanking(int driverRanking) {
        this.driverRanking = driverRanking;
    }

    public int getDriverRoundNumber() {
        return driverRoundNumber;
    }

    public void setDriverRoundNumber(int driverRoundNumber) {
        this.driverRoundNumber = driverRoundNumber;
    }

    public int getDriverRoundPoints() {
        return driverRoundPoints;
    }

    public void setDriverRoundPoints(int driverRoundPoints) {
        this.driverRoundPoints = driverRoundPoints;
    }

    public int getDriverTotalPoints() {
        return driverTotalPoints;
    }

    public void setDriverTotalPoints(int driverTotalPoints) {
        this.driverTotalPoints = driverTotalPoints;
    }

    public int getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(int driverLocation) {
        this.driverLocation = driverLocation;
    }

    public int getDriverRoundDate() {
        return driverRoundDate;
    }

    public void setDriverRoundDate(int driverRoundDate) {
        this.driverRoundDate = driverRoundDate;
    }
}

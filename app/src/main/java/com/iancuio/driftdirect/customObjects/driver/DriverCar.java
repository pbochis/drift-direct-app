package com.iancuio.driftdirect.customObjects.driver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/20/2015.
 */
public class DriverCar implements Serializable {
    private String carMaker;
    private String carModel;
    private String carHorsePower;
    private String carEngineSpecs;
    private String carStearingAngleMods;
    private String carSuspensionMods;
    private String carWheels;
    private String carTires;
    private String carOther;

    public String getCarMaker() {
        return carMaker;
    }

    public void setCarMaker(String carMaker) {
        this.carMaker = carMaker;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarHorsePower() {
        return carHorsePower;
    }

    public void setCarHorsePower(String carHorsePower) {
        this.carHorsePower = carHorsePower;
    }

    public String getCarEngineSpecs() {
        return carEngineSpecs;
    }

    public void setCarEngineSpecs(String carEngineSpecs) {
        this.carEngineSpecs = carEngineSpecs;
    }

    public String getCarStearingAngleMods() {
        return carStearingAngleMods;
    }

    public void setCarStearingAngleMods(String carStearingAngleMods) {
        this.carStearingAngleMods = carStearingAngleMods;
    }

    public String getCarSuspensionMods() {
        return carSuspensionMods;
    }

    public void setCarSuspensionMods(String carSuspensionMods) {
        this.carSuspensionMods = carSuspensionMods;
    }

    public String getCarWheels() {
        return carWheels;
    }

    public void setCarWheels(String carWheels) {
        this.carWheels = carWheels;
    }

    public String getCarTires() {
        return carTires;
    }

    public void setCarTires(String carTires) {
        this.carTires = carTires;
    }

    public String getCarOther() {
        return carOther;
    }

    public void setCarOther(String carOther) {
        this.carOther = carOther;
    }
}

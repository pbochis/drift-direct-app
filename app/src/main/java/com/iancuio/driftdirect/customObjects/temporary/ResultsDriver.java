package com.iancuio.driftdirect.customObjects.temporary;

/**
 * Created by Soulstorm on 12/13/2015.
 */
public class ResultsDriver {
    private int rank;
    private Long profilePicture;
    private int points;
    private Long flag;
    private String name;
    private String carModel;
    private String carHP;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carName) {
        this.carModel = carName;
    }

    public String getCarHP() {
        return carHP;
    }

    public void setCarHP(String carHP) {
        this.carHP = carHP;
    }
}

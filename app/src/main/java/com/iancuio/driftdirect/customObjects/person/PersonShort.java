package com.iancuio.driftdirect.customObjects.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iancuio.driftdirect.customObjects.driver.DriverDetailsShort;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/29/2015.
 */
public class PersonShort implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String nick;
    private Long profilePicture;
    private String description;
    private DateTime birthDate;
    private DriverDetailsShort driverDetails;
    private long country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public DriverDetailsShort getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetailsShort driverDetails) {
        this.driverDetails = driverDetails;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}

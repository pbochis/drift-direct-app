package com.iancuio.driftdirect.customObjects.championship;

import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;
import com.iancuio.driftdirect.customObjects.news.News;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.RoundShort;
import com.iancuio.driftdirect.customObjects.sponsor.Sponsor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 11/19/2015.
 */
public class Championship implements Serializable {

    private long id;
    private String name;
    private ChampionshipRules rules;
    private String information;
    private String ticketsUrl;
    private Long backgroundImage;
    private Long logo;
    private List<RoundShort> rounds;
    private List<PersonShort> drivers;
    private List<ChampionshipJudgeParticipation> judges;
    private List<Sponsor> sponsors;
    private List<News> news;
    private PersonShort organizer;
    private boolean published;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public ChampionshipRules getRules() {
        return rules;
    }

    public void setRules(ChampionshipRules rules) {
        this.rules = rules;
    }

    public Long getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public List<RoundShort> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundShort> rounds) {
        this.rounds = rounds;
    }

    public List<PersonShort> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<PersonShort> drivers) {
        this.drivers = drivers;
    }

    public List<ChampionshipJudgeParticipation> getJudges() {
        return judges;
    }

    public void setJudges(List<ChampionshipJudgeParticipation> judges) {
        this.judges = judges;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public PersonShort getOrganizer() {
        return organizer;
    }

    public void setOrganizer(PersonShort organizer) {
        this.organizer = organizer;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}

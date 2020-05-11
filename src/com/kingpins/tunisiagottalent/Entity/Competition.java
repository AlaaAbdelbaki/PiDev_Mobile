package com.kingpins.tunisiagottalent.Entity;

import java.util.Date;


public class Competition {
    private int id;
    private String subject;
    private Date competition_date;
    private Date competition_end_date;
     public Competition(){};
    public Competition(String subject, Date competition_date, Date competition_end_date) {
        this.subject = subject;
        this.competition_date = competition_date;
        this.competition_end_date = competition_end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCompetition_date() {
        return competition_date;
    }

    public void setCompetition_date(Date competition_date) {
        this.competition_date = competition_date;
    }

    public Date getCompetition_end_date() {
        return competition_end_date;
    }

    public void setCompetition_end_date(Date competition_end_date) {
        this.competition_end_date = competition_end_date;
    }

    public Competition(int id, String subject, Date competition_date, Date competition_end_date) {
        this.id = id;
        this.subject = subject;
        this.competition_date = competition_date;
        this.competition_end_date = competition_end_date;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", competition_date=" + competition_date +
                ", competition_end_date=" + competition_end_date +
                '}';
    }
}

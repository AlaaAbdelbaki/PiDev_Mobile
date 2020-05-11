package com.kingpins.tunisiagottalent.Entity;

import java.util.Date;





public class competition_participant {
    private int id;
    private Competition competition;
    private User user;
    private Date participation_date;
    private video video;

    public competition_participant(int id, Competition competition_id, User user_id, Date participation_date, video video_id) {
        this.id = id;
        this.competition = competition_id;
        this.user = user_id;
        this.participation_date = participation_date;
        this.video = video_id;
    }

    public competition_participant(Competition competition_id, User user_id, Date participation_date, video video_id) {
        this.competition = competition_id;
        this.user = user_id;
        this.participation_date = participation_date;
        this.video = video_id;
    }

    public competition_participant() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCompetition_id() {
        return competition.getId();
    }

    public void setCompetition_id(Competition competition_id) {
        this.competition = competition_id;
    }

    public String getUser_id() {
        return user.getUsername();
    }

    public void setUser_id(User user_id) {
        this.user = user_id;
    }

    public Date getParticipation_date() {
        return participation_date;
    }

    public void setParticipation_date(Date participation_date) {
        this.participation_date = participation_date;
    }

    public video getVideo_id() {
        return video;
    }

    public void setVideo_id(video video_id) {
        this.video = video_id;
    }

    @Override
    public String toString() {
        return "competition_participant{" +
                "id=" + id +
                ", competition_id=" + competition +
                ", user_id=" + user +
                ", participation_date=" + participation_date +
                ", video_id=" + video +
                '}';
    }
}

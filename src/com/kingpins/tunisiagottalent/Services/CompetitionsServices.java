/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.google.gson.Gson;
import com.kingpins.tunisiagottalent.Entity.Competition;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Entity.competition_participant;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Utils.Statics;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anis
 */
public class CompetitionsServices {

    List<Competition> comps;
    List<video> Videos;
    List<video> rank;
    List<competition_participant> participations;
    public static CompetitionsServices instance;
    private final ConnectionRequest con;

    public CompetitionsServices() {
        con = new ConnectionRequest();
    }

    public static CompetitionsServices getInstance() {
        if (instance == null) {
            instance = new CompetitionsServices();
        }
        return instance;
    }

    public List<Competition> CompetitionsList() {

        String url = Statics.BASE_URL + "/competitions";
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String str = new String(con.getResponseData());
                    comps = new ArrayList<>();
                    JSONParser j = new JSONParser();

                    Map<String, Object> CompMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                    List<Map<String, Object>> compList = (List<Map<String, Object>>) CompMap.get("root");
                    if (compList != null) {
                        for (Map<String, Object> map : compList) {
                            comps.add(parseComps(map));
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                con.removeResponseListener(this);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(con);

        return comps;
    }

    private Competition parseComps(Map<String, Object> map) {
        Competition c = new Competition();
        c.setId(((Double) map.get("id")).intValue());
        c.setSubject(map.get("subject").toString());
        c.setCompetition_date(new Date((((Double) ((Map<String, Object>) map.get("competitionDate")).get("timestamp")).longValue() * 1000)));
        c.setCompetition_end_date(new Date((((Double) ((Map<String, Object>) map.get("competitionEndDate")).get("timestamp")).longValue() * 1000)));

        return c;

    }

    public List<competition_participant> CompetitionParticipationList(Competition c) {

        String url = Statics.BASE_URL + "/competitions/details/" + c.getId();
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String str = new String(con.getResponseData());

                    participations = new ArrayList<>();
                    JSONParser j = new JSONParser();

                    Map<String, Object> ParticipationsMap = j.parseJSON(new CharArrayReader(str.toCharArray()));

                    List<Map<String, Object>> ParticipationsList = (List<Map<String, Object>>) ParticipationsMap.get("root");
                    if (ParticipationsList != null) {
                        for (Map<String, Object> map : ParticipationsList) {

                            participations.add(parseParticipations(map, c));
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                con.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return participations;
    }

    private competition_participant parseParticipations(Map<String, Object> map, Competition c) {
        competition_participant p = new competition_participant();
        p.setId(((Double) map.get("id")).intValue());
        p.setCompetition_id(c);
        p.setParticipation_date(new Date((((Double) ((Map<String, Object>) map.get("participationDate")).get("timestamp")).longValue() * 1000)));

        //User
        User u = new User();
        u.setId((int) (double) ((((Map<String, Object>) map.get("user")).get("id"))));
        u.setUsername(((((Map<String, Object>) map.get("user")).get("username"))).toString());
        u.setEmail(((((Map<String, Object>) map.get("user")).get("email"))).toString());
        u.setRole(((((Map<String, Object>) map.get("user")).get("roles"))).toString());
        if (((((Map<String, Object>) map.get("user")).get("birthday"))) != null) {
            u.setBirthday(new Date((((Double) ((Map<String, Object>) (((Map<String, Object>) map.get("user")).get("birthday"))).get("timestamp")).longValue() * 1000)));
        }
        if ((((Map<String, Object>) map.get("user")).get("profilePic")) != null) {
            u.setProfilePic((((Map<String, Object>) map.get("user")).get("profilePic")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("sexe")) != null) {
            u.setGender((((Map<String, Object>) map.get("user")).get("sexe")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("telephoneNumber")) != null) {
            u.setPhone_number((((Map<String, Object>) map.get("user")).get("telephoneNumber")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("adresse")) != null) {
            u.setAddress((((Map<String, Object>) map.get("user")).get("adresse")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("name")) != null) {
            u.setName((((Map<String, Object>) map.get("user")).get("name")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("firstName")) != null) {
            u.setLastName((((Map<String, Object>) map.get("user")).get("firstName")).toString());
        }
        if ((((Map<String, Object>) map.get("user")).get("bio")) != null) {
            u.setBio((((Map<String, Object>) map.get("user")).get("bio")).toString());
        }

        p.setUser_id(u);
        //video
        video v = new video();
        v.setId((int) (double) ((((Map<String, Object>) map.get("video")).get("id"))));
        v.setOwner(u);
        v.setPublish_date(new Date((((Double) ((Map<String, Object>) (((Map<String, Object>) map.get("video")).get("publishDate"))).get("timestamp")).longValue() * 1000)));
        v.setTitle((((Map<String, Object>) map.get("video")).get("title")).toString());
        v.setUrl((((Map<String, Object>) map.get("video")).get("url")).toString());
        Object m=(((Map<String, Object>) map.get("video")).get("votes"));
        List<Map<String,Double>> thisisnotok=(List<Map<String,Double>>) m;
        List<Integer> f=new ArrayList();
        
        for (Map<String,Double> entry : thisisnotok) {
            f.add((int)(double)entry.get("id"));
        }
        v.setVotes(f);
        p.setVideo_id(v);

        return p;
    }

    public List<video> CompetitionRanksList(Competition c) {

        String url = Statics.BASE_URL + "/competitions/ranks/" + c.getId();
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String str = new String(con.getResponseData());
                    rank = new ArrayList<>();
                    JSONParser j = new JSONParser();

                    Map<String, Object> ranksMap = j.parseJSON(new CharArrayReader(str.toCharArray()));

                    List<Map<String, Object>> ranksList = (List<Map<String, Object>>) ranksMap.get("root");
                    if (ranksList != null) {
                        for (Map<String, Object> map : ranksList) {
                            rank.add(parseRanks(map));
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                con.removeResponseListener(this);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(con);

        return rank;
    }

    private video parseRanks(Map<String, Object> map) {
        //video
        video v = new video();
        v.setId(((Double) map.get("id")).intValue());

        v.setPublish_date(new Date((((Double) ((Map<String, Object>) map.get("publishDate")).get("timestamp")).longValue() * 1000)));

        //User
        User u = new User();
        u.setId((int) (double) ((((Map<String, Object>) map.get("owner")).get("id"))));
        u.setUsername(((((Map<String, Object>) map.get("owner")).get("username"))).toString());
        u.setEmail(((((Map<String, Object>) map.get("owner")).get("email"))).toString());
        u.setRole(((((Map<String, Object>) map.get("owner")).get("roles"))).toString());
        if (((((Map<String, Object>) map.get("owner")).get("birthday"))) != null) {
            u.setBirthday(new Date((((Double) ((Map<String, Object>) (((Map<String, Object>) map.get("owner")).get("birthday"))).get("timestamp")).longValue() * 1000)));
        }
        if ((((Map<String, Object>) map.get("owner")).get("profilePic")) != null) {
            u.setProfilePic((((Map<String, Object>) map.get("owner")).get("profilePic")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("sexe")) != null) {
            u.setGender((((Map<String, Object>) map.get("owner")).get("sexe")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("telephoneNumber")) != null) {
            u.setPhone_number((((Map<String, Object>) map.get("owner")).get("telephoneNumber")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("adresse")) != null) {
            u.setAddress((((Map<String, Object>) map.get("owner")).get("adresse")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("name")) != null) {
            u.setName((((Map<String, Object>) map.get("owner")).get("name")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("firstName")) != null) {
            u.setLastName((((Map<String, Object>) map.get("owner")).get("firstName")).toString());
        }
        if ((((Map<String, Object>) map.get("owner")).get("bio")) != null) {
            u.setBio((((Map<String, Object>) map.get("owner")).get("bio")).toString());
        }

        v.setOwner(u);

        v.setTitle(map.get("title").toString());
        v.setUrl((map.get("url")).toString());
        Object m=map.get("votes");
        List<Map<String,Double>> thisisnotok=(List<Map<String,Double>>) m;
        List<Integer> f=new ArrayList();
        
        for (Map<String,Double> entry : thisisnotok) {
            f.add((int)(double)entry.get("id"));
        }
        v.setVotes(f);

        return v;
    }

    public void participate(video v, competition_participant cp) {
        Gson gson = new Gson();
        String json = gson.toJson(v);
        String json2 = gson.toJson(cp);
        String url = Statics.BASE_URL + "/competition/participate/?video=" + json + "&participation=" + json2;

        con.setUrl(url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String str = new String(con.getResponseData());
                System.out.println(str);
                Dialog.show("Confirmation", "Your Video has been successfully added", "Ok", null);
                con.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }
    boolean result = false;

    public boolean alreadyParticipated(int comp, int user) {

        String url = Statics.BASE_URL + "/competition/participated/?competition=" + comp + "&user=" + user;

        con.setUrl(url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                String str = new String(con.getResponseData());
                result = Boolean.valueOf(str);
                System.out.println(result);

                con.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return result;
    }
    
     public void Vote(int user, int video) {
      
        String url = Statics.BASE_URL + "/vote/?video=" + video + "&user=" + user;

        con.setUrl(url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                System.out.println("vote");
                con.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }
     public void unVote1(int user, int video) {
      
        String url = Statics.BASE_URL + "/unvote/?video=" + video + "&user=" + user;

        con.setUrl(url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                 System.out.println("unvote");
                con.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }
     public List<video> VideoList() {

        String url = Statics.BASE_URL + "/Homepage/";
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String str = new String(con.getResponseData());

                    Videos = new ArrayList<>();
                    JSONParser j = new JSONParser();

                    Map<String, Object> VideosMap = j.parseJSON(new CharArrayReader(str.toCharArray()));

                    List<Map<String, Object>> VideosList = (List<Map<String, Object>>) VideosMap.get("root");
                    if (VideosList != null) {
                        for (Map<String, Object> map : VideosList) {

                            Videos.add(parseRanks(map));
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                con.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return Videos;
    }
     
      public List<video> HomepageRanks() {

        String url = Statics.BASE_URL + "/Homepage/ranks";
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String str = new String(con.getResponseData());
                    rank = new ArrayList<>();
                    JSONParser j = new JSONParser();

                    Map<String, Object> ranksMap = j.parseJSON(new CharArrayReader(str.toCharArray()));

                    List<Map<String, Object>> ranksList = (List<Map<String, Object>>) ranksMap.get("root");
                    if (ranksList != null) {
                        for (Map<String, Object> map : ranksList) {
                            rank.add(parseRanks(map));
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                con.removeResponseListener(this);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(con);

        return rank;
    }
}



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

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;

import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Utils.Statics;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Anis
 */
public class UserServices {

    public static UserServices instance;
    private final ConnectionRequest con;
    ArrayList<User> users = new ArrayList<>();
    public boolean ResultOK;
//    private ConnectionRequest req;
    public User user;

    public UserServices() {
        con = new ConnectionRequest();
    }

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }
    boolean result;

    public boolean loginAction(String username, String password) {

        // création d'une nouvelle demande de connexion
        String url = Statics.BASE_URL + "/login/" + username + "/" + password;
        con.setUrl(url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            result = con.getResponseCode() == 200;
            
            if (result) {
                try {
                    parseListUserJson(new String(con.getResponseData()));
                    String str = new String(con.getResponseData());//Récupération de la réponse du serveur
                    // System.out.println(str);//Affichage de la réponse serveur sur la console
                    
                } catch (ParseException ex) {

                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        return result;
    }

    public User parseListUserJson(String json) throws ParseException {

        User u = new User();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
            u.setId((int) (double) obj.get("id"));
            u.setUsername(obj.get("username").toString());
            u.setEmail(obj.get("email").toString());
            u.setRole(obj.get("roles").toString());
            if (obj.get("birthday") != null) {
                u.setBirthday(new Date((((Double) ((Map<String, Object>) obj.get("birthday")).get("timestamp")).longValue() * 1000)));
            }
            if (obj.get("profilePic") != null) {
                u.setProfilePic(obj.get("profilePic").toString());
            }
            if (obj.get("sexe") != null) {
                u.setGender(obj.get("sexe").toString());
            }
            if (obj.get("telephoneNumber") != null) {
                u.setPhone_number(obj.get("telephoneNumber").toString());
            }
            if (obj.get("adresse") != null) {
                u.setAddress(obj.get("adresse").toString());
            }
            if (obj.get("name") != null) {
                u.setLastName(obj.get("name").toString());
            }
            if (obj.get("firstName") != null) {
                u.setName(obj.get("firstName").toString());
            }
            if (obj.get("bio") != null) {
                u.setBio(obj.get("bio").toString());
            }
            UserSession z = UserSession.getInstance(u);
            

        } catch (IOException ex) {
        }

        return u;
    }
 public boolean RegisterAction(String username,String email ,String password) {

        // création d'une nouvelle demande de connexion
        String url = Statics.BASE_URL + "/Register/" + "?email=" + email + "&username=" + username+"&password="+password;
        con.setUrl(url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               result = con.getResponseCode() == 200;
            
            if (result) {
                
                    
                    String str = new String(con.getResponseData());//Récupération de la réponse du serveur
                     System.out.println(str);//Affichage de la réponse serveur sur la console
             

                
            }
            }
        });
            
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        return result;}
    public boolean updateUser(User user) {
        String url = Statics.BASE_URL
                + "/updateUser?"
                + "password=" + user.getPassword()
                + "&id=" + user.getId()
                + "&firstName=" + user.getName()
                + "&lastName=" + user.getLastName()
                + "&address=" + user.getAddress()
                + "&bio=" + user.getBio()
                + "&birthday=" + user.getBirthday()
                + "&gender=" + user.getGender()
                + "&phoneNumber=" + user.getPhone_number()
                + "&email=" + user.getEmail();

        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ResultOK = req.getResponseCode() == 200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ResultOK;
    }

    User parseUser(String json) {
        User u = new User();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
            u.setId((int) (double) obj.get("id"));
            u.setUsername(obj.get("username").toString());
            u.setEmail(obj.get("email").toString());
            u.setRole(obj.get("roles").toString());
            if (obj.get("birthday") != null) {
                Date bday = new Date(((((Double) ((Map<String, Object>) obj.get("birthday")).get("timestamp")).longValue() + 3600) * 1000));
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(bday);
//                cal.add(Calendar.DATE, 1);
                u.setBirthday(bday);
            }
            if (obj.get("profilePic") != null) {
                u.setProfilePic(obj.get("profilePic").toString());
            }
            if (obj.get("sexe") != null) {
                u.setGender(obj.get("sexe").toString());
            }
            if (obj.get("telephoneNumber") != null) {
                u.setPhone_number(obj.get("telephoneNumber").toString());
            }
            if (obj.get("adresse") != null) {
                u.setAddress(obj.get("adresse").toString());
            }
            if (obj.get("name") != null) {
                u.setLastName(obj.get("name").toString());
            }
            if (obj.get("firstName") != null) {
                u.setName(obj.get("firstName").toString());
            }
            if (obj.get("bio") != null) {
                u.setBio(obj.get("bio").toString());
            }
        } catch (IOException ex) {
        }

        return u;
    }

    public User getUser(int id) {
        String url = Statics.BASE_URL + "/getUser?id=" + id;
//        System.out.println(url);
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user = parseUser(new String(con.getResponseData()));
                con.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return user;
    }

    public boolean deleteUser(int id) {
        String url = Statics.BASE_URL + "/deleteUser?id=" + id;
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ResultOK = req.getResponseCode() == 200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ResultOK;
    }

    public String encryptPwd(String pwd) {
        String pwd2 = BCrypt.hashpw(pwd, BCrypt.gensalt(13));
        pwd2 = pwd2.substring(3);
        pwd2 = "$2y" + pwd2;
//        System.out.println(pwd2);
        return pwd2;
    }

    public boolean addVideo(video v) {
        String url = Statics.BASE_URL + "/videos/add?url=" + v.getUrl() + "&title=" + v.getTitle() + "&id=" + v.getOwner().getId();
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ResultOK = req.getResponseCode() == 200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ResultOK;
    }

    String parseSub(String json) {
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
            return obj.get("state").toString();
        } catch (IOException ex) {
        }
        return null;
    }

    boolean state = false;

    public boolean isSubscribed(int follower, int user) {
        String url = Statics.BASE_URL + "/isSubscribed?subscriberId=" + user + "&subscribedToId=" + follower;

        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                state = parseSub(new String(con.getResponseData())).equals("true");
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return state;
    }

    ArrayList<Integer> parseSubCount(String json) {
        int subbedTo;
        int sub;
        ArrayList<Integer> list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
//            System.out.println("start");
//            System.out.println(obj.get("subscriberscount"));
//            System.out.println("end");
            subbedTo = Integer.parseInt(((Map<String, Object>) obj.get("subscribedToCount")).get("1").toString());
            sub = Integer.parseInt(((Map<String, Object>) obj.get("subscriberscount")).get("1").toString());
            list.add(subbedTo);
            list.add(sub);
        } catch (IOException ex) {
        }
        return list;
    }

    public ArrayList<Integer> getSubcount(int userId) {
        ArrayList list = new ArrayList<>();
        String url = Statics.BASE_URL + "/subcount?id=" + userId;
        int subscribedTo;

        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                int subscribedTo;
                int subscribers;

                subscribedTo = parseSubCount(new String(con.getResponseData())).get(0);
                subscribers = parseSubCount(new String(con.getResponseData())).get(1);
//                System.out.println("subscribedTo");
//                System.out.println(subscribedTo);
//                System.out.println("subscribers");
//                System.out.println(subscribers);
                con.removeResponseListener(this);

                list.add(subscribedTo);

                list.add(subscribers);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return list;

    }

    public boolean subscribe(int subscribedToId, int subscriberId) {
        String url = Statics.BASE_URL + "/subscribe?subscriberId=" + subscriberId + "&subscribedToId=" + subscribedToId;
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ResultOK = req.getResponseCode() == 200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ResultOK;

    }

    public boolean unSubscribe(int subscribedToId, int subscriberId) {
        String url = Statics.BASE_URL + "/unsubscribe?subscriberId=" + subscriberId + "&subscribedToId=" + subscribedToId;
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ResultOK = req.getResponseCode() == 200;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ResultOK;
    }

    ArrayList<User> parseAllUsers(String json) {
        ArrayList<User> usersList = new ArrayList<>();
        try {
//            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");

            for (Map<String, Object> obj : list) {
                User u = new User();

                u.setId((int) (double) obj.get("id"));
                u.setUsername(obj.get("username").toString());
                u.setEmail(obj.get("email").toString());
                u.setRole(obj.get("roles").toString());
                if (obj.get("birthday") != null) {
                    u.setBirthday(new Date(((((Double) ((Map<String, Object>) obj.get("birthday")).get("timestamp")).longValue()+3600) * 1000)));
                }
                if (obj.get("profilePic") != null) {
                    u.setProfilePic(obj.get("profilePic").toString());
                }
                if (obj.get("sexe") != null) {
                    u.setGender(obj.get("sexe").toString());
                }
                if (obj.get("telephoneNumber") != null) {
                    u.setPhone_number(obj.get("telephoneNumber").toString());
                }
                if (obj.get("adresse") != null) {
                    u.setAddress(obj.get("adresse").toString());
                }
                if (obj.get("name") != null) {
                    u.setLastName(obj.get("name").toString());
                }
                if (obj.get("firstName") != null) {
                    u.setName(obj.get("firstName").toString());
                }
                if (obj.get("bio") != null) {
                    u.setBio(obj.get("bio").toString());
                }

                usersList.add(u);
            }
        } catch (IOException ex) {

        }
        return usersList;
    }

    public ArrayList<User> getAllUsers() {
//        ArrayList<User> users = new ArrayList<>();
        String url = Statics.BASE_URL + "/getAllUsers";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseAllUsers(new String(con.getResponseData()));
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return users;
    }
}

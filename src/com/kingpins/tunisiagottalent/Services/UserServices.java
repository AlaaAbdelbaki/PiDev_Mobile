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
import com.codename1.ui.events.ActionListener;

import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Utils.Statics;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.util.Calendar;

import java.util.Date;

import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Anis
 */
public class UserServices {

    public static UserServices instance;
    private final ConnectionRequest con;
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
                    System.out.println(str);//Affichage de la réponse serveur sur la console
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
            System.out.println(UserSession.instance);

        } catch (IOException ex) {
        }

        return u;
    }

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
                Date bday = new Date(((((Double) ((Map<String, Object>) obj.get("birthday")).get("timestamp")).longValue()+3600 )* 1000));
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

}

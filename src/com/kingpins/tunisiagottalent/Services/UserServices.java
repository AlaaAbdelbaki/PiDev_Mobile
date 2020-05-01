/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.DateFormatPatterns;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;

import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Utils.Statics;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anis
 */
public class UserServices {

    public static UserServices instance;
    private ConnectionRequest con;

    public UserServices() {
        con = new ConnectionRequest();
    }

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }

    public void loginAction(String username, String password) {
        // création d'une nouvelle demande de connexion
        String url = Statics.BASE_URL + "/login/" + username + "/" + password;
        con.setUrl(url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            boolean result = con.getResponseCode() == 200;
            System.out.println(result);
            try {
                parseListUserJson(new String(con.getResponseData()));
                // String str = new String(con.getResponseData());//Récupération de la réponse du serveur

                // System.out.println(str);//Affichage de la réponse serveur sur la console
            } catch (ParseException ex) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
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
            u.setBirthday(new Date((((Double) ((Map<String, Object>) obj.get("birthday")).get("timestamp")).longValue() * 1000)));
            u.setProfilePic(obj.get("profilePic").toString());
            u.setGender(obj.get("sexe").toString());
            u.setPhone_number(obj.get("telephoneNumber").toString());
            u.setAddress(obj.get("adresse").toString());
            u.setName(obj.get("name").toString());
            u.setLastName(obj.get("firstName").toString());
            u.setBio(obj.get("bio").toString());
            UserSession z = UserSession.getInstance(u);
            System.out.println(UserSession.instance);

        } catch (IOException ex) {
        }

        return u;
    }

}

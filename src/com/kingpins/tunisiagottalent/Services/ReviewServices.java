/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.Services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.kingpins.tunisiagottalent.Entity.Review;
import com.kingpins.tunisiagottalent.Utils.Statics;

/**
 *
 * @author sarah
 */
public class ReviewServices {

    public static ReviewServices instance;
    private final ConnectionRequest cnx;

    public ReviewServices() {
        cnx = new ConnectionRequest();
    }

    public static ReviewServices getInstance() {
        if (instance == null) {
            instance = new ReviewServices();
        }
        return instance;
    }
    boolean result;

    public boolean addReview(Review r) {
        String url = Statics.BASE_URL + "/AjouterRJson?rating=" + r.getRating()
                + "&title=" + r.getTitle() + "&category=" + r.getCategory() + "&content=" + r.getContent()+"&user_id="+r.getUser_id().getId();
        cnx.setUrl(url);
        cnx.setPost(false);
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = cnx.getResponseCode() == 200; //Code HTTP 200 OK
                cnx.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return result;
    }

}

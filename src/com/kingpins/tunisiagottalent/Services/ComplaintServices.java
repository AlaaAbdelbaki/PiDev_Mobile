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
import com.kingpins.tunisiagottalent.Entity.Complaint;
import com.kingpins.tunisiagottalent.Utils.Statics;

/**
 *
 * @author sarah
 */
public class ComplaintServices {
 
 public static ComplaintServices instance;
 private final ConnectionRequest cnx;
  public ComplaintServices(){
 cnx = new ConnectionRequest();
 }
 public static ComplaintServices getInstance() {
        if (instance == null) {
            instance = new ComplaintServices();
        }
        return instance;
    }
    boolean result;  
    public boolean addComplaint(Complaint c) {
        String url = Statics.BASE_URL + "/AjouterCJson?subject="+ c.getSubject()
                   +"&content="+c.getContent()+"&user_id="+c.getUser_id(); 
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

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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.kingpins.tunisiagottalent.Entity.Event;
import com.kingpins.tunisiagottalent.Entity.Ticket;
import com.kingpins.tunisiagottalent.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hela
 */
public class EventServices {

    public ArrayList<Event> events;
    public ArrayList<Ticket> tickets;

    public static EventServices instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public EventServices() {
        req = new ConnectionRequest();
    }

    public static EventServices getInstance() {
        if (instance == null) {
            instance = new EventServices();
        }
        return instance;
    }

    public ArrayList<Event> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> EventListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) EventListJson.get("root");
            for (Map<String, Object> obj : list) {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Event t = new Event();

                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setTitle(obj.get("title").toString());
                t.setImg(obj.get("img").toString());
                t.setDescription(obj.get("description").toString());
                t.setType(obj.get("type").toString());
                t.setLocation(obj.get("location").toString());
                String start_date = obj.get("startDate").toString();
                String end_date = obj.get("endDate").toString();
                // t.setNb_places(Integer.parseInt(obj.get("nb_places").toString()));
                // t.setStart_date((Date) obj.get("startDate"));
                //  t.setEnd_date((Date) obj.get("endDate"));
                // t.setStart_date(obj.get("endDate").toString());
                SimpleDateFormat sdf = new SimpleDateFormat("MM:mm:ss");
                float nb = Float.parseFloat(obj.get("nbPlaces").toString());
                t.setNb_places((int) nb);
                events.add(t);
            }

        } catch (IOException ex) {

        }
        return events;
    }

    public ArrayList<Event> getAllEvents() {
        String url = Statics.BASE_URL + "/afficheEventApi";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;

    }

    public boolean participer(Event e) {
        String url = Statics.BASE_URL + "/buyTicketApi/" + e.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;

    }

    /*    
  public ArrayList<Ticket> parseTickets(String jsonText){
        try {
            tickets=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> TicketListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)TicketListJson.get("root");
             for (Map<String,Object> obj : list){
               Ticket t =new Ticket();
                
                
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                float  price= Float.parseFloat(obj.get("price").toString());
               t.setPrice(price);
               
                   float  event_id= Float.parseFloat(obj.get("event_id").toString());
               t.setEvent_id((int) event_id);  
                      
              
                tickets.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tickets;
    } 
  
   public ArrayList<Ticket> getAllTickets(){
        String url = Statics.BASE_URL+"/AfficheTicketApi";
        req.setUrl(url);
        req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               tickets = parseTickets(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tickets;
        
    }
     */
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.Services;
import com.kingpins.tunisiagottalent.Entity.Article;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.kingpins.tunisiagottalent.Entity.Comments;
import com.kingpins.tunisiagottalent.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author EZZEDINE
 */
public class ArticleService {
    public ArrayList<Article> articles;
    public ArrayList<Comments> comments;
    public boolean resultOK;
    private ConnectionRequest req;
    public static ArticleService instance;
    
    public ArticleService() {
        req = new ConnectionRequest(); }
     
    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }
    
    public ArrayList<Article> parseArticles(String jsonText){
       try {
            articles = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> articleListJson;
        
        
            articleListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
       
            List<Map<String, Object>> list = (List<Map<String, Object>>) articleListJson.get("root");
            for(Map<String,Object> obj : list){
                Article a= new Article();
                  a.setId((int)Float.parseFloat(obj.get("id").toString()));
                  a.setTitle(obj.get("title").toString());
                  a.setContent(obj.get("content").toString());
                  a.setImg(obj.get("img").toString());
             System.out.println(a);
             articles.add(a);
            }
       
        
             }
       catch (IOException ex) {
            
        }
       
        
   
       return articles; }
    
 public ArrayList<Article> getAllArticles(){
        String url = Statics.BASE_URL+"/article/show";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                articles = parseArticles(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(articles);
        return articles;
 }  
 
 
 
    public ArrayList<Comments> parseComments(String jsonText){
       try {
            comments = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> commentListJSON;
        
        
            commentListJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
       
            List<Map<String, Object>> list = (List<Map<String, Object>>) commentListJSON.get("root");
            for(Map<String,Object> obj : list){
                
                Comments c = new Comments();
                
                c.setBody(obj.get("content").toString());
                comments.add(c);
            }
       
        
             }
       catch (IOException ex) {
            
        }
       
        
   
       return comments;
     }
     
     
     public ArrayList<Comments> getAllComments(int id){
        String url = Statics.BASE_URL+"/comments/show?articleid="+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comments = parseComments(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(comments);
        return comments;
 }
}

 
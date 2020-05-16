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
import com.codename1.ui.events.ActionListener;
import com.kingpins.tunisiagottalent.Entity.Order;
import com.kingpins.tunisiagottalent.Entity.Product;
import com.kingpins.tunisiagottalent.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author paspo
 */
public class ProductServices {
     public ArrayList<Product> products;
     public ArrayList<Product> chart;
     public static ProductServices instance=null;
     public boolean resultOK;
     private ConnectionRequest req;
     
     
     private ProductServices(){
          req = new ConnectionRequest();
     }
     
     public static ProductServices getInstance() {
        if (instance == null) {
            instance = new ProductServices();
        }
        return instance;
    }
     
     public ArrayList<Product> parseProduct(String jsonText){
        
       
        try {
             products = new ArrayList<>();
             JSONParser j = new JSONParser();
             Map<String,Object> productListJson;
             productListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             List<Map<String,Object>> list = (List<Map<String,Object>>) productListJson.get("root");
             
             for(Map<String,Object> obj : list){
             Product p = new Product();
             p.setId((int)Float.parseFloat(obj.get("id").toString()));
             p.setProduct_name(obj.get("productName").toString());
             p.setImg(obj.get("img").toString());
             p.setPrice(Double.parseDouble(obj.get("price").toString()));
             p.setStock((int)Float.parseFloat(obj.get("stock").toString()));
             products.add(p);
        }
             
        }
        
        catch (IOException ex) {
            
        }
        
        return products;
        
    }
     public ArrayList<Product> getAllProducts(){
        String url = Statics.BASE_URL+"/shop/product/all";
        req.setUrl(url);
        req.setPost(false);
        
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                    products = parseProduct(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
     
     public boolean addOrder(Order o){
         String url = Statics.BASE_URL+"/shop/order/add?address="+o.getAddress()+"&total="+o.getTotal();
         req.setUrl(url);
         req.setPost(false);
         
         req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 resultOK=req.getResponseCode() == 200;
                 req.removeResponseListener(this);
             }
         });
         NetworkManager.getInstance().addToQueueAndWait(req);
         return resultOK;
     }
     
     public boolean addOrderLine(int orderId,int productid,int quantity){
            String url = Statics.BASE_URL+"/shop/orderline/add?orderid="+orderId+"&productid="+productid+"&quantity="+quantity;
         req.setUrl(url);
         req.setPost(false);
         
         req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 resultOK=req.getResponseCode() == 20;
                 req.removeResponseListener(this);
             }
         });    
         NetworkManager.getInstance().addToQueueAndWait(req);
         return resultOK;
     }
     
     
     
          public ArrayList<Product> parseChart(String jsonText) throws IOException{
              chart = new ArrayList<Product>();
              JSONParser jc = new JSONParser();
             Map<String,Object> chartListJson;
             chartListJson = jc.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             List<Map<String,Object>> listchart = (List<Map<String,Object>>) chartListJson.get("root");
             
             
             for(Map<String,Object> obj : listchart){
             Product p = new Product();
             p.setId(Integer.parseInt(obj.get("product_id").toString()));
             p.setQuantity(Integer.parseInt(obj.get("1").toString()));
             chart.add(p);
        }
             return chart;
    }
          
          public ArrayList<Product> getAllChart(){
              String url = Statics.BASE_URL+"/shop/count";
              req.setUrl(url);
              req.setPost(false);
        
              req.addResponseListener(new ActionListener<NetworkEvent>() {
              @Override
              public void actionPerformed(NetworkEvent evt) {

                  try {
                      chart = parseChart(new String(req.getResponseData()));
                      req.removeResponseListener(this);
                      
                  } catch (IOException ex) {

                  }
                    
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return chart;
          }
          
          
          public ArrayList<Product> parseOneProduct(String jsonText) throws IOException{
              chart = new ArrayList<Product>();
              JSONParser jc = new JSONParser();
             Map<String,Object> chartListJson;
             chartListJson = jc.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             List<Map<String,Object>> listchart = (List<Map<String,Object>>) chartListJson.get("root");
             
             
             for(Map<String,Object> obj : listchart){
             Product p = new Product();
             p.setProduct_name(obj.get("productName").toString());
             chart.add(p);
        }
             return chart;
    }
          
          public ArrayList<Product> getOneProduct(int id){
              String url = Statics.BASE_URL+"/product/name/"+id;
              req.setUrl(url);
              req.setPost(false);
        
              req.addResponseListener(new ActionListener<NetworkEvent>() {
              @Override
              public void actionPerformed(NetworkEvent evt) {

                  try {
                      chart = parseOneProduct(new String(req.getResponseData()));
                      req.removeResponseListener(this);
                      
                  } catch (IOException ex) {

                  }
                    
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return chart;
          }
}

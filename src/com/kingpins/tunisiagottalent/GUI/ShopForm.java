/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Cart;
import com.kingpins.tunisiagottalent.Entity.Product;
import com.kingpins.tunisiagottalent.Services.ProductServices;
import java.io.IOException;
import java.util.ArrayList;



/**
 *
 * @author paspo
 */
public class ShopForm extends Form{
    Form current;
    int test = 0;
    
    public ShopForm(Resources theme) throws IOException{
       // new HomeForm(theme).show();

       super(BoxLayout.y());
 
        current=this;
        Button showShoppingCart = new Button("Cart");
        FontImage.setMaterialIcon(showShoppingCart, FontImage.MATERIAL_SHOPPING_BASKET, 3);
        
        Button showChart = new Button("Chart");
        FontImage.setMaterialIcon(showChart, FontImage.MATERIAL_ADD_CHART, 3);
        
        showChart.addActionListener(event -> {
            new ProductChartForm(current).show();
        });
        
        Container cnt4 = new Container(new LayeredLayout());
        LayeredLayout ll4 = (LayeredLayout)cnt4.getLayout();
        
        cnt4.add(showShoppingCart);
        cnt4.add(showChart);
        ll4.setInsets(showShoppingCart, "auto auto auto 50mm");
        ll4.setInsets(showChart, "auto auto auto 1mm");
                
        add(cnt4);
        showShoppingCart.addActionListener(event -> {
           try {
               new ShoppingCartForm(current, Cart.instance.getC()).show();
           } catch (IOException ex) {

           }
        });
        
        ArrayList<Product> pd = ProductServices.getInstance().getAllProducts();
        for(Product p : pd){
            Container cnt1 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt1.getLayout();
            
            Label productnamelabel = new Label(p.getProduct_name());

            cnt1.add(productnamelabel);
            ll2.setInsets(productnamelabel, "auto auto auto auto");
                        
            
            add(cnt1);
            
            
            Container cnt3 = new Container(new LayeredLayout());
            LayeredLayout ll3 = (LayeredLayout)cnt3.getLayout();
            
            
            EncodedImage enc;
            Image imgs;
            ImageViewer imgv;
            String url = "http://127.0.0.1:8000/assets/img/shop-img/"+p.getImg();
            enc = EncodedImage.create("/logo1.png");
            imgs = URLImage.createToStorage(enc, url, url);
            imgv = new ImageViewer(imgs);
            //add(imgv);
            
            Label stock = new Label();
            //add(stock);
            cnt3.add(imgv);
            cnt3.add(stock);
             ll3.setInsets(imgv, "auto auto auto auto");
            ll3.setInsets(stock, "auto auto auto auto");
            add(cnt3);
             
            Container cnt2 = new Container(new LayeredLayout());
            LayeredLayout ll = (LayeredLayout)cnt2.getLayout();
            
            
            Button addtocartbutton = new Button("Add to cart");
            addtocartbutton.setUIID("LoginButton");
            addtocartbutton.getAllStyles().setBgColor(0x339bff);
            addtocartbutton.setPreferredSize(new Dimension(450,100));
            Button viewitembutton = new Button("View Item");
            viewitembutton.getAllStyles().setBgColor(0x339bff);
            viewitembutton.setPreferredSize(new Dimension(450,100));
            viewitembutton.setUIID("LoginButton");
            FontImage.setMaterialIcon(addtocartbutton, FontImage.MATERIAL_SHOPPING_CART, 3);
            FontImage.setMaterialIcon(viewitembutton, FontImage.MATERIAL_VISIBILITY, 3);
            
            
            cnt2.add(viewitembutton);
            cnt2.add(addtocartbutton);
            ll.setInsets(addtocartbutton, "auto auto auto 30mm");
            ll.setInsets(viewitembutton, "auto auto auto 3mm");

            add(cnt2);


             if(p.getStock() == 0 ){
                addtocartbutton.setEnabled(false);
                 stock.setText("Out Of Stock ");
                 stock.getAllStyles().setFgColor(0xff0000);

            }
         
            addtocartbutton.addActionListener(event -> {
                test = 0 ;
                
                for(Product pi : Cart.instance.getC()){
                    if(pi.getProduct_name().equals(p.getProduct_name())){
                        test = 1;
                        int quantity = pi.getQuantity() + 1 ;
                        pi.setQuantity(quantity);
                        
                    }
                    
                }
                
                if(test == 0){
                    Product pp = new Product(p.getId(),p.getProduct_name(),p.getImg(),p.getStock(),p.getPrice(),p.getQuantity());
                    Cart.instance.AddProduct(pp);
                    
                }
                Dialog.show("Success", "Item added to cart !", new Command("OK"));
            });
            
            viewitembutton.addActionListener(e -> {
                try {
                    new ViewItemForm(current, p).show();
                } catch (IOException ex) {

                }
            });
            
        }
        
    }
}

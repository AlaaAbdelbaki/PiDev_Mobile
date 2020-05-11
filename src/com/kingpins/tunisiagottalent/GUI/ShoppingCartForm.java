/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.kingpins.tunisiagottalent.Entity.Cart;
import com.kingpins.tunisiagottalent.Entity.Order;
import com.kingpins.tunisiagottalent.Entity.Product;
import com.kingpins.tunisiagottalent.Services.ProductServices;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author paspo
 */
public class ShoppingCartForm extends Form{
    Form form;
    
    public ShoppingCartForm(Form previous,ArrayList<Product> cartlist) throws IOException{
                
                super(BoxLayout.y());
                double total = 0;
                Label totalelabel = new Label();
                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
       if(cartlist.isEmpty()){
           Label empty = new Label("Your Cart is Empty");
           add(empty);
       }
       else{
           for (Product p : cartlist){
                total = total + (p.getPrice()*p.getQuantity());
                Container cnt1 = new Container(new LayeredLayout());
                LayeredLayout ll1 = (LayeredLayout)cnt1.getLayout();
                
                
                Label productname = new Label(p.getProduct_name());
                
                Label quantityText = new Label("Quantity : ");
                quantityText.getAllStyles().setFgColor(0xff0000);
                
                Label quantityValue = new Label(Integer.toString(p.getQuantity()));
                
                Button additionquantity = new Button();
                FontImage.setMaterialIcon(additionquantity, FontImage.MATERIAL_ADD, 5);
                
                Button substractquantity = new Button();
                FontImage.setMaterialIcon(substractquantity, FontImage.MATERIAL_REMOVE, 5);
                
                Label priceproducttext = new Label("Price : ");
                priceproducttext.getAllStyles().setFgColor(0xff0000);
                
                Label priceproductvalue = new Label(Double.toString(p.getPrice()*p.getQuantity()));
                
                Button trash = new Button("Remove from Cart");
                FontImage.setMaterialIcon(trash, FontImage.MATERIAL_REMOVE_SHOPPING_CART, 4);
                
                
                EncodedImage enc;
                Image imgs;
                ImageViewer imgv;
                String url = "http://127.0.0.1:8000/assets/img/shop-img/"+p.getImg();
                enc = EncodedImage.create("/logo1.png");
                imgs = URLImage.createToStorage(enc, url, url);
                imgs = imgs.scaled(400, 600);
                
                imgv = new ImageViewer(imgs);
                
                cnt1.add(imgv);
                cnt1.add(productname);
                cnt1.add(quantityText);
                cnt1.add(quantityValue);
                cnt1.add(additionquantity);
                cnt1.add(substractquantity);
                cnt1.add(priceproducttext);
                cnt1.add(priceproductvalue);
                cnt1.add(trash);
                ll1.setInsets(imgv, "auto auto auto 1mm");
                ll1.setInsets(productname, "auto auto 26mm 22mm");
                ll1.setInsets(quantityText, "auto auto 17mm 22mm");
                
                ll1.setInsets(additionquantity, "auto auto 16mm 34mm");
                ll1.setInsets(quantityValue,"auto auto 17mm 41mm");
                ll1.setInsets(substractquantity, "auto auto 16mm 43mm");
                
                ll1.setInsets(priceproducttext, "auto auto 12mm 22mm");
                ll1.setInsets(priceproductvalue, "auto auto 12mm 33mm");
                ll1.setInsets(trash, "auto auto 3mm 24mm");
                add(cnt1);
                
                additionquantity.addActionListener(event -> {
                    
                    if(p.getQuantity()<=p.getStock()-1){
                        substractquantity.setEnabled(true);
                        double test = Double.parseDouble(totalelabel.getText());
                    
                    int quantity = p.getQuantity()+1;
                    p.setQuantity(quantity);
                    quantityValue.setText(Integer.toString(p.getQuantity()));
                    priceproductvalue.setText(Double.toString(p.getQuantity()*p.getPrice()));
                    totalelabel.setText(Double.toString(test+p.getPrice()));
                    }
                    else{
                        additionquantity.setEnabled(false);
                    }
                    
                });
                substractquantity.addActionListener(event -> {
                    if(p.getQuantity()>=2){
                        additionquantity.setEnabled(true);
                        double test = Double.parseDouble(totalelabel.getText());
                    
                    int quantity = p.getQuantity()-1;
                    p.setQuantity(quantity);
                    quantityValue.setText(Integer.toString(p.getQuantity()));
                    priceproductvalue.setText(Double.toString(p.getQuantity()*p.getPrice()));
                    totalelabel.setText(Double.toString(test-p.getPrice()));
                    }
                    else{
                        substractquantity.setEnabled(false);
                    }
                });
                
                trash.addActionListener(event -> {
                    Cart.instance.RemoveProduct(p);
                    getCurrentForm().repaint();
                    getCurrentForm().refreshTheme();
                    getCurrentForm().revalidate();
                });
                
            }
            Label straightline = new Label("________________________________________________________________");
            add(straightline);
            
            Container cnt2 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt2.getLayout();
            
            totalelabel.setText(Double.toString(total));
            cnt2.add(totalelabel);
            Label totaltext = new Label("Total : ");
            
            
            ll2.setInsets(totalelabel,"auto 0 0 auto");
            ll2.setInsets(totaltext, "auto 0 0 43mm");
            cnt2.add(totaltext);
            add(cnt2);
            
            Button order = new Button("Order Now ");
            order.setUIID("LoginButton");
            
            order.setPreferredSize(new Dimension(450,100));
            add(order);
           
            
            order.addActionListener(e -> {
                
                    
            Dialog d = new Dialog("Please enter your shipping address");
            
            
            TextField address = new TextField("","your address",15,TextArea.ANY);
            TextField city = new TextField("","your city",15,TextArea.ANY);
            TextField country = new TextField("","your country",15,TextArea.ANY);
            TextField postalCode = new TextField("","your Postal Code",15,TextArea.NUMERIC);
            
            address.getAllStyles().setFgColor(0x000000);
            city.getAllStyles().setFgColor(0x000000);
            country.getAllStyles().setFgColor(0x000000);
            postalCode.getAllStyles().setFgColor(0x000000);
            
            Button valider = new Button("Order Now");
            valider.setUIID("LoginButton");
            valider.getAllStyles().setBgColor(0x339bff);
            valider.setPreferredSize(new Dimension(450,100));
            d.setLayout(BoxLayout.y());
            d.add(address);
            d.add(city);
            d.add(country);
            d.add(postalCode);
            d.add(valider);
            
            
            valider.addActionListener(event -> {
        String addressComplet = address.getText()+" "+city.getText()+" "+country.getText()+" "+postalCode.getText();
        
        Order o = new Order(Double.parseDouble(totalelabel.getText()),addressComplet);
        ProductServices.instance.addOrder(o);
            
            
            for (Product p : Cart.instance.getC()){
              ProductServices.instance.addOrderLine(o.getId(),p.getId(),p.getQuantity());

            }
            
            Dialog.show("Success", "Your order has been placed", new Command("OK"));

            
            
            
    });
            
            d.showPopupDialog(order);
                
            });
    }
       }
            
            
}

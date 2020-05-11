/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.LayeredLayout;
import com.kingpins.tunisiagottalent.Entity.Cart;
import com.kingpins.tunisiagottalent.Entity.Order;
import com.kingpins.tunisiagottalent.Entity.Product;
import com.kingpins.tunisiagottalent.Services.ProductServices;
import java.io.IOException;
import javax.mail.MessagingException;



/**
 *
 * @author paspo
 */
public class OrderItems extends Form {
    Form form;
    
    public OrderItems(Double total) throws IOException {
     
    getCurrentForm().getAllStyles().setBgColor(0xff0000);
    Container cnt1 = new Container(new LayeredLayout());
    LayeredLayout ll1 = (LayeredLayout)cnt1.getLayout();
        
    TextField address = new TextField("","your address",15,TextArea.ANY);
    TextField city = new TextField("","your city",15,TextArea.ANY);
    TextField country = new TextField("","your country",15,TextArea.ANY);
    TextField postalCode = new TextField("","your Postal Code",15,TextArea.NUMERIC);
    
    Button valider = new Button("Order Now");
    
    address.getAllStyles().setFgColor(0x000000);
    city.getAllStyles().setFgColor(0x000000);
    country.getAllStyles().setFgColor(0x000000);
    postalCode.getAllStyles().setFgColor(0x000000);
    
    cnt1.add(address);
    cnt1.add(city);
    cnt1.add(country);
    cnt1.add(postalCode);
    cnt1.add(valider);
    
    ll1.setInsets(address, "auto auto 50mm 15mm");
    ll1.setInsets(city, "auto auto 40mm 15mm");
    ll1.setInsets(country, "auto auto 30mm 15mm");
    ll1.setInsets(postalCode, "auto auto 20mm 15mm");
    ll1.setInsets(valider, "auto auto 10mm 15mm");
    
    
    
    
    
    /*add(address);
    add(city);
    add(country);
    add(postalCode);
    add(valider);*/
    
    add(cnt1);

    
    
    
    
    
    valider.addActionListener(event -> {
        String addressComplet = address.getText()+" "+city.getText()+" "+country.getText()+" "+postalCode.getText();
        
        Order o = new Order(total,addressComplet);
        ProductServices.instance.addOrder(o);
            
            
            for (Product p : Cart.instance.getC()){
              ProductServices.instance.addOrderLine(o.getId(),p.getId(),p.getQuantity());

            }
            
            
            

    });
    
    }
    
   
    
}

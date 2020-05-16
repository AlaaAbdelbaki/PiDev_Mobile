/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Product;
import java.io.IOException;

/**
 *
 * @author paspo
 */
public class ViewItemForm extends Form{
    
    public ViewItemForm(Form previous,Product p) throws IOException{
        super(BoxLayout.y());
        setUIID("CompForm");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
        
         Container cnt1 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt1.getLayout();
            
            Label productnamelabel = new Label(p.getProduct_name());

            cnt1.add(productnamelabel);
            ll2.setInsets(productnamelabel, "auto auto auto auto");
                        
            
            add(cnt1);
            
            EncodedImage enc;
            Image imgs;
            ImageViewer imgv;
            String url = "http://127.0.0.1:8000/assets/img/shop-img/"+p.getImg();
            enc = EncodedImage.create("/logo1.png");
            imgs = URLImage.createToStorage(enc, url, url);
            imgv = new ImageViewer(imgs);
            add(imgv);
            
            Container cnt2 = new Container(new LayeredLayout());
            LayeredLayout ll3 = (LayeredLayout)cnt2.getLayout();
            
            if(p.getStock() != 0){
                Label itemLeft = new Label("Only "+p.getStock()+" Items Left !");
                itemLeft.getAllStyles().setFgColor(0x00b300);
                cnt2.add(itemLeft);
                ll3.setInsets(itemLeft, "auto auto auto auto");
            }
            else{
                Label itemLeft = new Label("Out Of Stock");
                itemLeft.getAllStyles().setFgColor(0xff0000);
                cnt2.add(itemLeft);
                ll3.setInsets(itemLeft, "auto auto auto auto");
            }
            add(cnt2);
            
            Label lorem = new Label("Lorem ipsum dolor sit amet,\n consectetur adipiscing elit.\n Etiam nec euismod sem.\n ");
            add(lorem);
    }
}

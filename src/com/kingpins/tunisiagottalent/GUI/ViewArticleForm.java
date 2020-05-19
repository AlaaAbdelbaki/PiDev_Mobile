/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.share;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.kingpins.tunisiagottalent.Entity.Article;
import java.io.IOException;
import com.codename1.components.ShareButton;
import static com.codename1.ui.CN.share;
import static com.codename1.ui.CN.share;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author EZZEDINE
 */
public class ViewArticleForm extends Form {
    
    public ViewArticleForm(Article a , Form previous) throws IOException{
         super(BoxLayout.y());
       //setupSideMenu(theme);
      setUIID("CompForm");
      getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
           Container cnt1 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt1.getLayout();
        EncodedImage enc;
            Image imgs;
            ImageViewer imgv;
           String url = "http://127.0.0.1:8000/assets/img/blog-img/"+a.getImg();
            enc = EncodedImage.create("/logo1.png");
            imgs = URLImage.createToStorage(enc, url, url);
            imgs.scaled(2000,600);
            imgv = new ImageViewer(imgs);
            add(imgv);
              
            SpanLabel contentSpanLabel = new SpanLabel(a.getContent());
            add(contentSpanLabel);
            
            ShareButton share = new ShareButton();
                    share.setUIID("LoginButton");
                    share.getAllStyles().setBorder(Border.createRoundBorder(50, 30));
                    share.getAllStyles().setBgColor(0xF36B08);
                    FontImage.setMaterialIcon(share, FontImage.MATERIAL_SHARE, 4);
                    share.setText("");
                    share.setTextToShare(a.getContent().toString());
                    
           
            Container shareContainer=new Container(BoxLayout.xCenter());
            shareContainer.add(share);
            
           cnt1.add(shareContainer);  
           add(cnt1);
    }

   
}


    
    


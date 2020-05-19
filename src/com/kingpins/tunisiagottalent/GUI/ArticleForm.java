/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import static com.codename1.io.Log.p;
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
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Article;
import com.kingpins.tunisiagottalent.Services.ArticleService;
import java.io.IOException;
//import static java.time.temporal.TemporalAdjusters.previous;
import java.util.ArrayList;
import com.codename1.ui.URLImage;
import com.codename1.components.ShareButton;
/**
 *
 * @author EZZEDINE
 */
public class ArticleForm extends SideMenuBaseForm{

    Form current;
   public ArticleForm(Resources theme) throws IOException{
       //new HomeForm(theme).show();
       super(BoxLayout.y());
        setUIID("CompForm");
       setupSideMenu(theme);
        //current=this;
        ArrayList<Article> articles = ArticleService.getInstance().getAllArticles();
        System.out.println(articles);
        for(Article a : articles){
            
            Container cnt1 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt1.getLayout();
            
            Label Titlelabel = new Label(a.getTitle());
            Titlelabel.getAllStyles().setFgColor(0x000000);
            cnt1.add(Titlelabel);
            ll2.setInsets(Titlelabel, "auto auto auto auto");
            
       /* ShareButton share = new ShareButton();
                    share.setUIID("LoginButton");
                    share.getAllStyles().setBorder(Border.createRoundBorder(30, 30));
                    share.getAllStyles().setBgColor(0x009FFD);
                    FontImage.setMaterialIcon(share, FontImage.MATERIAL_SHARE, 4);
                    share.setText("");
                    share.setTextToShare(a.getContent().toString());
                    
           
            Container shareContainer=new Container(BoxLayout.xCenter());
            shareContainer.add(share);
            
           cnt1.add(shareContainer);  
            */
          
          add(cnt1); 
           
            EncodedImage enc;
            Image imgs;
            ImageViewer imgv;
           String url = "http://127.0.0.1:8000/assets/img/shop-img/"+a.getImg();
            enc = EncodedImage.create("/logo1.png");
            imgs = URLImage.createToStorage(enc, url, url);
            imgs.scaled(1500, 1200);
            imgv = new ImageViewer(imgs);
            add(imgv);
             
              // add(cnt1);
           // Label contentLabel = new Label(a.getContent());
            
              
            Button readMore = new Button("Read More");
            
          //  add(contentLabel);
            add(readMore);
            
            
            readMore.addActionListener(event -> {
                try {
                    new ViewArticleForm(a,this).show();
                } catch (IOException ex) {
           
                }});
            
        }}

   
}

      
           
            
           
            
            
            

    



   
    
        
    


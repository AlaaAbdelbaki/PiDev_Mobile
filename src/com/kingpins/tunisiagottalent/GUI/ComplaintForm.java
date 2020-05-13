/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.BrowserWindow;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Complaint;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Services.ComplaintServices;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.util.Date;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.web.WebView;


/**
 *
 * @author sarah
 */
public class ComplaintForm extends SideMenuBaseForm {
ComplaintServices rs;
    ComplaintForm(Resources res) throws IOException {
        super(BoxLayout.y());
    Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());     
        setupSideMenu(res);
        setLayout(BoxLayout.y());
        Label ls=new Label("Subject");
        ComboBox tsubject= new ComboBox();
        tsubject.addItem("Events ");
        tsubject.addItem("Orders");
        tsubject.addItem("Competitions");
        tsubject.addItem("Articles");
        Label ld=new Label("Content");
        TextArea taContent= new TextArea("");
        Button btnValider = new Button("Add");
        btnValider.setUIID("LoginButton");
        User u= UserSession.instance.getU();
       
        btnValider.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent evt) {
                 if ((tsubject.getSelectedItem()==null) ||(taContent.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        
                        Complaint c = new Complaint(tsubject.getSelectedItem().toString(),taContent.getText(),u);
                        if( ComplaintServices.getInstance().addComplaint(c))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "server error", new Command("OK"));
                    }
                    
                }
                
                
            }
          });
        
         addAll(ls,tsubject,ld,taContent,btnValider);
         Container espace1=new Container(BoxLayout.y());
         Label la=new Label("   ");
         espace1.add(la);
         add(espace1);
          Container co=new Container(BoxLayout.x());
         Label contact=new Label("Other ways to contact Us:");
         co.add(contact);
         add(co);
         Container c=new Container(BoxLayout.x());
          Label MailIcon = new Label("tunisiagottalents@gmail.com");
       FontImage.setMaterialIcon(MailIcon, FontImage.MATERIAL_MAIL, 3);
       c.add(MailIcon);
        Container c1=new Container(BoxLayout.x());
         Label TelIcon = new Label("54104757");
       FontImage.setMaterialIcon(TelIcon, FontImage.MATERIAL_SMARTPHONE, 3);
       c1.add(TelIcon);
       Container c2=new Container(BoxLayout.x());
       Label MapIcon = new Label("Z.I.ChotranaII B.P.160 Pôle Technologique ");
       FontImage.setMaterialIcon(MapIcon, FontImage.MATERIAL_PLACE, 3);
       c2.add(MapIcon);
       Container c3=new Container(BoxLayout.x());
       Label a=new Label("El Ghazela - Ariana 2083");
       Button b=new Button("View Map");
       
       b.addActionListener(e->{
        BrowserWindow win = new BrowserWindow("https://www.google.com/maps/place/ESPRIT/@36.8992777,10.1874516,17z/data=!3m1!4b1!4m5!3m4!1s0x12e2cb75abef9f39:0xb7b85ec579f3acb!8m2!3d36.8992777!4d10.1896403");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
                 win.setTitle("Esprit");
                 win.show();
        });
            
               c3.addAll(a,b);
       addAll(c,c1,c2,c3); 
       //Container espace2=new Container(BoxLayout.y());
         //Label la2=new Label("   ");
         //espace2.add(la2);
         //add(espace2);
         
       Container co2=new Container(BoxLayout.x());
       Label contact2= new Label("Connect With Us:");
       co2.add(contact2);
       add(co2);
       Container c4=new Container(BoxLayout.x());
       Image imgs=Image.createImage("/web.png");
       Button web = new Button(imgs);
        web.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("http://127.0.0.1:8000/");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });   
       Image im=Image.createImage("/facebook_icon.png");
       Button facebook = new Button( im );
       facebook.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("https://facebook.com");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });
       Button twitter = new Button( res.getImage("twitter_img.png"));
       twitter.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("https://twitter.com");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });
       Button instagram = new Button( res.getImage("insta_icon.png"));
       instagram.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("https://instagram.com");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });
       Image i=Image.createImage("/wordpress_icom.png");
       Button wordpress = new Button(i);
       wordpress.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("https://wordpress.com");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });
       Image img=Image.createImage("/linkedin_icon.png");
       Button linkedin = new Button(img);
       linkedin.addActionListener(e->{
            BrowserWindow win = new BrowserWindow("https://linkedin.com");
            win.addCloseListener(evt->{
                System.out.println("Browser was closed");
            });
            win.addLoadListener(evt->{
                System.out.println("Loaded page "+evt.getSource());
            });
            
            win.setTitle("Web Browser");
            win.show();
        });
       
        c4.addAll(web,facebook,instagram,twitter,wordpress,linkedin);
        add(c4);
        Container space=new Container(BoxLayout.y());
        //Label l=new Label("    ");
        Label ll=new Label("And if you want to share your...; ");
        //Label lll=new Label("    ");
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_MIC, s);
        Button voice= new Button(icon);
        voice.setUIID("LoginButton");
        space.addAll(ll,voice);
        FileSystemStorage fs = FileSystemStorage.getInstance();
        String recordings = fs.getAppHomePath() + "recording/";
        fs.mkdir(recordings);
        try {
            for (String file : fs.listFiles(recordings)) {
                MultiButton mb = new MultiButton(file.substring(file.lastIndexOf("/") + 1));
                mb.addActionListener((e) -> {
                    try {
                        Media m = MediaManager.createMedia(recordings + file, false);
                        m.play();
                    } catch (Throwable err) {
                        Log.e(err);
                    }
                });
              
               space.add(mb);        
            }

            voice.addActionListener( (ev) -> {
                try {
                    String file = Capture.captureAudio();
                    if (file != null) {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MMM-dd-kk-mm");
                        String fileName = sd.format(new Date());
                        String filePath = recordings + fileName;
                        Util.copy(fs.openInputStream(file), fs.openOutputStream(filePath));
                        MultiButton mb = new MultiButton(fileName);
                        mb.addActionListener((e) -> {
                            try {
                                Media m = MediaManager.createMedia(filePath, false);
                                m.play();
                            } catch (IOException err) {
                                Log.e(err);
                            }
                        });
                        space.add(mb);
                        space.revalidate();
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            });
        } catch (IOException err) {
            Log.e(err);
        }
        add(space);
       Container c5=new Container(BoxLayout.yBottom());
       Label CopyrightIcon = new Label("2020 All rights reserved |");
      FontImage.setMaterialIcon(CopyrightIcon, FontImage.MATERIAL_COPYRIGHT, 3);
       c5.add(CopyrightIcon);
       add(c5);
       Container c6=new Container(BoxLayout.xCenter());
       Label m=new Label("This Website is made with");
       Label n=new Label("");
       FontImage.setMaterialIcon(n, FontImage.MATERIAL_FAVORITE, 3);
       Label loveIcon = new Label("by KingPins");
       c6.addAll(m,n,loveIcon);
       add(c6);
    }

    @Override
    protected void showOtherForm(Resources res) {
        }
       
    
    
}

   


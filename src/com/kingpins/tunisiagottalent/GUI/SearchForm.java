/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.contacts.Contact;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Services.UserServices;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author alaa
 */
public class SearchForm extends SideMenuBaseForm {
    
    UserServices us = new UserServices();
    ArrayList<User> userList = us.getAllUsers();

//    Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    public SearchForm(Resources res) throws IOException {
        setUIID("ProfileForm");

//Init
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Image duke = null;
        try {
            duke = Image.createImage("/load.png");
        } catch (IOException err) {
            Log.e(err);
        }
        int fiveMM = Display.getInstance().convertToPixels(5);
        final Image finalDuke = duke.scaledWidth(fiveMM);
        Toolbar.setGlobalToolbar(true);
        add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            userList = us.getAllUsers();
            Display.getInstance().callSerially(() -> {
                removeAll();
                for (User u : userList) {
                    MultiButton m = new MultiButton();
                    String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + u.getProfilePic();
            EncodedImage enc = null;
            enc = (EncodedImage) res.getImage("round-mask.png");
            Image roundMask = Image.createImage(enc.getWidth() / 2, enc.getHeight() / 2, 0xff000000);
            Graphics gr = roundMask.getGraphics();
            gr.setColor(0xffffff);
            gr.fillArc(0, 0, enc.getWidth() / 2, enc.getWidth() / 2, 0, 360);
            Image profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
            profilePic = profilePic.scaled(enc.getWidth() / 2, enc.getHeight() / 2);
            Object mask = roundMask.createMask();
            profilePic = profilePic.applyMask(mask);
          //  Label UsernameLabel = new Label("   " +u.getUsername(), profilePic);
                    m.setTextLine1(u.getUsername());
                    m.setIcon(profilePic);
                
//                    
                    m.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            try {
                                new ProfileForm(res, u).show();
                            } catch (IOException ex) {
                            }
                        }
                    });
                    add(m);
                    m.setVisible(false);
                }
                revalidate();
            });
        });
        
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(false);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        
        setupSideMenu(res);
    }
}

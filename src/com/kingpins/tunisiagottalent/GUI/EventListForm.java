/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;


import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;

import com.codename1.ui.Image;



import com.codename1.ui.Toolbar;

import com.codename1.ui.URLImage;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

import com.codename1.ui.util.Resources;

import com.kingpins.tunisiagottalent.Entity.Event;
import com.kingpins.tunisiagottalent.Services.EventServices;
import java.io.IOException;


import java.util.ArrayList;

import com.codename1.messaging.Message;
import com.codename1.ui.Display;
import com.kingpins.tunisiagottalent.Utils.UserSession;


/**
 *
 * @author hela
 */
public class EventListForm extends SideMenuBaseForm {

    public EventListForm(Resources res) throws IOException {

        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        setupSideMenu(res);

        EncodedImage enc;
        EventServices es = new EventServices();
        ArrayList<Event> list = es.getAllEvents();

        {
            for (Event r : list) {

                Container c1 = new Container(BoxLayout.x());
                Container c2 = new Container(BoxLayout.x());
                Container c3 = new Container(BoxLayout.y());
                Container c4 = new Container(BoxLayout.x());

                Image im = Image.createImage("/cc2.png");
                Button bparticiper = new Button(im);

                Image im2 = Image.createImage("/ttt2.png");
                Button bticket = new Button(im2);

                Image im3 = Image.createImage("/fff3.png");
                Button bsharefb = new Button(im3);

                bparticiper.setEnabled(true);
                bticket.setEnabled(false);

                // ShareButton sb = new ShareButton();
                // sb.setText("Share"); 
                // String imageFile = FileSystemStorage.getInstance().getAppHomePath() + r.getImg();
                //  sb.setImageToShare(imageFile, r.getImg());
                ImageViewer iv = new ImageViewer();
                System.out.println(r.getImg());

                Image placeholder = Image.createImage(this.getWidth() / 3 - 3, this.getWidth() / 3 - 3, 0xbfc9d2);
                EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                ImageViewer img1 = new ImageViewer(URLImage.createToStorage(encImage, "file" + r.getImg(), "http://127.0.0.1:/" + r.getImg()));
                //http://127.0.0.1:8000/PIdev/web/assets/img/shop-img/
                c1.add(img1);
                c1.add(c3);
                c4.add(bparticiper);
                c4.add(bticket);

                c3.add(new SpanLabel(r.getTitle().toUpperCase()));
                c3.add(new SpanLabel("Description: " + r.getDescription()));
                c3.add(new SpanLabel("S_Date:" + r.getStart_date()));
                c3.add(new SpanLabel("E_Date:" + r.getEnd_date()));
                Image im4 = Image.createImage("/ll3.png");
                c2.add(im4);
                c2.add(new SpanLabel(r.getLocation()));
                c3.add(c2);
                SpanLabel l1 = new SpanLabel("NbPlaces:" + r.getNb_places());
                c3.add(l1);
                c3.add(c4);

                ShareButton share = new ShareButton();
                share.setUIID("LoginButton");
                share.getAllStyles().setBorder(Border.createRoundBorder(10, 10));
                // share.getAllStyles().setBgColor(0x009FFD);
                FontImage.setMaterialIcon(share, FontImage.MATERIAL_SHARE, 4);
                share.setText("");
                share.setTextToShare(r.getDescription());

                c4.add(share);
                add(c1);

                bparticiper.addActionListener(e -> {
                    if (r.getNb_places() > 0) {
                        EventServices.getInstance().participer(r);
                        // l1.remove();
                        refreshTheme();

                        // l1.repaint();
                        //l1.revalidate();
                        bticket.setEnabled(true);

                        Dialog.show("", "You have participated successfully !!! ", new Command("OK"));

                    } else {

                        Dialog.show("", "No more places to participate !!! ", new Command("OK"));
                    }

                });
                String i = UserSession.instance.getU().getEmail();

                bticket.addActionListener(e -> {

                    String url = "http://127.0.0.1:8000/event/reservation/" + r.getId();
                    Message m1 = new Message(url);
                    Display.getInstance().sendMessage(new String[]{i}, "Get your ticket", m1);

                });
            }
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
    }
}

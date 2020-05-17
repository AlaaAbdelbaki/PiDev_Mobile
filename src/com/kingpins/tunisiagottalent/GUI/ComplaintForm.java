/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.geom.Dimension;
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
        setUIID("CompForm");
        setupSideMenu(res);
        Label ls = new Label("Subject");
        ComboBox tsubject = new ComboBox();
        tsubject.addItem("Events ");
        tsubject.addItem("Orders");
        tsubject.addItem("Competitions");
        tsubject.addItem("Articles");
        Label ld = new Label("Content");
        TextArea taContent = new TextArea("");
        Button btnValider = new Button("Add");
        btnValider.setUIID("LoginButton");
        User u = UserSession.instance.getU();

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tsubject.getSelectedItem() == null) || (taContent.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                        Complaint c = new Complaint(tsubject.getSelectedItem().toString(), taContent.getText(), u);
                        if (ComplaintServices.getInstance().addComplaint(c)) {
                            Dialog.show("Success", "Form added with success .It will be processed and we will reply as soon as possible.", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "server error", new Command("OK"));
                    }

                }

            }
        });

        addAll(ls, tsubject, ld, taContent, btnValider);
        Container c = new Container(BoxLayout.x());
        Label MailIcon = new Label("mohamedanis.bouaziz@esprit.com");
        FontImage.setMaterialIcon(MailIcon, FontImage.MATERIAL_MAIL, 3);
        c.add(MailIcon);
        Container c1 = new Container(BoxLayout.x());
        Label TelIcon = new Label("53779876+41675757");
        FontImage.setMaterialIcon(TelIcon, FontImage.MATERIAL_SMARTPHONE, 3);
        c1.add(TelIcon);
        Container c2 = new Container(BoxLayout.x());
        SpanLabel MapIcon = new SpanLabel("Z.I.ChotranaII B.P.160 Pôle Technologique ");
        FontImage.setMaterialIcon(MapIcon, FontImage.MATERIAL_PLACE, 3);
        c2.add(MapIcon);
        Container c3 = new Container(BoxLayout.x());
        BrowserComponent map = new BrowserComponent() {
            @Override
            protected Dimension calcPreferredSize() {
                Dimension d = super.calcPreferredSize();
                d.setHeight(Display.getInstance().getDisplayHeight() * 50 / 100);
                d.setWidth(Display.getInstance().getDisplayWidth());
                return d;
            }
        };
        map.setURL("https://goo.gl/maps/1w7XZMhiYdubNxV98");
        c3.add(map);
        addAll(c3,c, c1, c2);
        Container c4 = new Container(BoxLayout.xCenter());
        Dimension d=new Dimension(100, 100);
        Image imgs = Image.createImage("/web.png");
        Button web = new Button();
        web.setSize(d);
        FontImage.setMaterialIcon(web, FontImage.MATERIAL_WEB_ASSET);
        web.addActionListener(e -> {
            Display.getInstance().execute("http://127.0.0.1:8000/");
        });
        Image im = Image.createImage("/facebook_icon.png");
        im.scale(100,100);
        Button facebook = new Button(im);
        facebook.setSize(d);
        facebook.addActionListener(e -> {
           Display.getInstance().execute("https://www.facebook.com/ReformedAF");
        });
        Image im2 = Image.createImage("/twitter_icon.png");
        Button twitter = new Button(im2);
        im2.scale(100,100);
        twitter.addActionListener(e -> {
            Display.getInstance().execute("https://www.twitter.com/I_AmAlaa");
        });
        Image im3 = Image.createImage("/instagram_icon.png");
        Button instagram = new Button(im3);
       im3.scale(100,100);
        instagram.addActionListener(e -> {
           Display.getInstance().execute("https://www.instagram.com/__montassar__/");
        });
        Image img = Image.createImage("/linkedin_icon.png");
        Button linkedin = new Button(img);
        img.scale(100,100);
        linkedin.addActionListener(e -> {
           Display.getInstance().execute("https://www.linkedin.com/in/mohamedanis-bouaziz/");
        });
        
        c4.addAll(web, facebook, instagram, twitter, linkedin);
        add(c4);
        Container c5 = new Container(BoxLayout.yBottom());
        Label CopyrightIcon = new Label("2020 All rights reserved |");
        FontImage.setMaterialIcon(CopyrightIcon, FontImage.MATERIAL_COPYRIGHT, 3);
        c5.add(CopyrightIcon);
        add(c5);
        Container c6 = new Container(BoxLayout.xCenter());
        Label m = new Label("Made with");
        Label n = new Label("");
        FontImage.setMaterialIcon(n, FontImage.MATERIAL_FAVORITE, 3);
        SpanLabel loveIcon = new SpanLabel(" by KingPins");
        c6.addAll(m, n, loveIcon);
        add(c6);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import static com.codename1.components.ImageViewer.IMAGE_FILL;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.properties.UiBinding;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import com.codename1.util.DateUtil;

import com.kingpins.tunisiagottalent.Entity.Competition;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Services.CompetitionsServices;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anis
 */
public class CompetitionsForm extends SideMenuBaseForm {

    long d, h, m, s;
    Date t1 = new Date(System.currentTimeMillis());
    CompetitionsServices cs = CompetitionsServices.getInstance();
    public CompetitionsForm(Resources res) throws IOException {
        super( BoxLayout.y());
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        setScrollableY(false);
        Container everything=new Container(BoxLayout.y()){
            @Override
            protected Dimension calcPreferredSize() {
                Dimension d = super.calcPreferredSize(); 
                d.setHeight(Display.getInstance().getDisplayHeight()*90/100);
                d.setWidth(Display.getInstance().getDisplayWidth()*80/100);
                return d;
            }
        };
        ImageViewer breadcumb=new ImageViewer();
        Image im1=res.getImage("breadcumb2.jpg");
        breadcumb.setImage(im1);
        breadcumb.setImageInitialPosition(IMAGE_FILL);
        breadcumb.getAllStyles().stripMarginAndPadding();
        setUIID("CompForm");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Tabs t = new Tabs();
        Map<Competition, Label> timers = new HashMap<>();
        for (Competition comp : cs.CompetitionsList()) {
            SpanLabel l1 = new SpanLabel("From: \n" + sdf.format(comp.getCompetition_date()));
            SpanLabel l2 = new SpanLabel("To: \n" + sdf.format(comp.getCompetition_end_date()));
            l1.getAllStyles().setAlignment(CENTER);
            l2.getAllStyles().setAlignment(CENTER);
            Component.setSameHeight(l1, l2);
            Component.setSameWidth(l1, l2);
            Container cdates = new Container(BoxLayout.xCenter());
            cdates.addAll(l1, l2);
            Label l3 = new Label( comp.getSubject());
            l3.getAllStyles().setFgColor(0xF36B08);
            l3.getAllStyles().setAlignment(CENTER);
            Button Details = new Button("View Details");
            Details.setUIID("LoginButton");
            Button Participate = new Button("Participate", "LoginButton");
            Details.setUIID("LoginButton");
            Participate.getAllStyles().setBgColor(0x246A73);
            Participate.addActionListener(e -> {
                if (UserSession.instance.getU().getRole().contains("ROLE_TALENTED")) {
                    Dialog d1Dialog = new Dialog();
                    Container userContainer = new Container(BoxLayout.yCenter());
                    Label user = new Label("Talented Account");
                    user.getAllStyles().setFgColor(0x00FF00);
                    user.getAllStyles().setBorder(Border.createDoubleBorder(1, 0x00FF00));
                    String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + UserSession.instance.getU().getProfilePic();
                    EncodedImage enc;
                    enc = (EncodedImage) res.getImage("round-mask.png");
                    Image roundMask = Image.createImage(enc.getWidth(), enc.getHeight(), 0xff000000);
                    Graphics gr = roundMask.getGraphics();
                    gr.setColor(0xffffff);
                    gr.fillArc(0, 0, enc.getWidth(), enc.getWidth(), 0, 360);
                    Image profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
                    profilePic = profilePic.scaled(enc.getWidth(), enc.getHeight());
                    Object mask = roundMask.createMask();
                    profilePic = profilePic.applyMask(mask);
                    ImageViewer im = new ImageViewer(profilePic);
                    userContainer.addAll(im, user);
                    userContainer.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
                    userContainer.getUnselectedStyle().setBackgroundGradientEndColor(0xFFBCCA);
                    userContainer.getUnselectedStyle().setBackgroundGradientStartColor(0xFFBCCA);
                    userContainer.getUnselectedStyle().setPadding(10, 10, 10, 10);
                    d1Dialog.add(userContainer);

                    d1Dialog.showPopupDialog(Participate);

                } 
                else if(cs.alreadyParticipated(comp.getId(), UserSession.instance.getU().getId())==true){
                Dialog d1Dialog = new Dialog("Already Participated!");
                d1Dialog.showPopupDialog(Participate);
                }
                else {
                    try {
                        new NewParticipationForm(res, comp, this).show();
                    } catch (IOException ex) {

                    }
                }
            });
            Details.addActionListener(e -> {
                try {
                    new CompetitionParticipationsForm(res, comp, this).show();
                } catch (IOException ex) {

                }
            });
            Label timerLabel = new Label();
            timerLabel.getAllStyles().setAlignment(CENTER);
            timerLabel.getAllStyles().setFgColor(0xF36B08);
            timerLabel.getAllStyles().setUnderline(true);
            timerLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
            timerLabel.setPropertyValue("maskName", String.valueOf(Math.abs(comp.getCompetition_end_date().getTime() - t1.getTime())));
            if (DateUtil.compare(comp.getCompetition_end_date(), t1) == -1) {
                timerLabel.setText("Competition is Over");
            } else {
                timers.put(comp, timerLabel);
            }
            Container all = new Container(BoxLayout.yCenter());
            Container top = new Container(BoxLayout.yCenter());
             if (DateUtil.compare(comp.getCompetition_end_date(), t1) == -1) {
                 Participate.setVisible(false);
                java.util.List<video> win = cs.CompetitionRanksList(comp);
                if (win.size() > 0) {

                    String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + win.get(0).getOwner().getProfilePic();
                    EncodedImage enc;
                    enc = (EncodedImage) res.getImage("round-mask.png");
                    Image roundMask = Image.createImage(enc.getWidth()*2/3, enc.getHeight()*2/3, 0xff000000);
                    Graphics gr = roundMask.getGraphics();
                    gr.setColor(0xffffff);
                    gr.fillArc(0, 0, enc.getWidth()*2/3, enc.getWidth()*2/3, 0, 360);
                    Image profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
                    profilePic = profilePic.scaled(enc.getWidth()*2/3, enc.getHeight()*2/3);
                    Object mask = roundMask.createMask();
                    profilePic = profilePic.applyMask(mask);
                    ImageViewer im = new ImageViewer(profilePic);
                    refreshTheme();
                    Label profilePicLabel = new Label("Winner: "+cs.CompetitionRanksList(comp).get(0).getOwner().getUsername());
                    profilePicLabel.getAllStyles().setAlignment(CENTER);
                    top.addAll( im,profilePicLabel);
                }
            }
            all.addAll(timerLabel, cdates, l3, Details, Participate, top);
            t.addTab("", all);
        }
        everything.addAll(t);
        everything.getAllStyles().setMarginTop(30);
        addAll(breadcumb,everything);
        setupSideMenu(res);
        registerAnimated(this);
        UITimer uit = new UITimer(() -> {
            for (Map.Entry<Competition, Label> entry : timers.entrySet()) {
                d = Long.parseLong(entry.getValue().getPropertyValue("maskName").toString());
                d = d - 1000;
                entry.getValue().setPropertyValue("maskName", String.valueOf(d));
                h = d / 3600000;
                m = ((d / 1000) % 3600) / 60;
                s = (d / 1000) % 60;

                entry.getValue().setText(twoDigits(h) + ":" + twoDigits(m) + ":" + twoDigits(s));
            }
            refreshTheme();
        });
        uit.schedule(1000, true, this);
         ip.dispose();
    }
    public static String twoDigits(long v) {
        return v < 10 ? "0" + v : "" + v;
    }

}

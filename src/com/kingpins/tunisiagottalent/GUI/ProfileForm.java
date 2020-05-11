/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;

import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Services.UserServices;
import com.kingpins.tunisiagottalent.Services.VideoServices;

import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author alaa
 */
public class ProfileForm extends SideMenuBaseForm {

    UserServices us = new UserServices();
//    Profile elements
    User user = us.getUser(UserSession.instance.getU().getId());
    EncodedImage enc;
    Image profilePic;
    ImageViewer imgv;
    String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + user.getProfilePic();
//    String linkProfilePic = "http://192.168.1.8:8000/assets/uploads/" + user.getProfilePic();
//    String linkProfilePic = "http://192.168.34.17:8000/assets/uploads/" + user.getProfilePic();
    Label username = new Label(user.getUsername());
    Label name = new Label(user.getName());
    Label lastName = new Label(user.getLastName());
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyy");

    Label birthday = new Label(sdf1.format(user.getBirthday()));
    Label bio = new Label(user.getBio());
    Button updateProfile = new Button("Update your profile");
//    Button addVideo = new Button("Add video");
    Button admin = new Button("Admin");
    Button talent = new Button("Talent");
    Button regular = new Button("Simple User");
    Picker sort = new Picker();
    Form current = this;

    int count = 0;

//    Containers
    Container profilePicContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container usernameContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container statusContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container userDetails = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container userDetailsButtons = new Container(new FlowLayout(CENTER, CENTER));
    Container profileFeedContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container sortContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container footer = new Container(new FlowLayout(CENTER, CENTER));
    FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

//    Final form
    public ProfileForm(Resources res) throws IOException {
        super(BoxLayout.y());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        setupSideMenu(res);
//        Tests
        System.out.println("entered profile");
        System.out.println(user.getBirthday());
//        Init
        enc = EncodedImage.create("/load.png");
        Image roundMask = Image.createImage(enc.getWidth(), enc.getHeight(), 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, enc.getWidth(), enc.getWidth(), 0, 360);
        profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
        profilePic.scale(enc.getWidth(), enc.getHeight());
        imgv = new ImageViewer(profilePic);
        Object mask = roundMask.createMask();
        profilePic = profilePic.applyMask(mask);
        imgv.setImage(profilePic);
        if (user.getRole().contains("ROLE_TALENTED")) {

            fab.setTextPosition(BOTTOM);
            fab.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Dialog addVideo = new Dialog("Add video");
                    addVideo.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                    Container title = new Container(new FlowLayout(CENTER, CENTER));
                    Container url = new Container(new FlowLayout(CENTER, CENTER));
                    Container preview = new Container(new FlowLayout(CENTER, CENTER));
                    TextField titleInput = new TextField();
                    TextField urlInput = new TextField();
                    titleInput.getStyle().setFgColor(0);
                    urlInput.getStyle().setFgColor(0);
                    Button submit = new Button("Submit");
                    BrowserComponent browser = new BrowserComponent();
                    int videoWidth = addVideo.getWidth();
                    browser.setPreferredH((videoWidth * 9) / 16);
                    browser.setPreferredW(videoWidth);

                    urlInput.addDataChangedListener(new DataChangedListener() {
                        @Override
                        public void dataChanged(int type, int index) {
                            String code = urlInput.getText().substring(urlInput.getText().length() - 11);
                            String link = "https://www.youtube.com/embed/" + code;
                            System.out.println(link);
                            browser.setURL(link);
                            browser.getAllStyles().setPadding(0, 0, 0, 0);
                            browser.getAllStyles().setMargin(0, 0, 0, 0);
                        }
                    });
                    submit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            video v = new video();
                            v.setTitle(titleInput.getText());
                            v.setUrl("https://www.youtube.com/embed/" + urlInput.getText().substring(urlInput.getText().length() - 11));
                            v.setOwner(user);
                            if (us.addVideo(v)) {
                                addVideo.dispose();
                            }
                        }
                    });
                    title.add(titleInput);
                    url.add(urlInput);
                    preview.add(browser);
                    addVideo.add(new Label("Title"));
                    addVideo.add(title);
                    addVideo.add(new Label("Video Link"));
                    addVideo.add(url);
                    addVideo.add(new Label("Preview"));
                    addVideo.add(preview);
                    addVideo.add(submit);

                    addVideo.show();

                }
            });
            fab.bindFabToContainer(this.getContentPane());
        }

        sort.setType(Display.PICKER_TYPE_STRINGS);
        sort.setStrings("Newest videos", "Oldest videos");
        sort.setSelectedString("Oldest videos");
        sort.getStyle().setFgColor(0);
//        Adding profile elements to containers
        sortContainer.add(new Label("Sort videos"));
        sortContainer.add(sort);
        profilePicContainer.add(imgv);
        usernameContainer.add(username);
        userDetails.add(name);
        userDetails.add(lastName);
        userDetails.add(birthday);
        userDetails.add(bio);
        userDetailsButtons.add(updateProfile);
//        userDetailsButtons.add(addVideo);
        userDetails.add(userDetailsButtons);
        profileFeedContainer.add(userDetails);
        profileFeedContainer.add(sortContainer);
        profileFeedContainer.add(loadVideos());
        footer.add(new Label("                       "));
        profileFeedContainer.add(footer);
        if (count < 5) {
            count++;
        } else {
            count = 0;
        }
//        Button Actions

        updateProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    new UpdateProfileForm(res, current).show();
                } catch (IOException ex) {
                }
            }
        });
//        Container properties
//        profilePicContainer.setScrollableX(false);
//        usernameContainer.setScrollableX(false);
//        profileFeedContainer.setScrollableX(true);

//        Adding containers to the form
        System.out.println(user.getRole());
        if (user.getRole().contains("ROLE_ADMIN")) {
            statusContainer.add(admin);
        } else if (user.getRole().contains("ROLE_TALENTED")) {
            statusContainer.add(talent);
        } else {
            statusContainer.add(regular);
        }
        add(profilePicContainer);
        add(usernameContainer);
        add(statusContainer);
        add(profileFeedContainer);

        //Buttons actions
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Dialog alert = new Dialog("You're an admin");
                alert.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container buttons = new Container(new FlowLayout(CENTER, CENTER));
                SpanLabel message = new SpanLabel("Supreme lord");
                Button ok = new Button(new Command("OK"));
                content.add(message);
                buttons.add(ok);
                alert.add(content);
                alert.add(buttons);
                Display display = Display.getInstance();
//                alert.show((display.getDisplayHeight()/4)*2, (display.getDisplayHeight()/4)*3, 0, 0);
                alert.show();

            }
        });
        regular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog alert = new Dialog("You're still not a talent");
                SpanLabel message = new SpanLabel("What are you waiting for?\nParticipate in a competition to upgrade your account!");
                alert.add(message);
                Button participate = new Button("Participate");
                participate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            new CompetitionsForm(res).show();
                        } catch (IOException ex) {
                        }
                    }
                });
                Button Cancel = new Button(new Command("CANCEL"));
                alert.add(Cancel);
                alert.add(participate);
                alert.showDialog();
            }
        });
        talent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog alert = new Dialog("You're a talent");
                SpanLabel message = new SpanLabel("You're a talent !!\nYou can now post as much videos as you want !");
                alert.add(message);
                Button ok = new Button(new Command("OK"));
                alert.add(ok);
                alert.showDialog();
            }
        });
//        sort.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                try {
////                    loadVideos().refresh();
//                } catch (IOException ex) {
//                }
//            }
//        });
    }
//

    Container loadVideos() throws IOException {
        Container videos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Display display = Display.getInstance();
        int videoWidth = (int) ((double) display.getDisplayWidth());
        int videoHeight = (int) ((double) videoWidth * 0.5625);
//        Tabs t = new Tabs();
//        t.hideTabs();
//        Style s = UIManager.getInstance().getComponentStyle("Button");
//        FontImage radioEmptyImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_UNCHECKED, s);
//        FontImage radioFullImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_CHECKED, s);
        ArrayList<video> vids = VideoServices.getInstance().getVideo(user.getId());
        System.out.println(vids.size());
//        System.out.println(sort.getText());
        if (sort.getText().equals("Oldest videos")) {

            for (video v : VideoServices.getInstance().getVideo(user.getId())) {
                //        for (int i = vids.size(); i <= 0; i--) {

                Label Title = new Label(v.getTitle());

                BrowserComponent browser = new BrowserComponent();

                browser.setPreferredH(videoHeight);
                browser.setPreferredW(videoWidth);
                browser.setURL(v.getUrl());
                browser.getAllStyles().setPadding(0, 0, 0, 0);
                browser.getAllStyles().setMargin(0, 0, 0, 0);
                videos.add(Title);
                videos.add(browser);
            }
        } else {
            for (int i = vids.size(); i <= 0; i--) {
                Label Title = new Label(vids.get(i).getTitle());

                BrowserComponent browser = new BrowserComponent();

                browser.setPreferredH(videoHeight);
                browser.setPreferredW(videoWidth);
                browser.setURL(vids.get(i).getUrl());
                browser.getAllStyles().setPadding(0, 0, 0, 0);
                browser.getAllStyles().setMargin(0, 0, 0, 0);
                videos.add(Title);
                videos.add(browser);
            }
        }
        return videos;
    }

//    InfiniteContainer loadVideos2() {
//        InfiniteContainer videos2 = new InfiniteContainer() {
//            @Override
//            public Component[] fetchComponents(int index, int amount) {
//                ArrayList<video> vids = VideoServices.getInstance().getVideo(user.getId());
//                MultiButton[] cmps = new MultiButton[vids.size()];
//                for (int i = 0; i < cmps.length; i++) {
//
//                    int videoWidth = (int) ((double) Display.getInstance().getDisplayWidth());
//                    int videoHeight = (int) ((double) videoWidth * 0.5625);
//                    String Title = vids.get(i).getTitle();
//
//                    BrowserComponent browser = new BrowserComponent();
//
//                    browser.setPreferredH(videoHeight);
//                    browser.setPreferredW(videoWidth);
//                    browser.setURL(vids.get(i).getUrl());
//                    browser.getAllStyles().setPadding(0, 0, 0, 0);
//                    browser.getAllStyles().setMargin(0, 0, 0, 0);
//                    
//                    cmps[i] = new MultiButton(Title);
//                    cmps[i]
//                    videos.add(Title);
//                    videos.add(browser);
//                }
//                return cmps;
//            }
//        };
//
//        return videos2;
//    }

}

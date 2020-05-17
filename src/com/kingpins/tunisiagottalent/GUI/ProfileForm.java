/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
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
//    User user = us.getUser(UserSession.instance.getU().getId());
    EncodedImage enc;
    Image profilePic;
    ImageViewer imgv;

    Label username = new Label("username");
    Label name = new Label("name");
    Label lastName = new Label("lastName");
    Label subCount = new Label("SubCount");
    Label subscribedToCount = new Label("subscribedToCount");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyy");

    Label birthday = new Label("bday");
    SpanLabel bio = new SpanLabel("bio");
    Button updateProfile = new Button("Update your profile", "LoginButton");
//    Button addVideo = new Button("Add video");
    Button admin = new Button("Admin", "LoginButton");

    Button talent = new Button("Talent", "LoginButton");
    Button regular = new Button("Simple User", "LoginButton");
    Button subscribe = new Button("Subscribe");
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
    Container subInfo = new Container(new FlowLayout(CENTER, CENTER));
    Container subButton = new Container(new FlowLayout(CENTER, CENTER));
    Container subscribtion = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

//    Final form
    public ProfileForm(Resources res, User user) throws IOException {
        super(BoxLayout.y());
        
        setUIID("ProfileForm");
        setupSideMenu(res);
        
        updateProfile.getAllStyles().setBgColor(0xF36B08);

        admin.getAllStyles().setBgColor(0xff0000);
        FontImage.setMaterialIcon(admin, FontImage.MATERIAL_BUILD, 3);

        talent.getAllStyles().setBgColor(0x32CD32);
        FontImage.setMaterialIcon(talent, FontImage.MATERIAL_FAVORITE, 3);

        regular.getAllStyles().setBgColor(0xF36B08);
        FontImage.setMaterialIcon(regular, FontImage.MATERIAL_ACCOUNT_CIRCLE, 3);

        //labels init
        username.setText(user.getUsername());
        name.setText(user.getName());
        lastName.setText(user.getLastName());
        birthday.setText(sdf1.format(user.getBirthday()));
        bio.setText(user.getBio());
        subCount.setText("Subscribers: " + Integer.toString(us.getSubcount(user.getId()).get(1)));
        subscribedToCount.setText("Subscribed to: " + Integer.toString(us.getSubcount(user.getId()).get(0)));
        //labels init end

        //if profile
        subscribe.setVisible(false);
        if (!user.getUsername().equals(UserSession.instance.getU().getUsername())) {
            updateProfile.setVisible(false);
            //if not subscribed
            subscribe.setVisible(true);
            if (!us.isSubscribed(user.getId(), UserSession.instance.getU().getId())) {
                subscribe.setText("Subscribe");
                subButton.revalidate();

            } //if subscribed
            else {
                subscribe.setText("Unsubscribe");
                subButton.revalidate();
            }
        }

        //Profile picture mask init
        String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + user.getProfilePic();
        enc = (EncodedImage) res.getImage("round-mask.png");
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
        //Profile picture mask init ends

        if (user.getRole().contains("ROLE_TALENTED") && user.getId() == UserSession.instance.getU().getId()) {

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
                    Button submit = new Button("Submit", "LoginButton");
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

                    addVideo.showPopupDialog(fab);

                }
            });
            fab.bindFabToContainer(this.getContentPane());
        }

        sort.setType(Display.PICKER_TYPE_STRINGS);
        sort.setStrings("Newest videos", "Oldest videos");
        sort.setSelectedString("Oldest videos");
        sort.getStyle().setFgColor(0);
//        Adding profile elements to containers
        subInfo.add(subCount);
        subInfo.add(subscribedToCount);
        subButton.add(subscribe);
        subscribtion.add(subInfo);
        subscribtion.add(subButton);
        sortContainer.add(new Label("Sort videos"));
        sortContainer.add(sort);
        profilePicContainer.add(imgv);
        profilePicContainer.revalidate();
        usernameContainer.add(username);
        userDetails.add(name);
        userDetails.add(lastName);
        userDetails.add(birthday);
        userDetails.add(bio);
        userDetailsButtons.add(updateProfile);
        userDetails.add(userDetailsButtons);
        profileFeedContainer.add(userDetails);
        profileFeedContainer.add(subscribtion);
        profileFeedContainer.add(sortContainer);
        profileFeedContainer.add(loadVideos(user));
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
        subscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int subCountValue;
                subCountValue = us.getSubcount(user.getId()).get(1);
                System.out.println("Subcount= " + subCountValue);
                if (subscribe.getText().equals("SUBSCRIBE")) {
                    if (us.subscribe(user.getId(), UserSession.instance.getU().getId())) {
                        subCountValue++;
                        subCount.setText("Subscribers: " + subCountValue);
                        subscribe.setText("UNSUBSCRIBE");
                        subInfo.revalidate();
                        subButton.revalidate();
                        System.out.println("changed to unsub");
                        return;
                    }

                }
                subCountValue = us.getSubcount(user.getId()).get(1);
                if (subscribe.getText().equals("UNSUBSCRIBE")) {
                    if (us.unSubscribe(user.getId(), UserSession.instance.getU().getId())) {
                        subCountValue--;
                        subCount.setText("Subscribers: " + subCountValue);
                        subscribe.setText("SUBSCRIBE");
                        subInfo.revalidate();
                        subButton.revalidate();
                        System.out.println("changed to sub");
                    }
                }
            }
        });
//        sort.addStateChangeListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                sort.getSelectedString();
//                try {
//                    loadVideos(user).revalidate();
//                } catch (IOException ex) {
//                }
//            }
//        });
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(sort.getSelectedString());

                //Needs to be fixed
            }
        });
        

    }
//

    Container loadVideos(User user) throws IOException {
        //   setUIID("loadedVideos");
        Container videos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Display display = Display.getInstance();
        int videoWidth = (int) ((double) display.getDisplayWidth());
        int videoHeight = (int) ((double) videoWidth * 0.5625);
        ArrayList<video> vids = VideoServices.getInstance().getVideo(user.getId());
        System.out.println(vids.size());
        if (sort.getSelectedString().equals("Oldest videos")) {

            for (video v : VideoServices.getInstance().getVideo(user.getId())) {

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

}

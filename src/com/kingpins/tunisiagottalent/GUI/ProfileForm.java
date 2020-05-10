/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;

import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Services.UserServices;
import com.kingpins.tunisiagottalent.Services.VideoServices;

import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;

import java.text.SimpleDateFormat;

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
    Label username = new Label(user.getUsername());
    Label name = new Label(user.getName());
    Label lastName = new Label(user.getLastName());
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyy");
    
    Label birthday = new Label(sdf1.format(user.getBirthday()));
    Label bio = new Label(user.getBio());
    Button updateProfile = new Button("Update your profile");
//    Button addVideo = new Button("Add video");
    int count = 0;

//    Containers
    Container profilePicContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container usernameContainer = new Container(new FlowLayout(CENTER, CENTER));
    Container userDetails = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container userDetailsButtons = new Container(new FlowLayout(CENTER, CENTER));
    Container profileFeedContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
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
        fab.setTextPosition(BOTTOM);
        fab.addActionListener(e -> ToastBar.showErrorMessage("Not implemented yet..."));
        fab.bindFabToContainer(this.getContentPane());
//        Adding profile elements to containers
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
        profileFeedContainer.add(loadVideos());
        if (count < 5) {
            count++;
        } else {
            count = 0;
        }
//        Button Actions
        Form current = this;
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
        profilePicContainer.setScrollableX(false);
        usernameContainer.setScrollableX(false);
        profileFeedContainer.setScrollableX(true);

//        Adding containers to the form
        add(profilePicContainer);
        add(usernameContainer);
        add(profileFeedContainer);

    }
//

    Container loadVideos() throws IOException {
        Container videos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Display display = Display.getInstance();
        int videoWidth = (int) ((double) display.getDisplayWidth());
        int videoHeight = (int) ((double) videoWidth * 0.5625);
        for (video v : VideoServices.getInstance().getVideo(user.getId())) {

            Label Title = new Label(v.getTitle());

            BrowserComponent browser = new BrowserComponent();

            browser.setPreferredH(videoHeight);
            browser.setPreferredW(videoWidth);
//            browser.setScrollVisible(false);
//            browser.setScrollable(false);

            String integrationCode = "<iframe width=\"" + videoWidth + "\" height=\"" + videoHeight + "\" src=\"" + v.getUrl() + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            browser.setPage(integrationCode, null);
            browser.getAllStyles().setPadding(0, 0, 0, 0);
            browser.getAllStyles().setMargin(0, 0, 0, 0);
            videos.add(Title);
            videos.add(browser);

        }
        return videos;
    }

}

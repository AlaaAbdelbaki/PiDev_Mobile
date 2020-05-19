/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;


import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.Switch;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Services.CompetitionsServices;
import com.kingpins.tunisiagottalent.Services.UserServices;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;


/**
 *
 * @author Anis
 */
public class HomeForm extends SideMenuBaseForm {
 FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
    CompetitionsServices cs = CompetitionsServices.getInstance();
 UserServices us = new UserServices();
    public HomeForm(Resources res) throws IOException {
        super(BoxLayout.y());
         Dialog ip = new InfiniteProgress().showInfiniteBlocking();
        setUIID("Homepage");
        setupSideMenu(res);
      
        
        Label topLabel=new Label("Top 3");
        topLabel.getAllStyles().setAlignment(CENTER);
        add(topLabel);
            Container RanksContainer=new Container(BoxLayout.xCenter());
            RanksContainer.getAllStyles().setMarginBottom(30);
            RanksContainer.getAllStyles().setMarginTop(30);
            updateRanks(RanksContainer,res);
            add(RanksContainer);
        for (video v : cs.VideoList()) {
            Container videoContainer = new Container(BoxLayout.y()) {
                @Override
                protected Dimension calcPreferredSize() {
                    Dimension d = super.calcPreferredSize();
                    d.setHeight(Display.getInstance().getDisplayHeight() * 55 / 100);
                    d.setWidth(Display.getInstance().getDisplayWidth() * 90 / 100);
                    return d;
                }
            };
            Container DetailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + v.getOwner().getProfilePic();
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
            Label UsernameLabel = new Label("   " + v.getOwner().getUsername(), profilePic);
            Label TitleLabel = new Label(v.getTitle());
            Label DateLabel = new Label(v.getPublish_date().toString());
            DateLabel.getAllStyles().setFgColor(0x990099);
            DateLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
            DetailsContainer.addAll(UsernameLabel, DateLabel, TitleLabel);

            BrowserComponent browser = new BrowserComponent() {
                @Override
                protected Dimension calcPreferredSize() {
                    Dimension d = super.calcPreferredSize();
                    d.setHeight(Display.getInstance().getDisplayWidth() * 60 / 100);
                    d.setWidth(Display.getInstance().getDisplayWidth() * 90 / 100);
                    return d;
                }
            };
            browser.setURL(v.getUrl());
            videoContainer.add(DetailsContainer);
            browser.getAllStyles().setMarginLeft(10);
            browser.getAllStyles().setMarginRight(10);
            Container votingContainer = new Container(BorderLayout.center());
            Switch s1 = new Switch();
            s1.getUnselectedStyle().setFgColor(0xF36B08);
            s1.getSelectedStyle().setBgColor(0x990099);
            Label Heart = new Label();
            Heart.getAllStyles().setFgColor(0x990099);
            Label numVotes = new Label(String.valueOf(v.getNbVote().size()));
            Label voteicon = new Label();
            voteicon.getAllStyles().setFgColor(0x990099);
            FontImage.setMaterialIcon(voteicon, FontImage.MATERIAL_THUMB_UP, 3);
            s1.addActionListener((e) -> {
                if (s1.isOn()) {

                    numVotes.setText(String.valueOf(Integer.valueOf(numVotes.getText()) + 1));
                    FontImage.setMaterialIcon(Heart, FontImage.MATERIAL_FAVORITE, 4);
                    cs.Vote(UserSession.instance.getU().getId(), v.getId());
                    updateRanks(RanksContainer,res);
                    refreshTheme();
                    
                    return;
                }
                if (s1.isOff()) {
                    numVotes.setText(String.valueOf(Integer.valueOf(numVotes.getText()) - 1));
                    FontImage.setMaterialIcon(Heart, FontImage.MATERIAL_FAVORITE_OUTLINE, 4);
                    
                     cs.unVote1(UserSession.instance.getU().getId(), v.getId());
                     updateRanks(RanksContainer,res);
                     refreshTheme();
                    return;
                    
                }
               
            });
            if (v.getNbVote().contains(UserSession.instance.getU().getId())) {
                s1.setValue(true);
                FontImage.setMaterialIcon(Heart, FontImage.MATERIAL_FAVORITE, 4);
            }

            votingContainer.getAllStyles().setMarginTop(30);
            votingContainer.add(BorderLayout.CENTER_BEHAVIOR_CENTER, BoxLayout.encloseXCenter(s1, Heart));
            votingContainer.add(BorderLayout.EAST, BoxLayout.encloseX(numVotes, voteicon));
            
           
           
            videoContainer.add(browser);
            videoContainer.add(votingContainer);
           
            videoContainer.getAllStyles().setMarginBottom(20);
            videoContainer.getAllStyles().setMarginTop(20);
            videoContainer.getAllStyles().setBorder(Border.createRoundBorder(50, 50, 0xffffff));
            add(videoContainer);
        }
if (UserSession.instance.getU().getRole().contains("ROLE_TALENTED") ) {

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
                            v.setOwner(UserSession.instance.getU());
                            if (us.addVideo(v)) {
                                addVideo.dispose();
                                try {
                                    new HomeForm(res).showBack();
                                } catch (IOException ex) {
                                
                                }
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
ip.remove();
    }
    public void updateRanks(Container c,Resources res){
        c.removeAll();
        
         for (video v : cs.HomepageRanks()) {
                String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + v.getOwner().getProfilePic();
                EncodedImage enc = null;
                enc = (EncodedImage) res.getImage("round-mask.png");
                Image roundMask = Image.createImage(enc.getWidth() * 2 / 3, enc.getHeight() * 2 / 3, 0xff000000);
                Graphics gr = roundMask.getGraphics();
                gr.setColor(0xffffff);
                gr.fillArc(0, 0, enc.getWidth() * 2 / 3, enc.getWidth() * 2 / 3, 0, 360);
                Image profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
                profilePic = profilePic.scaled(enc.getWidth() * 2 / 3, enc.getHeight() * 2 / 3);
                Object mask = roundMask.createMask();
                profilePic = profilePic.applyMask(mask);
                ImageViewer im = new ImageViewer(profilePic);
                Label profilePicLabel = new Label(v.getOwner().getUsername());
                profilePicLabel.getAllStyles().setAlignment(CENTER);
                profilePicLabel.getAllStyles().setMarginBottom(30);
                profilePicLabel.getAllStyles().setFgColor(0xffffff);
                im.getAllStyles().setMarginRight(20);
                c.add(im);
            }
         
        };
}

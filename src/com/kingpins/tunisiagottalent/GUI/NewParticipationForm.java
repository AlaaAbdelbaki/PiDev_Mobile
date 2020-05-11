/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Competition;
import com.kingpins.tunisiagottalent.Entity.competition_participant;
import com.kingpins.tunisiagottalent.Entity.video;
import com.kingpins.tunisiagottalent.Services.CompetitionsServices;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.util.Date;


/**
 *
 * @author Anis
 */
public class NewParticipationForm extends Form{
    String embededurl;
    CompetitionsServices cs = CompetitionsServices.getInstance();
    public NewParticipationForm(Resources res, Competition c, Form parentForm) throws IOException {
       super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
     this.getAllStyles().setBgImage(res.getImage("bg-4.jpg"));
     this.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        getToolbar().addMaterialCommandToLeftBar(
                "",
                FontImage.MATERIAL_ARROW_BACK,
                (ev) -> parentForm.showBack());
        
        Label FormTitle=new Label("Add New Video");
        FormTitle.getAllStyles().setAlignment(CENTER);
        TextField Title = new TextField("", "Title", 15, TextField.EMAILADDR);
         TextField url = new TextField("", "URL", 15, TextField.EMAILADDR);
         Label dateLabel=new Label(new Date(System.currentTimeMillis()).toString());
         BrowserComponent browser = new BrowserComponent();
         browser.setVisible(false);
         url.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
                if (!url.getText().isEmpty()){
         embededurl = "https://www.youtube.com/embed/";
        String code = url.getText().substring(url.getText().length() - 11);
        embededurl = embededurl + code;
               browser.setURL(embededurl);
                browser.setVisible(true);}
            }
        });
         Button Postvideo=new Button("Post Video");
         Postvideo.setUIID("LoginButton");
         Postvideo.addActionListener(e -> {
          if ((url.getText().length() == 0) || (Title.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all fields", new Command("OK"));
            } else {
          video v =new video(embededurl, Title.getText(), new Date(System.currentTimeMillis()), UserSession.instance.getU());
              competition_participant cp=new competition_participant(c, UserSession.instance.getU(), new Date(System.currentTimeMillis()), v);
              cs.participate(v,cp);
            /*  try {
                  new CompetitionParticipationsForm(res,c,new CompetitionsForm(res)).show();
              } catch (IOException ex) {
                  
              }*/
          }
             
         });
         Container labels=new Container(BoxLayout.yCenter()).addAll(Title,
                url,dateLabel);
         Container by = new Container( new GridLayout(2, 1));
                by.addAll(
                labels,
                browser
                
        );
         add( BorderLayout.NORTH,FormTitle);
        add(BorderLayout.CENTER,by);
        add(BorderLayout.SOUTH,Postvideo);
    }
}

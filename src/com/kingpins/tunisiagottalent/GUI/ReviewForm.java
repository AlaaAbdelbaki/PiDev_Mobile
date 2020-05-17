/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.Review;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Services.ReviewServices;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;

/**
 *
 * @author sarah
 */
public class ReviewForm extends SideMenuBaseForm {

    ReviewServices rs;

    ReviewForm(Resources res) throws IOException {
        super(BoxLayout.y());
         setUIID("CompForm");
        setupSideMenu(res);
        
        User u = UserSession.instance.getU();
        Label lc = new Label("Category");
        ComboBox category = new ComboBox();
        category.addItem("Events");
        category.addItem("Orders");
        category.addItem("Competitions");
        category.addItem("Articles");
        Label lr = new Label("Rating");
        Slider rating = new Slider();
        rating.setEditable(true);
        rating.setMinValue(1);
        rating.setMaxValue(6);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(rating.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(rating.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(rating.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(rating.getSliderFullUnselectedStyle(), fullStar);
        rating.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        Label lt = new Label("Title");
        TextField tftitle = new TextField("", "title");
        Label ld = new Label("Content");
        TextArea taContent = new TextArea("");
        Button btnValider = new Button("Add");
        btnValider.setUIID("LoginButton");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((taContent.getText().length() == 0) || (category.getSelectedItem() == null) || (tftitle.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Review r = new Review(u, category.getSelectedItem().toString(), rating.getProgress(), taContent.getText(), tftitle.getText());
                        
                        if (ReviewServices.getInstance().addReview(r)) {
                            Dialog.show("Success", "Review Sent with success", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (Exception e) {
                        Dialog.show("ERROR", "server error", new Command("OK"));
                    }

                }

            }
        });

        addAll(lr, FlowLayout.encloseCenter(rating));
        addAll(lc, category, lt, tftitle, ld, taContent);
        add(btnValider);
    }

    

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

}

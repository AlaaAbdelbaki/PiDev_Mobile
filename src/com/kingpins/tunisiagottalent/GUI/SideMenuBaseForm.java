/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.io.InputStream;





/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) throws IOException {
         /*       Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());*/
        Image logo = res.getImage("logo.png");
      
        ImageViewer im =new ImageViewer(logo);
        Container sidemenuTop = BorderLayout.center(im);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Home", FontImage.MATERIAL_HOME, null );
         getToolbar().addMaterialCommandToSideMenu("  Profile", FontImage.MATERIAL_PERSON, e -> {
            try {
                new ProfileForm(res,UserSession.instance.getU()).show();
            } catch (IOException ex) {
               
            }
        });
          getToolbar().addMaterialCommandToSideMenu("  Search", FontImage.MATERIAL_SEARCH,e->{
            try {
                new SearchForm(res).show();
            } catch (IOException ex) {
            }
        });
        getToolbar().addMaterialCommandToSideMenu("  Shop", FontImage.MATERIAL_SHOP, null);
        getToolbar().addMaterialCommandToSideMenu("  Events", FontImage.MATERIAL_EVENT,null);
        getToolbar().addMaterialCommandToSideMenu("  Competitions", FontImage.MATERIAL_TRENDING_UP, e -> {
            try {
                new CompetitionsForm(res).show();
            } catch (IOException ex) {
               
            }
        });
        getToolbar().addMaterialCommandToSideMenu("  News", FontImage.MATERIAL_LIST,null);
        getToolbar().addMaterialCommandToSideMenu("  Reviews", FontImage.MATERIAL_THUMB_UP, null);
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {new LoginForm(res).show();
        UserSession.instance.cleanUserSession();});
    }

   
}

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
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.kingpins.tunisiagottalent.Utils.UserSession;

/**
 *
 * @author hela
 */
public class EventListForm extends SideMenuBaseForm {

    public EventListForm(Resources res) throws IOException {

        super(BoxLayout.y());
        setUIID("CompForm");
        setupSideMenu(res);

        EncodedImage enc;
        EventServices es = new EventServices();
        ArrayList<Event> list = es.getAllEvents();

        {
            for (Event r : list) {

                Container c1 = new Container(BoxLayout.x());
                Container c2 = new Container(BoxLayout.x());
                Container c3 = new Container(BoxLayout.y());
                Container c4 = new Container(BoxLayout.xCenter());
                Image im = Image.createImage("/cc2.png");
                Image im2 = Image.createImage("/ttt2.png");
                Button bticket = new Button("Get your Ticket!");
                bticket.setUIID("LoginButton");
                bticket.setIcon(im2);
                bticket.getAllStyles().setBgColor(0xF36B08);
                if (r.getNb_places() == 0) {
                    bticket.setVisible(false);
                }
                ImageViewer iv = new ImageViewer();

                Image placeholder = Image.createImage(this.getWidth() / 3 - 3, this.getWidth() / 3 - 3, 0xbfc9d2);
                EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                ImageViewer img1 = new ImageViewer(URLImage.createToStorage(encImage, "file" + r.getImg(), "http://127.0.0.1:8000/assets/img/shop-img/" + r.getImg()));
                c1.add(img1);
                c1.add(c3);
                c4.add(bticket);
                c3.add(new SpanLabel(r.getTitle().toUpperCase()));
                c3.add(new SpanLabel("Description: " + r.getDescription()));
                c3.add(new SpanLabel("From:" + r.getStart_date()));
                c3.add(new SpanLabel("To:" + r.getEnd_date()));
                Image im4 = Image.createImage("/ll3.png");
                c2.add(im4);
                c2.add(new SpanLabel(r.getLocation()));
                c3.add(c2);
                SpanLabel nbLabel = new SpanLabel("NbPlaces:" );
                SpanLabel l1= new SpanLabel(String.valueOf(r.getNb_places()));
                c3.add(BoxLayout.encloseX(nbLabel,l1));
               // c3.add(c4);
                ShareButton share = new ShareButton();
                share.setUIID("LoginButton");
                share.getAllStyles().setBorder(Border.createRoundBorder(10, 10));              
                FontImage.setMaterialIcon(share, FontImage.MATERIAL_SHARE, 4);
                share.setText("");
                share.setTextToShare(r.getDescription());
                c4.add(share);
                add(c1);
                add(c4);
                String i = UserSession.instance.getU().getEmail();
                bticket.addActionListener(e -> {
                    if (Integer.parseInt(l1.getText()) == 0) {
                        bticket.setVisible(false);
                    }
                    l1.setText(String.valueOf(Integer.parseInt(l1.getText()) - 1));
                    String url = "http://127.0.0.1:8000/event/reservation/" + r.getId();
                    Display.getInstance().execute(url);
                });
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Services.UserServices;

/**
 *
 * @author Anis
 */
public class SignUpForm extends Form {

    UserServices us;

    public SignUpForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        this.us = UserServices.getInstance();
        getAllStyles().setBgImage(theme.getImage("mp4.jpg"));
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        getTitleArea().setUIID("Container");

        Image logo = theme.getImage("logo.png");
        ImageViewer logoViewer = new ImageViewer(logo);
        Container im = FlowLayout.encloseCenter(logoViewer);
        im.getAllStyles().setMarginTop(200);
        TextField usename = new TextField("", "Username", 15, TextField.EMAILADDR);
        TextField email = new TextField("", "Email", 15, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 15, TextField.PASSWORD);
        //TextField password = new TextField("", "Password", 15, TextField.PASSWORD);
        usename.getAllStyles().setMargin(LEFT, 0);
        email.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label userIcon = new Label("", "TextField");
        Label emailIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        userIcon.getAllStyles().setMargin(RIGHT, 0);
        emailIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(userIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button loginButton = new Button("SignUp");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            if ((usename.getText().length() == 0) || (password.getText().length() == 0) || (email.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all fields", new Command("OK"));
            } else {
                if (us.RegisterAction(usename.getText(), email.getText(), password.getText())) {
                    Dialog.show("Confirmation", "Welcome To Our Community ", new Command("OK"));
                    new LoginForm(theme).show();
                } else {
                    Dialog.show("Alert", "User Already Exists", new Command("OK"));
                }

            }

        });
        Label l = new Label("Already Have an Account ?");
        Button createNewAccount = new Button("Login");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener((e) -> {

            new LoginForm(theme).show();

        });
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                spaceLabel,
                BorderLayout.center(usename).
                        add(BorderLayout.WEST, userIcon),
                BorderLayout.center(email).
                        add(BorderLayout.WEST, emailIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                BorderLayout.center(l).
                        add(BorderLayout.EAST, createNewAccount)
        );
        add(BorderLayout.NORTH, im);
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(false);
        by.setScrollVisible(false);
    }

}

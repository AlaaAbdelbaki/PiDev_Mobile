/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.File;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CN1Constants;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;

import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.kingpins.tunisiagottalent.Entity.User;
import com.kingpins.tunisiagottalent.Services.UserServices;
import com.kingpins.tunisiagottalent.Utils.Statics;
import com.kingpins.tunisiagottalent.Utils.UserSession;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author alaa
 */
public class UpdateProfileForm extends Form {

    //Services
    UserServices us = new UserServices();
    //UI elements
    Label usernameLabel = new Label("Username");
    Label firstNameLabel = new Label("First Name");
    Label lastNameLabel = new Label("Last Name");
    Label birthdayLabel = new Label("Birthday");
    Label phoneNumberLabel = new Label("Phone number");
    Label genderLabel = new Label("Gender");
    Label bioLabel = new Label("Biography");
    Label emailLabel = new Label("Email");
    Label passwordLabel = new Label("Password");
    Label passwordConfirmationLabel = new Label("Confirm password");
    Label uploadPicLabel = new Label("Profile Picture");
    Label addressLabel = new Label("Address");

    TextField usernameInput = new TextField();
    TextField firstNameInput = new TextField();
    TextField lastNameInput = new TextField();
    TextField addressInput = new TextField();
    Picker birthdayInput = new Picker();
    TextField phoneNumberInput = new TextField(TextField.PHONENUMBER);
    Picker genderInput = new Picker();
    TextArea bioInput = new TextArea("bio");
    TextField emailInput = new TextField(TextField.EMAILADDR);
    TextField passwordInput = new TextField(TextField.PASSWORD);
    TextField passwordConfirmationInput = new TextField(TextField.PASSWORD);
    Button deleteAccount = new Button("Delete Account");
    Button submit = new Button("Submit");
    Button uploadPic = new Button("Upload profile picture");
    Button takePic = new Button("Take a picture");
    ImageViewer imgv;
    
    

    //Containers
    Container updateProfileContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container uploadProfilePic = new Container(new FlowLayout(CENTER,CENTER));
    Container buttons = new Container(new FlowLayout(CENTER, CENTER));
    User user = us.getUser(UserSession.instance.getU().getId());

    //Init
    void loadInfo() {

        System.out.println(UserSession.instance.getU().getId());
        usernameInput.setEnabled(false);
        genderInput.setType(Display.PICKER_TYPE_STRINGS);
        birthdayInput.setType(Display.PICKER_TYPE_DATE);
        genderInput.setStrings("Male", "Female");
        usernameInput.setText(user.getUsername());
        firstNameInput.setText(user.getName());
        System.out.println(user.getLastName());
        lastNameInput.setText(user.getLastName());
        birthdayInput.setDate(user.getBirthday());
        phoneNumberInput.setText(user.getPhone_number());
        addressInput.setText(user.getAddress());
        if (user.getGender().equals("male")) {
            genderInput.setSelectedString("Male");
        } else if (user.getGender().equals("female")) {
            genderInput.setSelectedString("Female");
        } else {
            genderInput.setText(null);
        }
        bioInput.setText(user.getBio());
        emailInput.setText(user.getEmail());
    }

    public UpdateProfileForm(Resources res, Form parentForm) throws IOException {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> parentForm.showBack());
        //Init
        loadInfo();
        buttons.add(deleteAccount);
        buttons.add(submit);
        usernameInput.getStyle().setFgColor(0);
        firstNameInput.getStyle().setFgColor(0);
        lastNameInput.getStyle().setFgColor(0);
        phoneNumberInput.getStyle().setFgColor(0);
        emailInput.getStyle().setFgColor(0);
        passwordConfirmationInput.getStyle().setFgColor(0);
        passwordInput.getStyle().setFgColor(0);
        birthdayInput.getStyle().setFgColor(0);
        genderInput.getStyle().setFgColor(0);
        addressInput.getStyle().setFgColor(0);

        //Adding to containers
        updateProfileContainer.add(usernameLabel);
        updateProfileContainer.add(usernameInput);
        updateProfileContainer.add(firstNameLabel);
        updateProfileContainer.add(firstNameInput);
        updateProfileContainer.add(lastNameLabel);
        updateProfileContainer.add(lastNameInput);
        updateProfileContainer.add(uploadPicLabel);
        uploadProfilePic.add(uploadPic);
        uploadProfilePic.add(takePic);
        updateProfileContainer.add(uploadProfilePic);
//        updateProfileContainer.add(imgv);
        updateProfileContainer.add(birthdayLabel);
        updateProfileContainer.add(birthdayInput);
        updateProfileContainer.add(phoneNumberLabel);
        updateProfileContainer.add(phoneNumberInput);
        updateProfileContainer.add(genderLabel);
        updateProfileContainer.add(genderInput);
        updateProfileContainer.add(bioLabel);
        updateProfileContainer.add(bioInput);
        updateProfileContainer.add(addressLabel);
        updateProfileContainer.add(addressInput);
        updateProfileContainer.add(emailLabel);
        updateProfileContainer.add(emailInput);
        updateProfileContainer.add(passwordLabel);
        updateProfileContainer.add(passwordInput);
        updateProfileContainer.add(passwordConfirmationLabel);
        updateProfileContainer.add(passwordConfirmationInput);
        updateProfileContainer.add(buttons);
        add(updateProfileContainer);

        //Button Actions
        uploadPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MultipartRequest cr = new MultipartRequest();
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                cr.setUrl(Statics.BASE_URL + "/upload=" + UserSession.instance.getU().getId());
                cr.setPost(true);
                String mime = "image/jpeg";
                try {
                    cr.addData("file", filePath, mime);
                } catch (IOException ex) {
                }
                cr.setFilename("file", randomName() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
//                String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + user.getProfilePic();
//                Image profilePic;
//                EncodedImage enc;
//                try {
//                    enc = EncodedImage.create("/load.png");
//                    profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
//                    profilePic.scale(enc.getWidth(), enc.getHeight());
//                    imgv = new ImageViewer(profilePic);
//
//                } catch (IOException ex) {
//                }
                System.out.println(filePath);

                NetworkManager.getInstance().addToQueueAndWait(cr);
            }
        });
        takePic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MultipartRequest cr = new MultipartRequest();
                String filePath = Capture.capturePhoto();
                cr.setUrl(Statics.BASE_URL + "/upload=" + UserSession.instance.getU().getId());
                cr.setPost(true);
                String mime = "image/jpeg";
                try {
                    cr.addData("file", filePath, mime);
                } catch (IOException ex) {
                }
                cr.setFilename("file", randomName() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
//                String linkProfilePic = "http://127.0.0.1:8000/assets/uploads/" + user.getProfilePic();
//                Image profilePic;
//                EncodedImage enc;
//                try {
//                    enc = EncodedImage.create("/load.png");
//                    profilePic = URLImage.createToStorage(enc, linkProfilePic, linkProfilePic);
//                    profilePic.scale(enc.getWidth(), enc.getHeight());
//                    imgv = new ImageViewer(profilePic);
//
//                } catch (IOException ex) {
//                }
                System.out.println(filePath);

                NetworkManager.getInstance().addToQueueAndWait(cr);
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                User user = UserSession.instance.getU();
                user.setUsername(usernameInput.getText());
                if (!firstNameInput.getText().equals("")) {
                    user.setName(firstNameInput.getText());
                }
                if (!lastNameInput.getText().equals("")) {
                    user.setLastName(lastNameInput.getText());
                }
                user.setBirthday(birthdayInput.getDate());
                if (!phoneNumberInput.getText().equals("")) {
                    user.setPhone_number(phoneNumberInput.getText());
                }
                if (!bioInput.getText().equals("")) {
                    user.setBio(bioInput.getText());
                }
                if (genderInput.getText().equals("Male")) {
                    user.setGender("male");
                } else {
                    user.setGender("female");
                }
                if (!emailInput.getText().equals("")) {

                    user.setEmail(emailInput.getText());
                }
                if (!passwordInput.getText().equals("") && !passwordConfirmationInput.getText().equals("")) {
                    if (passwordInput.getText().equals(passwordConfirmationInput.getText())) {
                        String encrypted = us.encryptPwd(passwordInput.getText());
                        user.setPassword(encrypted);
                    } else {
                        Dialog error = new Dialog("Error");
                        TextArea errorBody = new TextArea("Passwords mismatch!\nPlease verify your password.");
                        Button ok = new Button(new Command("OK"));
                        error.add(errorBody);
                        error.add(ok);
                        error.showDialog();
                    }
                }
                if (us.updateUser(user)) {

                    try {
                        new ProfileForm(res).show();
                    } catch (IOException ex) {
                    }
                }
            }
        });
        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog alert = new Dialog("Warning");
                SpanLabel message = new SpanLabel("Are you sure you want to delete your account?\nThis action once done cannot be reverted!");
                alert.add(message);
                Button ok = new Button("Proceed");
                Button cancel = new Button(new Command("Cancel"));
                //User clicks on ok to delete account
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (us.deleteUser(UserSession.instance.getU().getId())) {
                            UserSession.instance.cleanUserSession();
                            new LoginForm(res).show();
                        }
                    }
                });

                alert.add(cancel);
                alert.add(ok);
                alert.showDialog();

            }
        });
    }

    String randomName() {
        Random rnd = new Random();
        String lettersAndNumbersAndSymbols = "abcdefghijklmnopqrstuvwxyz0123456789_";
        String name = "";
        for (int i = 0; i < 51; i++) {
            name += lettersAndNumbersAndSymbols.charAt(rnd.nextInt(lettersAndNumbersAndSymbols.length()));
        }
        return name;
    }

}

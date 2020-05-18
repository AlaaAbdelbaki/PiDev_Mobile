/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.Entity;

import java.util.Date;


/**
 *
 * @author Anis
 */
public class Comments {
    private int id;
    private User author;
    private String body;
    
    public Comments(){
        
    }
    
    public Comments(String body){
        this.body=body;
    }
    public Comments(int id, User author, String body, Date created_at) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.created_at = created_at;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    private Date created_at;

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    
}

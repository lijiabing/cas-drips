package com.cas.server.dao;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class User implements Serializable{
    private String username;
    private String password;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

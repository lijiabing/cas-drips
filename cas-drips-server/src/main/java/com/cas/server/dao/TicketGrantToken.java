package com.cas.server.dao;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by Administrator on 2018\11\23 0023.
 * TGT  TGT 的id就是cookie的值
 */
public class TicketGrantToken implements Serializable{

    private String id;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.game.smartctrlapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class ListRankBean implements Serializable {

    public List<UserBean> getAppUser() {
        return appUser;
    }

    public void setAppUser(List<UserBean> appUser) {
        this.appUser = appUser;
    }

    private List<UserBean> appUser;

}

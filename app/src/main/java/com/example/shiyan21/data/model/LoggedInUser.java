package com.example.shiyan21.data.model;

/**
 * 数据类，用于捕获从LoginRepository检索的已登录用户的用户信息
 */
public class LoggedInUser {

    private final String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.displayName = displayName;
    }

}
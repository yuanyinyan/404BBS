package com.bbs404.entity;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.bbs404.avobject.InfoObject;
import com.bbs404.avobject.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created in com.bbs404.entity.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 14:42
 */
public abstract class Info implements Serializable {

    private Date createAt;
    private String UserId;
    private String avatarUrl;
    private String name;
    private String avObjectId;

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvObjectId() {
        return avObjectId;
    }

    public void setAvObjectId(String avObjectId) {
        this.avObjectId = avObjectId;
    }

    public Info(InfoObject avO) {
        if (avO == null) return;

        this.setName("");
        this.setAvObjectId(avO.getObjectId());
        this.setCreateAt(avO.getCreatedAt());

        User user = avO.getUser();
        if (user != null) {
            this.setName(user.getUsername());
            this.setUserId(user.getObjectId());
            try {
                AVFile avataObj = user.getAVFile(User.AVATAR);
                this.setAvatarUrl(avataObj.getUrl());
            } catch (Exception ev) {
                this.setAvatarUrl(null);
            }
        }
    }

    public Info() {
    }

}


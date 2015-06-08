package com.bbs404.avobject;

import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.bbs404.entity.Info;

/**
 * Created in com.bbs404.avobject.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 14:43
 */
public abstract class InfoObject extends AVObject {
    public static final String CONTENT = "CONTENT";
    public static final String USER = "USER";
    public static final String OBJECT_ID = "objectId";
    public static final String CREATE_AT = "createdAt";
    public static final String UPDATE_AT = "updateAt";

    public InfoObject() {
        super();
    }

    public User getUser() {
        try {
            return getAVUser(USER, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void setUser(User usr) {
        put(USER, usr);
    }

    public abstract Info getInfo();

}
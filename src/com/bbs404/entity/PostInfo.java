package com.bbs404.entity;

import android.view.View;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bbs404.avobject.PostObject;

import java.util.ArrayList;

/**
 * Created in com.bbs404.entity.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 15:14
 */
public class PostInfo extends Info {
    private ArrayList<String> avImageUrls;
    private String audioURI;
    private int audioLength;
    private String Content;

    private int likes, comments;

    public PostObject getPostObject() {
        try {
            return AVObject.createWithoutData(PostObject.class, getAvObjectId());
        } catch (Exception e) {
            return null;
        }
    }

    public void increaseLikes(int amount) {
        likes += amount;
    }

    public void increaseComments(int amount) {
        comments += amount;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }

    public String getAudioURI() {
        return audioURI;
    }

    public void setAudioURI(String audioURI) {
        this.audioURI = audioURI;
    }

    public ArrayList<String> getAvImageUrls() {
        return avImageUrls;
    }

    public void setAvImageUrls(ArrayList<String> avImageUrls) {
        this.avImageUrls = avImageUrls;
    }

    public PostInfo(PostObject avO) {
        super(avO);

        this.setAudioLength(avO.getAudioLen());
        this.setAudioURI(avO.getAudioUrl());

        this.setAvImageUrls(avO.getImageUrls());

        setContent(avO.getContent());

        likes = avO.getLikes();
        comments = avO.getComments();
    }

    public String getContent() {
        if (Content == null) Content = "";
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}


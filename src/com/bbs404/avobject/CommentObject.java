package com.bbs404.avobject;

import com.avos.avoscloud.AVClassName;
import com.bbs404.entity.CommentInfo;
import com.bbs404.entity.Info;
import com.bbs404.entity.PostInfo;

/**
 * Created in com.bbs404.avobject.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 23:24
 */
@AVClassName("Comment")
public class CommentObject extends InfoObject {
    public static final String POST = "POST";

    public CommentObject() {
        super();
    }

    @Override
    public Info getInfo() {
        return getCommentInfo();
    }

    public CommentInfo getCommentInfo() {
        return new CommentInfo(this);
    }

    public String getContent() {
        return getString(CONTENT);
    }

    public void setContent(String content) {
        put(CONTENT, content);
    }

    public void setPost(PostObject post) {
        put(POST, post);
    }

    public void setPost(PostInfo post) {
        setPost(post.getPostObject());
    }

    public PostObject getPost() {
        try {
            return getAVObject(POST, PostObject.class);
        } catch (Exception e) {
            return null;
        }
    }

}

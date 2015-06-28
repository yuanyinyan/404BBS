package com.bbs404.entity;

import com.avos.avoscloud.AVObject;
import com.bbs404.avobject.CommentObject;

/**
 * Created in com.bbs404.entity.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 23:30
 */
public class CommentInfo extends Info {
    PostInfo postInfo;
    private String comment;

    public PostInfo getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
    }

    public CommentInfo(CommentObject commentObject) {
        super(commentObject);
        postInfo = new PostInfo(commentObject.getPost());
        comment = commentObject.getContent();
    }

    public CommentInfo() {
    }

    public String getContent() {
        return getComment();
    }

    public CommentObject getCommentObject() {
        try {
            return AVObject.createWithoutData(CommentObject.class, getAvObjectId());
        } catch (Exception e) {
            return null;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}

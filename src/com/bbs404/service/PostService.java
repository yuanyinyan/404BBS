package com.bbs404.service;

import com.bbs404.avobject.CommentObject;
import com.bbs404.avobject.PostObject;
import com.bbs404.avobject.User;
import com.bbs404.entity.CommentInfo;

/**
 * Created in com.bbs404.service.
 * User: YuanYin
 * Date: 2015/6/8
 * Time: 10:40
 */
public class PostService {

    public static void SaveComment(CommentInfo commentInfo) throws Exception {
        final CommentObject comment = new CommentObject();
        comment.setContent(commentInfo.getComment());
        comment.setPost(commentInfo.getPostInfo().getPostObject());
        comment.setUser(User.curUser());
        comment.setFetchWhenSave(true);
        comment.save();

        commentInfo.setAvObjectId(comment.getObjectId());
        commentInfo.setCreateAt(comment.getCreatedAt());
        commentInfo.setAvatarUrl(User.curUser().getAvatarUrl());
    }
}

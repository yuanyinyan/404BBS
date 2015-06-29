package com.bbs404.service;

import com.avos.avoscloud.AVQuery;
import com.bbs404.avobject.CommentObject;
import com.bbs404.avobject.PostObject;
import com.bbs404.avobject.User;
import com.bbs404.entity.CommentInfo;
import com.bbs404.entity.PostInfo;

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
        Increment(commentInfo.getPostInfo());
    }

    public static void Increment(final PostInfo post) throws Exception {

        AVQuery<PostObject> query = PostObject.getQuery(PostObject.class);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo(PostObject.OBJECT_ID, post.getAvObjectId());

        try {
            PostObject postObject = query.getFirst();
            postObject.increment(PostObject.COMMENTS, 1);
            postObject.save();
            post.increaseComments(1);

        } catch (Exception e) {
            throw e;
        }
    }

}

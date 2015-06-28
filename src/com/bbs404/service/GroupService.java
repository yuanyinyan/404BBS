package com.bbs404.service;

import com.avos.avoscloud.*;
import com.bbs404.avobject.ChatGroup;
import com.bbs404.avobject.User;
import com.bbs404.base.App;
import com.bbs404.base.C;
import com.bbs404.util.ChatUtils;

import java.util.Arrays;
import java.util.List;

public class GroupService {
    public static final String GROUP_ID = "groupId";

    public static List<ChatGroup> findGroups() throws AVException {
        User user = User.curUser();
        AVQuery<ChatGroup> q = AVObject.getQuery(ChatGroup.class);
        q.whereEqualTo(ChatGroup.M, user.getObjectId());
        q.include(ChatGroup.OWNER);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        return q.find();
    }

    public static boolean isGroupOwner(ChatGroup chatGroup, User user) {
        return chatGroup.getOwner().equals(user);
    }

    public static void inviteMembers(ChatGroup chatGroup, List<User> users) {
        Group group = getGroup(chatGroup);
        List<String> userIds = UserService.transformIds(users);
        group.inviteMember(userIds);
    }

    public static Group getGroup(ChatGroup chatGroup) {
        Session session = ChatService.getSession();
        return session.getGroup(chatGroup.getObjectId());
    }

    public static void kickMember(ChatGroup chatGroup, User member) {
        Group group = getGroup(chatGroup);
        group.kickMember(Arrays.asList(member.getObjectId()));
    }

    public static ChatGroup setNewChatGroupData(String groupId, String newGroupName) throws AVException {
        CloudService.saveChatGroup(groupId, User.curUser().getObjectId(), newGroupName);
        ChatGroup chatGroup = ChatGroup.createWithoutData(ChatGroup.class, groupId);
        chatGroup.fetch("owner");
        return chatGroup;
    }

    public static void cacheChatGroups(List<String> ids) throws AVException {
        if (ids.size() == 0) {
            return;
        }
        findChatGroups(ids);
    }

    private static List<ChatGroup> findChatGroups(List<String> ids) throws AVException {
        AVQuery<ChatGroup> q = AVObject.getQuery(ChatGroup.class);
        q.whereContainedIn(C.OBJECT_ID, ids);
        q.include(ChatGroup.OWNER);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<ChatGroup> chatGroups = q.find();
        App.registerChatGroupsCache(chatGroups);
        return chatGroups;
    }

    public static void cacheChatGroupIfNone(String groupId) throws AVException {
        if (App.lookupChatGroup(groupId) == null) {
            cacheChatGroups(Arrays.asList(groupId));
        }
    }
}

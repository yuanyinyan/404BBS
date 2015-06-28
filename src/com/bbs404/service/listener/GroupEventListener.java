package com.bbs404.service.listener;

import com.avos.avoscloud.Group;

import java.util.List;

//GroupMemberEvent
public interface GroupEventListener {

    void onJoined(Group group);

    void onMemberJoin(Group group, List<String> joinedPeerIds);

    void onMemberLeft(Group group, List<String> leftPeerIds);

    void onQuit(Group group);
}

package com.bbs404.service;

import com.avos.avoscloud.*;
import com.bbs404.db.DBMsg;
import com.bbs404.entity.Msg;
import com.bbs404.entity.MsgBuilder;
import com.bbs404.entity.RoomType;

import java.io.IOException;

public class MsgAgent {
    RoomType roomType;
    String toId;

    public MsgAgent(RoomType roomType, String toId) {
        this.roomType = roomType;
        this.toId = toId;
    }

    public interface MsgBuilderHelper {
        void specifyType(MsgBuilder msgBuilder);
    }

    public Msg createAndSendMsg(MsgBuilderHelper msgBuilderHelper) throws IOException, AVException {
        MsgBuilder builder = new MsgBuilder();
        builder.target(roomType, toId);
        msgBuilderHelper.specifyType(builder);
        builder.upload();
        Msg msg = builder.build();
        sendMsg(msg);
        DBMsg.insertMsg(msg);
        return msg;
    }

    public Msg sendMsg(Msg msg) {
        AVMessage avMsg = msg.toAVMessage();
        Session session = ChatService.getSession();
        if (roomType == RoomType.Single) {
            session.sendMessage(avMsg);
        } else {
            Group group = session.getGroup(toId);
            group.sendMessage(avMsg);
        }
        return msg;
    }

}

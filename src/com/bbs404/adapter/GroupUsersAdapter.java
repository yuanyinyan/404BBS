package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.bbs404.R;
import com.bbs404.avobject.User;
import com.bbs404.util.ChatUtils;

import java.util.List;

public class GroupUsersAdapter extends BaseListAdapter<User> {
    public GroupUsersAdapter(Context ctx, List<User> datas) {
        super(ctx, datas);
    }

    @Override
    public View getView(int position, View conView, ViewGroup parent) {
        if (conView == null) {
            conView = View.inflate(ctx, R.layout.group_user_item, null);
        }
        User user = datas.get(position);
        ChatUtils.setUserView(conView, user);
        return conView;
    }
}

package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.bbs404.avobject.User;
import com.bbs404.ui.view.ViewHolder;
import com.bbs404.util.ChatUtils;
import com.bbs404.R;

import java.util.List;

public class GroupAddMembersAdapter extends BaseCheckListAdapter<User> {

  public GroupAddMembersAdapter(Context ctx, List<User> datas) {
    super(ctx, datas);
  }

  @Override
  public View getView(final int position, View conView, ViewGroup parent) {
    if (conView == null) {
      conView = View.inflate(ctx, R.layout.group_add_members_item, null);
    }
    User user = datas.get(position);
    ChatUtils.setUserView(conView, user);
    CheckBox checkBox = ViewHolder.findViewById(conView, R.id.checkbox);
    setCheckBox(checkBox, position);
    checkBox.setOnCheckedChangeListener(new CheckListener(position));
    return conView;
  }
}
